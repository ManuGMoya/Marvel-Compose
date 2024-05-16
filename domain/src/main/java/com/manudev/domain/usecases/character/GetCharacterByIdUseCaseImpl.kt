package com.manudev.domain.usecases.character

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCaseImpl @Inject constructor(
    private val characterRepository: ICharacterRepository
) : GetCharacterByIdUseCase {
    override suspend fun execute(characterId: Int): Flow<Result<CharacterDomain>> =
        characterRepository.getCharacterById(characterId)
}
