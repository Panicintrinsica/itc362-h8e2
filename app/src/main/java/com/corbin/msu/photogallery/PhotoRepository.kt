package com.corbin.msu.photogallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.FileInputStream
import java.security.cert.X509Certificate
import java.util.Properties
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class PhotoRepository() {
    private val nasaApi: NasaApi


    val unsafeOkHttpClient: OkHttpClient.Builder
        get() = try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            OkHttpClient.Builder().apply {
                sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                hostnameVerifier { _, _ -> true }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/")
            .client(unsafeOkHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        nasaApi = retrofit.create()
    }



    fun getPhotos(): Flow<PagingData<GalleryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { PhotoPagingSource(this) }
        ).flow
    }

    suspend fun fetchPhotos(): List<GalleryItem> =
        nasaApi.fetchPhotos()
}

//class PhotoRepository {
//    private val nasaApi: NasaApi
//
//    init {
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl("https://api.nasa.gov/planetary/")
//            .client(unsafeOkHttpClient.build())
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//        nasaApi = retrofit.create()
//    }
//
//    fun getPhotos(): Flow<PagingData<GalleryItem>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
//            pagingSourceFactory = { PhotoPagingSource(this) }
//        ).flow
//    }
//
//    suspend fun fetchPhotos(): NasaResponse =
//        nasaApi.fetchPhotos()
//}

