package com.manudev.data.character.remote.model

data class EventList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<EventSummary>?
)