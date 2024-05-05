package com.corbin.msu.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PhotoPagingSource(
    private val photoRepository: PhotoRepository
) : PagingSource<Int, GalleryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        return try {
            val response = photoRepository.fetchPhotos()
            LoadResult.Page(
                data = response,
                prevKey = null, // No previous page
                nextKey = null  // No next page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition
    }
}