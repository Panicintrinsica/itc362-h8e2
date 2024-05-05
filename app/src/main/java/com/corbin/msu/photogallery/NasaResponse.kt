package com.corbin.msu.photogallery

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NasaResponse(
    val photos: List<PhotoResponse>
)