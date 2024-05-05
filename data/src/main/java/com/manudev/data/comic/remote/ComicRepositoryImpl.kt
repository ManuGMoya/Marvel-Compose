package com.manudev.data.comic.remote

import com.manudev.data.comic.remote.datasource.ComicRemoteDataSource
import com.manudev.data.response.APIResponseStatus
import com.manudev.domain.model.ComicDomain
import com.manudev.domain.repository.IComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val remote: ComicRemoteDataSource
) : IComicRepository {


    override fun getComicsById(id: Int): Flow<List<ComicDomain>> =
        flow {
            when (val response = remote.getComicById(id)) {
                is APIResponseStatus.Success -> {
                    emit(response.data.data?.results?.map {
                        it.toDomain()
                    } ?: emptyList())
                }

                is APIResponseStatus.Error -> {
                    throw Exception(response.message)
                }
            }
        }
}
