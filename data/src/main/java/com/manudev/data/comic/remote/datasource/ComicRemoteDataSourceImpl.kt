package com.manudev.data.comic.remote.datasource

import com.manudev.data.comic.remote.ComicApi
import com.manudev.data.comic.remote.model.ComicDto
import com.manudev.data.response.APIResponseStatus
import com.manudev.data.response.DataWrapper
import javax.inject.Inject

class ComicRemoteDataSourceImpl @Inject constructor(
    private val service: ComicApi
) : ComicRemoteDataSource {

    override suspend fun getComicById(
        id: Int
    ): APIResponseStatus<DataWrapper<ComicDto>> {
        return try {
            val response = service.getComicById(id)
            if (response.isSuccessful) {
                APIResponseStatus.Success(response.body()!!)
            } else {
                APIResponseStatus.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            APIResponseStatus.Error(e.message ?: "Unknown error")
        }
    }
}
