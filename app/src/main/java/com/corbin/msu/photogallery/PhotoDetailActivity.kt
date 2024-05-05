package com.corbin.msu.photogallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.corbin.msu.photogallery.databinding.ActivityPhotoDetailBinding

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        Log.d("PhotoDetailActivity", "URL: $url, Title: $title")

        supportActionBar?.title = title

        // Set the title of the photo as the text of the TextView
        binding.photoTitleTextView.text = title

        binding.photoImageView.load(url) {
            listener(onSuccess = { request, metadata ->
                Log.d("PhotoDetailActivity", "Success: $request")
            }, onError = { request, errorResult ->
                val throwable = errorResult.throwable
                Log.e("PhotoDetailActivity", "Error: ${throwable.message}")
                Log.e("PhotoDetailActivity", Log.getStackTraceString(throwable))
            })
        }
    }
}