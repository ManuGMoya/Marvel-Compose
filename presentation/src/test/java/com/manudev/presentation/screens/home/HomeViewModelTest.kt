package com.manudev.presentation.screens.home

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.GetCharacterByNameUseCase
import com.manudev.domain.usecases.character.GetCharactersUseCase
import com.manudev.presentation.BaseTestCoroutine
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest : BaseTestCoroutine() {

    private lateinit var viewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @RelaxedMockK
    private lateinit var getCharacterByNameUseCase: GetCharacterByNameUseCase

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(getCharactersUseCase, getCharacterByNameUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCharacters is invoked with valid parameters, it should update the state with the correct characters`() =
        runTest {
            val characters = listOf(
                CharacterDomain(
                    id = 1,
                    name = "Character 1",
                    image = "url1",
                    numberOfComics = 5,
                    description = null
                ),
                CharacterDomain(
                    id = 2,
                    name = "Character 2",
                    image = "url2",
                    numberOfComics = 3,
                    description = ""
                ),
            )
            coEvery {
                getCharactersUseCase.execute(
                    any(),
                    any()
                )
            } returns flowOf(Result.success(characters))

            viewModel.getCharacters(0, 10)

            advanceUntilIdle()

            assertTrue(viewModel.state is HomeViewState.Success)
            val state = viewModel.state as HomeViewState.Success
            assertEquals(characters, state.characters)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getCharacters is invoked and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery {
                getCharactersUseCase.execute(
                    any(),
                    any()
                )
            } returns flowOf(Result.failure(RuntimeException(errorMessage)))

            viewModel.getCharacters(0, 10)

            advanceUntilIdle()

            assertTrue(viewModel.state is HomeViewState.Error)
            val state = viewModel.state as HomeViewState.Error
            assertEquals(errorMessage, state.error)
        }

    @Test
    fun `when getCharacterByNameUseCase is invoked with valid parameters, it should return a successful result with the correct characters`() =
        runTest {
            val characters = listOf(
                CharacterDomain(
                    id = 1,
                    name = "Character 1",
                    image = "url1",
                    numberOfComics = 5,
                    description = null
                ),
                CharacterDomain(
                    id = 2,
                    name = "Character 2",
                    image = "url2",
                    numberOfComics = 3,
                    description = ""
                ),
            )
            coEvery {
                getCharacterByNameUseCase.execute(
                    any(),
                    any(),
                    any()
                )
            } returns flowOf(Result.success(characters))

            viewModel.getCharacterByName(0, 10, "Ch")

            val result = getCharacterByNameUseCase.execute(0, 10, "Ch").first()

            assertTrue(result.isSuccess)
            assertEquals(characters, result.getOrNull())
        }

    @Test
    fun `when getCharacterByNameUseCase is invoked and an error occurs, it should return a failure result with the correct error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery {
                getCharacterByNameUseCase.execute(
                    any(),
                    any(),
                    any()
                )
            } returns flowOf(Result.failure(RuntimeException(errorMessage)))

            viewModel.getCharacterByName(0, 10, "Character")

            val result = getCharacterByNameUseCase.execute(0, 10, "Character").first()

            assertTrue(result.isFailure)
            assertEquals(errorMessage, result.exceptionOrNull()?.message)

        }
}
