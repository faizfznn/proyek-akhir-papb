package com.example.serena.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDetailScreen(
    navController: NavHostController,
    videoId: String,     // Terima videoId
    title: String,       // Terima title
    uploadDate: String,  // Terima uploadDate
    description: String  // Terima description
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Kegiatan") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Buat kolom bisa di-scroll
                .padding(paddingValues)
        ) {
            // Pengganti Image, gunakan WebView untuk memutar video YouTube
            YouTubePlayer(
                videoId = videoId,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp) // Sesuaikan tinggi player
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = title, // Gunakan title dari parameter
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    text = uploadDate, // Gunakan uploadDate dari parameter
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = description, // Gunakan description dari parameter
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubePlayer(
    videoId: String,
    modifier: Modifier = Modifier
) {
    // URL embed YouTube
    val embedHtml = """
        <html>
            <body style="margin:0;padding:0;overflow:hidden;">
                <iframe 
                    width="100%" 
                    height="100%" 
                    src="https://www.youtube.com/embed/$videoId" 
                    frameborder="0" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                    allowfullscreen>
                </iframe>
            </body>
        </html>
    """.trimIndent()

    // Gunakan AndroidView untuk menampung WebView
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true // Wajib untuk memutar video
                webViewClient = WebViewClient() // Wajib agar tidak membuka browser
                loadData(embedHtml, "text/html", "utf-8")
            }
        },
        modifier = modifier
    )
}