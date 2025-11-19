package com.kelompok4.serena.data

data class YouTubeSearchResponse(
    val items: List<YouTubeSearchItem>
)

data class YouTubeSearchItem(
    val id: YouTubeVideoId
)

data class YouTubeVideoId(
    val videoId: String
)

// Model untuk respons detail video YouTube (langkah 2)
data class YouTubeVideoListResponse(
    val items: List<YouTubeVideoItem>
)

data class YouTubeVideoItem(
    val id: String, // Ini adalah videoId
    val snippet: VideoSnippet,
    val statistics: VideoStatistics
)

data class VideoSnippet(
    val publishedAt: String, // Tanggal upload
    val title: String,
    val description: String,
    val thumbnails: VideoThumbnails
)

data class VideoThumbnails(
    val high: ThumbnailDetails // Ambil gambar resolusi tinggi
)

data class ThumbnailDetails(
    val url: String // URL thumbnail
)

data class VideoStatistics(
    val viewCount: String,
    val likeCount: String
)