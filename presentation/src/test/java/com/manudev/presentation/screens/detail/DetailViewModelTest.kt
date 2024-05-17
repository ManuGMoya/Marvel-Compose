package com.manudev.presentation.screens.detail

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.GetCharacterByIdUseCase
import com.manudev.domain.usecases.comic.ComicUseCase
import com.manudev.presentation.BaseTestCoroutine
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
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

    @OptIn(ExperimentalCoroutinesApi::class)
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
                Result.success(
                    character
                )
            )

            viewModel.getCharacterDetail(1)
            advanceUntilIdle()

            assertTrue(viewModel.state is DetailViewState.Success)
            val state = viewModel.state as DetailViewState.Success
            assertEquals(character, state.character)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCharacterDetail is called and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery { getCharacterByIdUseCase.execute(any()) } returns flowOf(
                Result.failure(
                    RuntimeException(errorMessage)
                )
            )

            viewModel.getCharacterDetail(1)
            advanceUntilIdle()

            assertTrue(viewModel.state is DetailViewState.Error)
            val state = viewModel.state as DetailViewState.Error
            assertEquals(errorMessage, state.error)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
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
            coEvery { getCharacterByIdUseCase.execute(any()) } returns flowOf(
                Result.success(
                    character
                )
            )

            viewModel.getCharacterDetail(characterId)
            advanceUntilIdle()

            coVerify {
                comicUseCase.execute(characterId)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
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
                Result.success(
                    character
                )
            )
            coEvery { comicUseCase.execute(any()) } returns flowOf(
                Result.failure(
                    RuntimeException(errorMessage)
                )
            )

            runBlocking {
                viewModel.getCharacterDetail(characterId)
            }
            advanceUntilIdle()

            assertTrue(viewModel.state is DetailViewState.Error)
            val state = viewModel.state as DetailViewState.Error
            assertEquals(errorMessage, state.error)
        }
}
