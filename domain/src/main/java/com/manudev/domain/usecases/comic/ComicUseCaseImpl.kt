package com.manudev.domain.usecases.comic

import com.manudev.domain.model.ComicDomain
import com.manudev.domain.repository.IComicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComicUseCaseImpl @Inject constructor(
    private val comicRepository: IComicRepository
) : ComicUseCase {

    override fun getComicById(id: Int): Flow<List<ComicDomain>> {
        return comicRepository.getComicsById(id)
    }
}
