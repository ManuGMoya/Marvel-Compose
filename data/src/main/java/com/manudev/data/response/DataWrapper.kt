package com.manudev.data.response

data class DataWrapper<T>(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: DataContainer<T>?,
    val etag: String?
)