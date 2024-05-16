package com.manudev.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.model.ComicDomain
import com.manudev.domain.usecases.character.GetCharacterByIdUseCase
import com.manudev.domain.usecases.comic.ComicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailViewState {
    data object Loading : DetailViewState()
    data class Success(val character: CharacterDomain, val comics: List<ComicDomain>) :
        DetailViewState()

    data class Error(val error: String) : DetailViewState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterById: GetCharacterByIdUseCase,
    private val comicUseCase: ComicUseCase,
) : ViewModel() {

    var state by mutableStateOf<DetailViewState>(DetailViewState.Loading)

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            state = DetailViewState.Loading
            getCharacterById.execute(id).collect { result ->
                if (result.isSuccess) {
                    val response = result.getOrNull()!!
                    state = DetailViewState.Success(response, emptyList())
                    response.id?.let { getComics(it) }
                } else {
                    state =
                        DetailViewState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

    private fun getComics(characterId: Int) {
        viewModelScope.launch {
            comicUseCase.execute(characterId).collect { result ->
                if (result.isSuccess) {
                    val response = result.getOrNull()!!
                    val character = (state as? DetailViewState.Success)?.character
                    if (character != null) {
                        state = DetailViewState.Success(character, response)
                    }
                } else {
                    state =
                        DetailViewState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

}
