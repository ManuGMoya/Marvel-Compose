package com.manudev.presentation.screens.home

import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.GetCharacterByNameUseCase
import com.manudev.domain.usecases.character.GetCharactersUseCase
import com.manudev.presentation.BaseTestCoroutine
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
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
            } returns flowOf(APIResponseStatus.Success(characters))

            viewModel.getCharacters(0, 10)

            assertTrue(viewModel.state is HomeViewState.Success)
            val state = viewModel.state as HomeViewState.Success
            assertEquals(characters, state.characters)
        }

    @Test
    fun `when getCharacters is invoked and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery { getCharactersUseCase.execute(any(), any()) } returns flowOf(APIResponseStatus.Error(errorMessage))

            viewModel.getCharacters(0, 10)

            assertTrue(viewModel.state is HomeViewState.Error)
            val state = viewModel.state as HomeViewState.Error
            assertEquals(errorMessage, state.error)
        }

    @Test
    fun `when getCharacterByName is invoked with valid parameters, it should update the state with the correct characters`() =
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
            coEvery { getCharacterByNameUseCase.execute(any(), any(), any()) } returns flowOf(APIResponseStatus.Success(characters))

            viewModel.getCharacterByName(0, 10, "Ch")

            assertTrue(viewModel.state is HomeViewState.Success)
            val state = viewModel.state as HomeViewState.Success
            assertEquals(characters, state.characters)
        }

    @Test
    fun `when getCharacterByName is invoked and an error occurs, it should update the state with the error message`() =
        runBlockingTest {
            val errorMessage = "Error message"
            coEvery { getCharacterByNameUseCase.execute(any(), any(), any()) } returns flowOf(APIResponseStatus.Error(errorMessage))

            viewModel.getCharacterByName(0, 10, "Character")

            assertTrue(viewModel.state is HomeViewState.Error)
            val state = viewModel.state as HomeViewState.Error
            assertEquals(errorMessage, state.error)
        }
}