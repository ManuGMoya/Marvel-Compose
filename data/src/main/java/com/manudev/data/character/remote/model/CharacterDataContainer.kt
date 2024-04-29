package com.manudev.data.character.remote.model

import com.manudev.domain.model.CharacterDomain

data class CharacterDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<Character>?
) {
    fun toDomain(): List<CharacterDomain> {
        return results?.map {
            it.toDomain()
        } ?: emptyList()
    }
}
