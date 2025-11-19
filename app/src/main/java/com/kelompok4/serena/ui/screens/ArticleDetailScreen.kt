package com.example.serena.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage // Import Coil
import com.kelompok4.serena.R
import com.example.serena.ui.screens.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    navController: NavHostController,
    title: String,       // Terima title
    imageUrl: String,    // Terima imageUrl
    description: String  // Terima description
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: handle bookmark */ }) {
                        Icon(imageVector = Icons.Outlined.Bookmark, contentDescription = "Bookmark")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            // Gunakan Coil AsyncImage
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.serena_logo),
                error = painterResource(id = R.drawable.serena_logo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = title, // Gunakan title dari parameter
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = description, // Gunakan description dari parameter
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}