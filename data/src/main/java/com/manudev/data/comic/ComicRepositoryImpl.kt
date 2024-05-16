package com.manudev.data.comic

import com.manudev.data.comic.remote.ComicApi
import com.manudev.domain.model.ComicDomain
import com.manudev.domain.repository.IComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val service: ComicApi
) : IComicRepository {

    override suspend fun getComicsById(characterId: Int): Flow<Result<List<ComicDomain>>> =
        flow {

            val response = service.getComicById(characterId)

            if (response.isSuccessful) {
                val comics = response.body()?.data?.results?.map { it.toDomain() }
                if (comics != null) {
                    emit(Result.success(comics))
                } else {
                    emit(Result.failure(Exception("No comics found")))
                }
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        }
}
