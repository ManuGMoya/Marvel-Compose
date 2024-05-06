package com.manudev.data.comic.remote.datasource

import com.manudev.data.comic.remote.model.ComicDto
import com.manudev.data.response.APIResponseStatus
import com.manudev.data.response.DataWrapper

interface ComicRemoteDataSource {

    suspend fun getComicById(
        id: Int
    ): APIResponseStatus<DataWrapper<ComicDto>>
}
