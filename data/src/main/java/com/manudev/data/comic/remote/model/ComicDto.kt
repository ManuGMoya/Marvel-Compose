package com.manudev.data.comic.remote.model

import com.manudev.data.character.remote.model.Image
import com.manudev.domain.model.ComicDomain
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ComicDto(
    val id: Int,
    val digitalId: Int,
    val title: String,
    val issueNumber: Double,
    val variantDescription: String,
    val description: String,
    val modified: Date,
    val isbn: String,
    val upc: String,
    val diamondCode: String,
    val ean: String,
    val issn: String,
    val format: String,
    val pageCount: Int,
    val dates: List<Date>,
    val thumbnail: Image,
) {
    fun toDomain(): ComicDomain {
        return ComicDomain(
            id = id,
            title = title,
            date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                .format(dates.first()),
            image = thumbnail.path + "." + thumbnail.extension
        )
    }
}
