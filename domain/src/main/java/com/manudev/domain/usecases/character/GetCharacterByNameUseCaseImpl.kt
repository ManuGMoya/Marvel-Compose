package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByNameUseCaseImpl @Inject constructor(
    private val characterRepository: ICharacterRepository
) : GetCharacterByNameUseCase {
    override suspend fun execute(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<List<CharacterDomain>> =
        characterRepository.getCharacterByName(offset, limit, nameStartsWith)
}
