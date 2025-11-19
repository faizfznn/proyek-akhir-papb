package com.kelompok4.serena.networking

import com.kelompok4.serena.data.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getMentalHealthNews(
        @Query("q") query: String = "mental health OR self-care",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("language") language: String = "id", // Cari berita berbahasa Indonesia
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = ApiConstants.NEWS_API_KEY
    ): Response<NewsApiResponse>
}