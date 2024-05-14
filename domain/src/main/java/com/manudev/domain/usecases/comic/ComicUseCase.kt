package com.manudev.domain.usecases.comic

import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.ComicDomain
import kotlinx.coroutines.flow.Flow

interface ComicUseCase {
    suspend fun getComicById(characterId: Int): Flow<APIResponseStatus<List<ComicDomain>>>
}
