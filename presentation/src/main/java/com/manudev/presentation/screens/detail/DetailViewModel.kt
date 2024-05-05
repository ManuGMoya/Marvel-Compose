package com.manudev.presentation.screens.detail

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
class DetailViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    var state by mutableStateOf(UiState(isLoading = true))

    data class UiState(
        val isLoading: Boolean = false,
        val character: CharacterDomain? = null,
        val error: String? = null
    )

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                characterUseCase.getCharacterById(id).collect { character ->
                    state = state.copy(
                        isLoading = false,
                        character = character
                    )
                }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message)
            }
        }
    }
}
