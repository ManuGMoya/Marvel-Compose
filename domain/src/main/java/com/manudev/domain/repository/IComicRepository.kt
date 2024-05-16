package com.manudev.domain.repository

import com.manudev.domain.model.ComicDomain
import kotlinx.coroutines.flow.Flow

interface IComicRepository {
    suspend fun getComicsById(characterId: Int): Flow<Result<List<ComicDomain>>>
}
