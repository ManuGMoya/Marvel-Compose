package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCaseImpl @Inject constructor(
    private val characterRepository: ICharacterRepository
) : GetCharactersUseCase {
    override suspend fun execute(offset: Int, limit: Int): Flow<List<CharacterDomain>> =
        characterRepository.getCharacters(offset, limit)
}
