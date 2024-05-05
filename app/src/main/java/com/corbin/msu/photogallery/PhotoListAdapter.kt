package com.corbin.msu.photogallery

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.corbin.msu.photogallery.databinding.ListItemGalleryBinding

class PhotoViewHolder(
    private val binding: ListItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var galleryItem: GalleryItem

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(galleryItem: GalleryItem) {
        this.galleryItem = galleryItem
        Log.d("PhotoViewHolder", "Loading item: " + galleryItem.url)

        binding.itemImageView.post {
            try {
                val request = ImageRequest.Builder(binding.root.context)
                    .data(galleryItem.url)
                    .size(binding.itemImageView.width, binding.itemImageView.height)
                    .target(binding.itemImageView)
                    .build()

                Log.d("PhotoViewHolder", "Request: $request")

                binding.root.context.imageLoader.enqueue(request)
            } catch (e: Exception) {
                Log.d("PhotoViewHolder", "Error: $e")
                e.printStackTrace()
            }
        }
    }

    override fun onClick(v: View?) {
        val context = itemView.context
        val intent = Intent(context, PhotoDetailActivity::class.java).apply {
            putExtra("url", galleryItem.url)
            putExtra("title", galleryItem.title)
        }
        context.startActivity(intent)
    }
}

class PhotoListAdapter : PagingDataAdapter<GalleryItem, PhotoViewHolder>(GalleryItemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    object GalleryItemComparator : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            // Id is unique.
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }
    }
}