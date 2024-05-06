package com.manudev.data.comic.remote.model

import com.manudev.data.character.remote.model.Image
import com.manudev.domain.model.ComicDomain
import java.text.SimpleDateFormat
import java.util.Locale

data class ComicDto(
    val id: Int,
    val title: String,
    val dates: List<DateDto>,
    val thumbnail: Image,
) {
    fun toDomain(): ComicDomain {
        val date = buildDate()
        return ComicDomain(
            id = id,
            title = title,
            date = date.orEmpty(),
            image = thumbnail.path + "." + thumbnail.extension
        )
    }

    private fun buildDate(): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val onSaleDate = dates.firstOrNull { it.type == "onsaleDate" }
        val date = if (onSaleDate != null) {
            parser.parse(onSaleDate.date.replace("Z", "+0000"))
        } else {
            null
        }
        return date?.let { formatter.format(it) }
    }
}

data class DateDto(
    val date: String,
    val type: String
)
