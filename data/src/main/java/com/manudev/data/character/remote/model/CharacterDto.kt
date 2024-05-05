package com.manudev.data.character.remote.model

import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.model.Comic
import java.util.Date

data class CharacterDto(
    val id: Int,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val urls: List<Url>?,
    val thumbnail: Image?,
    val comics: ComicList?,
    val stories: StoryList?,
    val events: EventList?,
    val series: SeriesList?
) {
    fun toDomain(): CharacterDomain {
        return CharacterDomain(
            id = id,
            name = name,
            image = thumbnail?.path + "." + thumbnail?.extension,
            description = description,
            comics = comics?.items?.map {
                Comic(
                    resourceURI = it.resourceURI,
                    name = it.name
                )
            } ?: emptyList()
        )
    }
}
