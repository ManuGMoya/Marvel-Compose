package com.manudev.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.model.ComicDomain
import com.manudev.domain.usecases.character.CharacterUseCase
import com.manudev.domain.usecases.comic.ComicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val comicUseCase: ComicUseCase,
) : ViewModel() {

    var state by mutableStateOf(UiState(isLoading = true))

    data class UiState(
        val isLoading: Boolean = false,
        val character: CharacterDomain? = null,
        val error: String? = null,
        val comics: List<ComicDomain> = emptyList()
    )

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                characterUseCase.getCharacterById(id).collect { character ->
                    state = state.copy(
                        isLoading = false,
                        character = character
                    )
                    getComics(character.id)
                }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message)
            }
        }
    }

    private fun getComics(characterId: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                comicUseCase.getComicById(characterId).collect { comics ->
                    state = state.copy(
                        isLoading = false,
                        comics = comics
                    )
                }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message)
            }
        }
    }
}
