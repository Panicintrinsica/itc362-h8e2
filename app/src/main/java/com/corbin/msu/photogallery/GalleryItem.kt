package com.corbin.msu.photogallery

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    val title: String,
    val url: String,
    val hdUrl: String?,
    val copyright: String?,
    val date: String,
    val explanation: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    @Json(name = "thumbnail_url") val thumbnailUrl: String?
)