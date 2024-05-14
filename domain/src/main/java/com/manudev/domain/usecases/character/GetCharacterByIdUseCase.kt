package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface GetCharacterByIdUseCase {
    suspend fun execute(characterId: Int): Flow<CharacterDomain>
}
