package com.manudev.domain.model

data class CharacterDomain(
    val id: Int,
    val name: String? = "",
    val image: String = "",
    val description: String? = "",
    val numberOfComics: Int,
)
