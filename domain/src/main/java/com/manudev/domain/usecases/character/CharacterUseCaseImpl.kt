package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCaseImpl @Inject constructor(
    private val characterRepository: ICharacterRepository
) : CharacterUseCase {

    override fun getCharacters(offset: Int, limit: Int): Flow<List<CharacterDomain>> {
        return characterRepository.getCharacters(offset, limit)
    }

    override fun getCharacterById(characterId: Int): Flow<CharacterDomain> {
        return characterRepository.getCharacterById(characterId)
    }

    override fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<List<CharacterDomain>> {
        return characterRepository.getCharacterByName(offset, limit, nameStartsWith)
    }

}
