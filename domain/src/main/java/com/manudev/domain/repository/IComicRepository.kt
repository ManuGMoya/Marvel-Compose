package com.manudev.domain.repository

import com.manudev.domain.model.ComicDomain
import kotlinx.coroutines.flow.Flow

interface IComicRepository {

    fun getComicsById(
        id: Int,
    ): Flow<List<ComicDomain>>
}
