package com.manudev.data.character.remote.model

import com.manudev.domain.model.CharacterDomain

data class CharacterDto(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: Image?,
    val comics: ComicList?,
) {
    fun toDomain(): CharacterDomain {
        return CharacterDomain(
            id = id,
            name = name,
            image = thumbnail?.path + "." + thumbnail?.extension,
            description = description,
            numberOfComics = comics?.items?.size ?: 0,
        )
    }
}
