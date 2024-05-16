package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface GetCharactersUseCase {
    suspend fun execute(offset: Int, limit: Int): Flow<Result<List<CharacterDomain>>>
}
