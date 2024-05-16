package com.manudev.presentation.screens.detail

import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.GetCharacterByIdUseCase
import com.manudev.domain.usecases.comic.ComicUseCase
import com.manudev.presentation.BaseTestCoroutine
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailViewModelTest : BaseTestCoroutine() {

    private lateinit var viewModel: DetailViewModel

    @RelaxedMockK
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @RelaxedMockK
    private lateinit var comicUseCase: ComicUseCase

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(getCharacterByIdUseCase, comicUseCase)
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
            coEvery { getCharacterByIdUseCase.execute(any()) } returns flowOf(
                APIResponseStatus.Success(
                    character
                )
            )

            viewModel.getCharacterDetail(1)

            assertTrue(viewModel.state is DetailViewState.Success)
            val state = viewModel.state as DetailViewState.Success
            assertEquals(character, state.character)
        }

    @Test
    fun `when getCharacterDetail is called and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery { getCharacterByIdUseCase.execute(any()) } returns flowOf(
                APIResponseStatus.Error(
                    errorMessage
                )
            )

            viewModel.getCharacterDetail(1)

            assertTrue(viewModel.state is DetailViewState.Error)
            val state = viewModel.state as DetailViewState.Error
            assertEquals(errorMessage, state.error)
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
            val response = flowOf(APIResponseStatus.Success(character))

            coEvery { getCharacterByIdUseCase.execute(any()) } returns response

            viewModel.getCharacterDetail(characterId)

            coVerify {
                comicUseCase.getComicById(characterId)
            }
        }

    @Test
    fun `when getCharacterDetail() is invoked and getComics() returns an error then the error state is updated`() =
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
            coEvery { getCharacterByIdUseCase.execute(any()) } returns flowOf(
                APIResponseStatus.Success(
                    character
                )
            )
            coEvery { comicUseCase.getComicById(any()) } returns flowOf(
                APIResponseStatus.Error(
                    errorMessage
                )
            )

            runBlocking {
                viewModel.getCharacterDetail(characterId)
            }

            assertTrue(viewModel.state is DetailViewState.Error)
            val state = viewModel.state as DetailViewState.Error
            assertEquals(errorMessage, state.error)
        }
}