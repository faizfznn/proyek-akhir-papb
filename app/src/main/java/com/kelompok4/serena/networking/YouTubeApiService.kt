package com.kelompok4.serena.networking

import com.kelompok4.serena.data.YouTubeSearchResponse
import com.kelompok4.serena.data.YouTubeVideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {

    // Langkah 1: Cari video
    @GET("youtube/v3/search")
    suspend fun searchMentalHealthVideos(
        @Query("part") part: String = "snippet",
        @Query("q") query: String = "meditasi OR self-care indonesia",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String = ApiConstants.YOUTUBE_API_KEY
    ): Response<YouTubeSearchResponse>

    // Langkah 2: Dapatkan detail (termasuk statistik) untuk video
    @GET("youtube/v3/videos")
    suspend fun getVideoDetails(
        @Query("part") part: String = "snippet,statistics",
        @Query("id") videoIds: String, // ID video, dipisah koma. Cth: "id1,id2,id3"
        @Query("key") apiKey: String = ApiConstants.YOUTUBE_API_KEY
    ): Response<YouTubeVideoListResponse>
}