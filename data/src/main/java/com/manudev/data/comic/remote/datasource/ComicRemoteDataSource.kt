package com.manudev.data.comic.remote.datasource

import com.manudev.data.comic.remote.model.ComicDto
import com.manudev.domain.APIResponseStatus
import com.manudev.data.response.DataWrapper

interface ComicRemoteDataSource {

    suspend fun getComicById(
        id: Int
    ): APIResponseStatus<DataWrapper<ComicDto>>
}
