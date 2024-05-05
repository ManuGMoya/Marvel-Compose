package com.manudev.data.comic.remote

import com.manudev.data.comic.remote.model.ComicDto
import com.manudev.data.response.DataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ComicApi {
    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComicById(
        @Path("characterId") characterId: Int,
    ): Response<DataWrapper<ComicDto>>
}
