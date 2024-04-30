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
): ViewModel() {

    var state by mutableStateOf(UiState(isLoading = true))
    data class UiState(
        val isLoading: Boolean = false,
        val characters: List<CharacterDomain> = emptyList(),
        val error: String? = null
    )


    fun getCharacters(offset: Int, limit: Int) {
        viewModelScope.launch {
            try {
                characterUseCase.getCharacters(offset, limit).collect { characters ->
                    state = UiState(isLoading = false, characters = characters)
                }
            } catch (e: Exception) {
                state = UiState(isLoading = false, error = e.message)
            }
        }
    }
}
