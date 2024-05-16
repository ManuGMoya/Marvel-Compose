package com.manudev.domain.usecases.comic

import com.manudev.domain.model.ComicDomain
import kotlinx.coroutines.flow.Flow

interface ComicUseCase {
    suspend fun execute(characterId: Int): Flow<Result<List<ComicDomain>>>
}
