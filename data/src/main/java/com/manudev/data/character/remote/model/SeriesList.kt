package com.manudev.data.character.remote.model

data class SeriesList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<SeriesSummary>?
)