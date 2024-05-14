package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow


interface GetCharacterByNameUseCase {
    suspend fun execute(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<List<CharacterDomain>>
}
