package com.manudev.domain.model

data class CharacterDomain(
    val id: Int,
    val name: String?,
    val image: String?,
    val description: String?,
    val numberOfComics: Int
)

data class Comic(
    val resourceURI: String?,
    val name: String?,
)