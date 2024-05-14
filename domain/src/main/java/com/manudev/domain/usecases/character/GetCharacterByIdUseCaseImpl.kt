package com.manudev.domain.usecases.character

import com.manudev.domain.repository.ICharacterRepository
import javax.inject.Inject

class GetCharacterByIdUseCaseImpl @Inject constructor(
    private val characterRepository: ICharacterRepository
) : GetCharacterByIdUseCase {
    override suspend fun execute(characterId: Int) =
        characterRepository.getCharacterById(characterId)
}
