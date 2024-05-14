package com.manudev.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.usecases.character.GetCharacterByNameUseCase
import com.manudev.domain.usecases.character.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeViewState {
    data object Loading : HomeViewState()
    data class Success(val characters: List<CharacterDomain>) : HomeViewState()
    data class Error(val error: String) : HomeViewState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterByNameUseCase: GetCharacterByNameUseCase
) : ViewModel() {

    var state by mutableStateOf<HomeViewState>(HomeViewState.Loading)

    fun getCharacters(offset: Int, limit: Int) {
        viewModelScope.launch {
            val currentCharacters = (state as? HomeViewState.Success)?.characters ?: emptyList()

            state = HomeViewState.Loading
            try {
                getCharactersUseCase.execute(offset, limit).collect { newCharacters ->
                    val updatedCharacters = currentCharacters + newCharacters
                    state = HomeViewState.Success(updatedCharacters)
                }
            } catch (e: Exception) {
                state = HomeViewState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getCharacterByName(offset: Int, limit: Int, nameStartsWith: String) {
        viewModelScope.launch {
            state = HomeViewState.Loading
            try {
                getCharacterByNameUseCase.execute(offset, limit, nameStartsWith)
                    .collect { newCharacters ->
                        state = HomeViewState.Success(newCharacters)
                    }
            } catch (e: Exception) {
                state = HomeViewState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
