package com.manudev.domain.usecases.comic

import com.manudev.domain.model.ComicDomain
import kotlinx.coroutines.flow.Flow

interface ComicUseCase {

    fun getComicById(
        id: Int
    ) : Flow<List<ComicDomain>>
}
