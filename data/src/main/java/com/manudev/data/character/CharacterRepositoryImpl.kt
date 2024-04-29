package com.manudev.data.character

import com.manudev.data.character.remote.CharacterRemoteDataSource
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remote: CharacterRemoteDataSource
) : CharacterRepository {

    override fun getCharacters(offset: Int, limit: Int): Flow<List<CharacterDomain>> =
        flow {
            emit(remote.getAllCharacter(offset, limit).toDomain())
        }

    override fun getCharacterById(characterId: Int): Flow<CharacterDomain> =
        flow {
            emit(remote.getCharacterById(characterId).toDomain().first())
        }

    override fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<List<CharacterDomain>> =
        flow {
            emit(
                remote.getCharacterByStartName(
                    offset = offset,
                    limit = limit,
                    nameStartsWith = nameStartsWith
                ).toDomain()
            )
        }

}
