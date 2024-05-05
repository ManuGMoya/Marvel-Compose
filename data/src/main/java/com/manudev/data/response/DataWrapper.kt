package com.manudev.data.response

import com.manudev.data.character.remote.model.CharacterDataContainer

data class DataWrapper(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDataContainer?,
    val etag: String?
)