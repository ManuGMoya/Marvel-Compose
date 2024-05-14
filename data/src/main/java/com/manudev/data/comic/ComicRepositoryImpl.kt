package com.manudev.data.comic

import com.manudev.data.comic.remote.datasource.ComicRemoteDataSource
import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.ComicDomain
import com.manudev.domain.repository.IComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val remote: ComicRemoteDataSource
) : IComicRepository {

    override suspend fun getComicsById(characterId: Int): Flow<APIResponseStatus<List<ComicDomain>>> =
        flow {
            when (val response = remote.getComicById(characterId)) {
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
