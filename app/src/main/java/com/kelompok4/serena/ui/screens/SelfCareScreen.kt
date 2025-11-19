package com.example.serena.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.serena.ui.components.ActivityCard
import com.example.serena.ui.components.ArticleCard
import com.example.serena.ui.components.RecommendationCard
import com.example.serena.ui.components.SectionHeader
import com.kelompok4.serena.ui.theme.*
import com.kelompok4.serena.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok4.serena.ui.viewmodel.SelfCareViewModel
import com.kelompok4.serena.ui.viewmodel.formatYouTubeDate
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfCareScreen(
    navController: NavHostController? = null,
    viewModel: SelfCareViewModel = viewModel() // Injeksi ViewModel
) {
    val searchQuery = remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState() // Ambil UI State

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (uiState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error: ${uiState.errorMessage}")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Search bar
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    placeholder = { Text(text = "Cari") },
                    leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Recommendation Card
                RecommendationCard(
                    painterRes = R.drawable.onboarding_1, // Ini bisa tetap hardcoded
                    title = "Serena punya rekomendasi buat kamu!",
                    message = "Kondisi mental kamu sedang dalam keadaan baik. Jaga kesehatanmu dengan rekomendasi artikel dan kegiatan dari Serena.",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Artikel Section
                SectionHeader(
                    title = "Artikel",
                    onClickSeeAll = { /* navController?.navigate("articles") */ } // Nonaktifkan dulu
                )
                LazyRow(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
                ) {
                    items(uiState.articles) { article ->
                        ArticleCard(
                            imageUrl = article.urlToImage, // Gunakan URL
                            title = article.title,
                            isVertical = false,
                            onClick = {
                                // Encode parameter untuk navigasi
                                val title = URLEncoder.encode(article.title, "UTF-8")
                                val imageUrl = URLEncoder.encode(article.urlToImage ?: "", "UTF-8")
                                val description = URLEncoder.encode(article.description ?: article.content ?: "", "UTF-8")
                                navController?.navigate("articleDetail/$title/$imageUrl/$description")
                            }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                // Activities Section
                SectionHeader(
                    title = "Kegiatan",
                    onClickSeeAll = { /* navController?.navigate("activities") */ } // Nonaktifkan dulu
                )
            }
            items(uiState.activities) { activity ->
                ActivityCard(
                    imageUrl = activity.snippet.thumbnails.high.url, // URL Thumbnail
                    title = activity.snippet.title, // Judul Video
                    date = formatYouTubeDate(activity.snippet.publishedAt), // Tanggal Upload
                    views = activity.statistics.viewCount, // Views
                    likes = activity.statistics.likeCount, // Likes
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    onClick = {
                        val videoId = activity.id
                        val title = URLEncoder.encode(activity.snippet.title, "UTF-8")
                        val date = URLEncoder.encode(formatYouTubeDate(activity.snippet.publishedAt), "UTF-8")
                        val description = URLEncoder.encode(activity.snippet.description, "UTF-8")
                        navController?.navigate("activityDetail/$videoId/$title/$date/$description")
                    }
                )
            }
        }
    }
}