package com.kelompok4.serena.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Klien untuk NewsAPI
    val newsApi: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.NEWS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
            .create(NewsApiService::class.java)
    }

    // Klien untuk YouTube API
    val youtubeApi: YouTubeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.YOUTUBE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
            .create(YouTubeApiService::class.java)
    }

    // Helper untuk membuat OkHttpClient dengan logging
    private fun createClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Lihat log di Logcat
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}