package com.kelompok4.serena.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok4.serena.data.ApiArticle
import com.kelompok4.serena.data.YouTubeVideoItem
import com.kelompok4.serena.networking.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// Data class untuk menampung state UI
data class SelfCareUiState(
    val articles: List<ApiArticle> = emptyList(),
    val activities: List<YouTubeVideoItem> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class SelfCareViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SelfCareUiState())
    val uiState: StateFlow<SelfCareUiState> = _uiState.asStateFlow()

    init {
        fetchAllData()
    }

    private fun fetchAllData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                fetchArticles()
                fetchActivities()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                Log.e("SelfCareViewModel", "Error fetching data", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Gagal memuat data: ${e.message}"
                )
            }
        }
    }

    private suspend fun fetchArticles() {
        val response = RetrofitInstance.newsApi.getMentalHealthNews()
        if (response.isSuccessful && response.body() != null) {
            _uiState.value = _uiState.value.copy(
                articles = response.body()!!.articles.filter {
                    // Filter artikel yang tidak punya gambar atau judul
                    it.urlToImage != null && it.title != "[Removed]"
                }
            )
        } else {
            throw Exception("Gagal memuat artikel: ${response.message()}")
        }
    }

    private suspend fun fetchActivities() {
        // 1. Cari video untuk mendapatkan ID
        val searchResponse = RetrofitInstance.youtubeApi.searchMentalHealthVideos()
        if (searchResponse.isSuccessful && searchResponse.body() != null) {
            val videoIds = searchResponse.body()!!.items.map { it.id.videoId }
            if (videoIds.isEmpty()) return

            val videoIdsString = videoIds.joinToString(",")

            // 2. Dapatkan detail video dari ID yang ditemukan
            val detailsResponse = RetrofitInstance.youtubeApi.getVideoDetails(videoIds = videoIdsString)
            if (detailsResponse.isSuccessful && detailsResponse.body() != null) {
                _uiState.value = _uiState.value.copy(
                    activities = detailsResponse.body()!!.items
                )
            } else {
                throw Exception("Gagal memuat detail video: ${detailsResponse.message()}")
            }
        } else {
            throw Exception("Gagal mencari video: ${searchResponse.message()}")
        }
    }
}

// Helper function untuk memformat tanggal (opsional)
fun formatYouTubeDate(isoDate: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(isoDate)
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale("id", "ID"))
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "Tanggal tidak diketahui"
    }
}