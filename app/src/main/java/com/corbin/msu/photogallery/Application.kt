package com.corbin.msu.photogallery

import android.app.Application
import coil.Coil
import coil.ImageLoader
import java.util.Properties

class NasaApplication : Application() {
    lateinit var apiKey: String
    override fun onCreate() {
        super.onCreate()

        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient(PhotoRepository().unsafeOkHttpClient.build())
            .build()

        Coil.setImageLoader(imageLoader)
    }
}