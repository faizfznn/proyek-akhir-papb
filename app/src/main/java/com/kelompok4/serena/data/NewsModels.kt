package com.kelompok4.serena.data

// Model sederhana untuk respons NewsAPI
data class NewsApiResponse(
    val articles: List<ApiArticle>
)

data class ApiArticle(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val content: String?
)