package com.manudev.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState(isLoading = true))

    data class UiState(
        val isLoading: Boolean = false,
        val characters: List<CharacterDomain> = emptyList(),
        val error: String? = null
    )


    fun getCharacters(offset: Int, limit: Int, reset: Boolean = false) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                characterUseCase.getCharacters(offset, limit).collect { newCharacters ->
                    state = state.copy(
                        isLoading = false,
                        characters = if (reset) newCharacters else state.characters + newCharacters
                    )
                }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun getCharacterByName(offset: Int, limit: Int, nameStartsWith: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                characterUseCase.getCharacterByName(offset, limit, nameStartsWith)
                    .collect { newCharacters ->
                        state = state.copy(
                            isLoading = false,
                            characters = newCharacters
                        )
                    }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message)
            }
        }
    }
}
