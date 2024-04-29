package com.manudev.data.character.remote.model

data class StoryList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<StorySummary>?
)