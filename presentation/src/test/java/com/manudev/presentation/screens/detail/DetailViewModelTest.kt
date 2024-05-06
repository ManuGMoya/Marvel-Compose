package com.manudev.presentation.screens.detail

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.CharacterUseCase
import com.manudev.domain.usecases.comic.ComicUseCase
import com.manudev.presentation.BaseTestCoroutine
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailViewModelTest : BaseTestCoroutine() {

    private lateinit var viewModel: DetailViewModel

    @RelaxedMockK
    private lateinit var characterUseCase: CharacterUseCase

    @RelaxedMockK
    private lateinit var comicUseCase: ComicUseCase

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        viewModel = spyk(
            DetailViewModel(
                characterUseCase,
                comicUseCase
            ),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `when getCharacterDetail is called with a valid ID, it should update the state with the correct character`() =
        runTest {
            val character = CharacterDomain(
                id = 1,
                name = "Character 1",
                image = "url1",
                numberOfComics = 5,
                description = null
            )
            coEvery { characterUseCase.getCharacterById(any()) } returns flowOf(character)

            viewModel.getCharacterDetail(1)

            assertEquals(character, viewModel.state.character)
            assertNull(viewModel.state.error)
        }

    @Test
    fun `when getCharacterDetail is called and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery { characterUseCase.getCharacterById(any()) } throws Exception(errorMessage)

            viewModel.getCharacterDetail(1)

            assertNull(viewModel.state.character)
            assertFalse(viewModel.state.isLoading)
            assertEquals(errorMessage, viewModel.state.error)
        }


    @Test
    fun `when getCharacterDetail is called and succeeds, it should also call getComics with the correct character ID`() =
        runTest {
            val characterId = 1
            val character = CharacterDomain(
                id = characterId,
                name = "Character 1",
                image = "url1",
                numberOfComics = 5,
                description = null
            )
            val response = flowOf(character)

            coEvery { characterUseCase.getCharacterById(any()) } returns response

            viewModel.getCharacterDetail(characterId)

            coVerify {
                comicUseCase.getComicById(characterId)
            }
        }


    @Test
    fun `when getCharacterDetail() is invoked and getComics() throws an error then the error state is updated`() =
        runTest {
            val characterId = 1
            val character = CharacterDomain(
                id = characterId,
                name = "Character 1",
                image = "url1",
                numberOfComics = 5,
                description = null
            )
            val errorMessage = "Error message"
            coEvery { characterUseCase.getCharacterById(any()) } returns flowOf(character)
            coEvery { comicUseCase.getComicById(any()) } throws Exception(errorMessage)

            runBlocking {
                viewModel.getCharacterDetail(characterId)
            }

            assertTrue(viewModel.state.comics.isEmpty())
            assertFalse(viewModel.state.isLoading)
            assertEquals(errorMessage, viewModel.state.error)
        }
}
