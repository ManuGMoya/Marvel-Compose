package com.manudev.presentation.screens.home


import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.CharacterUseCase
import com.manudev.presentation.BaseTestCoroutine
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest() : BaseTestCoroutine() {

    private lateinit var viewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var characterUseCase: CharacterUseCase

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(characterUseCase)
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
            coEvery { characterUseCase.getCharacters(any(), any()) } returns flowOf(characters)


            viewModel.getCharacters(0, 10)

            assertEquals(characters, viewModel.state.characters)
            assertFalse(viewModel.state.isLoading)
            assertNull(viewModel.state.error)
        }

    @Test
    fun `when getCharacters is invoked and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery { characterUseCase.getCharacters(any(), any()) } throws Exception(errorMessage)

            viewModel.getCharacters(0, 10)

            assertTrue(viewModel.state.characters.isEmpty())
            assertFalse(viewModel.state.isLoading)
            assertEquals(errorMessage, viewModel.state.error)
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
            coEvery { characterUseCase.getCharacterByName(any(), any(), any()) } returns flowOf(
                characters
            )


            viewModel.getCharacterByName(0, 10, "Ch")


            assertEquals(characters, viewModel.state.characters)
            assertFalse(viewModel.state.isLoading)
            assertNull(viewModel.state.error)
        }

    @Test
    fun `when getCharacterByName is invoked and an error occurs, it should update the state with the error message`() =
        runTest {
            val errorMessage = "Error message"
            coEvery { characterUseCase.getCharacterByName(any(), any(), any()) } throws Exception(
                errorMessage
            )

            viewModel.getCharacterByName(0, 10, "Character")

            assertTrue(viewModel.state.characters.isEmpty())
            assertFalse(viewModel.state.isLoading)
            assertEquals(errorMessage, viewModel.state.error)
        }

}
