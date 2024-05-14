package com.manudev.domain.usecases.comic

import com.manudev.domain.repository.IComicRepository
import javax.inject.Inject

class ComicUseCaseImpl @Inject constructor(
    private val comicRepository: IComicRepository
) : ComicUseCase {
    override suspend fun getComicById(characterId: Int) =
        comicRepository.getComicsById(characterId)
}