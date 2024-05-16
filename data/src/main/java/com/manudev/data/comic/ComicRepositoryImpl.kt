package com.manudev.data.comic

import com.manudev.data.comic.remote.ComicApi
import com.manudev.data.utils.Network.safeCall
import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.ComicDomain
import com.manudev.domain.repository.IComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val service: ComicApi
) : IComicRepository {

    override suspend fun getComicsById(characterId: Int): Flow<APIResponseStatus<List<ComicDomain>>> =
        flow {
            when (val response = service.getComicById(characterId).safeCall()) {
                is APIResponseStatus.Success -> {
                    val comics = response.data.data?.results?.map { it.toDomain() }
                    if (comics != null) {
                        emit(APIResponseStatus.Success(comics))
                    } else {
                        emit(APIResponseStatus.Error("Comics not found"))
                    }
                }

                is APIResponseStatus.Error -> {
                    emit(APIResponseStatus.Error(response.message))
                }
            }
        }
}
