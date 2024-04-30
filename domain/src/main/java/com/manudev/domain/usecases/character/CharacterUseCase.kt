package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface CharacterUseCase {

    fun getCharacters(
        offset: Int,
        limit: Int,
    ): Flow<List<CharacterDomain>>

    fun getCharacterById(
        characterId: Int
    ): Flow<CharacterDomain>

    fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String,
    ): Flow<List<CharacterDomain>>

}
