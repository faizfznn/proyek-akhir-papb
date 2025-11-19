package com.kelompok4.serena.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.serena.ui.screens.ActivityDetailScreen // Pastikan import ini benar
import com.example.serena.ui.screens.ArticleDetailScreen // Pastikan import ini benar
import com.kelompok4.serena.ui.screens.LoginScreen
import com.kelompok4.serena.ui.screens.MainScreen
import com.kelompok4.serena.ui.screens.OnboardingScreen
import com.kelompok4.serena.ui.screens.RegisterScreen
import com.kelompok4.serena.ui.screens.SplashScreen
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun RootNavGraph(
    navController: NavHostController,
    isFirstTime: Boolean,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigate = {
                    when {
                        isFirstTime -> navController.navigate(Routes.ONBOARDING) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                        !isLoggedIn -> navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                        else -> {
                            // Saat sudah login, pastikan kirim email user aktif
                            val email = "current_user@example.com" // nanti ambil dari session
                            navController.navigate("main/$email") {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }


        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onNavigateToMain = { email ->
                    navController.navigate("main/$email") {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(navController = navController)
        }

        composable(
            route = Routes.MAIN,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            MainScreen(userEmail = email)
        }

        // --- PINDAHKAN KODE ARTIKEL DAN ACTIVITY DETAIL KE SINI ---

        composable(
            route = "articleDetail/{title}/{imageUrl}/{description}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""
            val imageUrl = backStackEntry.arguments?.getString("imageUrl")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""
            val description = backStackEntry.arguments?.getString("description")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""

            // Pastikan memanggil ArticleDetailScreen dengan parameter yang benar
            ArticleDetailScreen(
                navController = navController,
                title = title,
                imageUrl = imageUrl,
                description = description
            )
        }

        composable(
            route = "activityDetail/{videoId}/{title}/{uploadDate}/{description}",
            arguments = listOf(
                navArgument("videoId") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("uploadDate") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            val title = backStackEntry.arguments?.getString("title")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""
            val uploadDate = backStackEntry.arguments?.getString("uploadDate")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""
            val description = backStackEntry.arguments?.getString("description")?.let { URLDecoder.decode(it, "UTF-8") } ?: ""

            // Pastikan memanggil ActivityDetailScreen dengan parameter yang benar
            ActivityDetailScreen(
                navController = navController,
                videoId = videoId,
                title = title,
                uploadDate = uploadDate,
                description = description
            )
        }

        // composable(Routes.KONSELING) {
        //    CounselingScheduleScreen(navController = navController)
        // }

    } // <-- NavHost ditutup di sini
}