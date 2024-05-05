package com.corbin.msu.photogallery

import retrofit2.http.GET
import retrofit2.http.Query
interface NasaApi {
    @GET("apod")
    suspend fun fetchPhotos(
        @Query("api_key") apiKey: String = "",
        @Query("count") count: Int = 50,
        @Query("thumbs") thumbs: Boolean = true
    ): List<GalleryItem>
}