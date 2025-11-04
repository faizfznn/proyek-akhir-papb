package com.example.serena.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.serena.ui.screens.ActivityDetailScreen
import com.example.serena.ui.screens.ArticleDetailScreen
import com.example.serena.ui.screens.ArticleListScreen
import com.example.serena.ui.screens.SelfCareScreen

/**
 * Defines the navigation graph for the Self Care feature.  This should be
 * hosted inside your application's main [NavHost] and can be combined
 * with other graphs as needed.  All routes used here are defined as
 * constants for ease of reuse.
 */
object Routes {
    const val SelfCare = "selfCare"
    const val Articles = "articles"
    const val ArticleDetail = "articleDetail/{id}"
    const val Activities = "activities"
    const val ActivityDetail = "activityDetail/{id}"
}

@Composable
fun SelfCareNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SelfCare
    ) {
        composable(Routes.SelfCare) {
            SelfCareScreen(navController)
        }
        composable(Routes.Articles) {
            ArticleListScreen(navController)
        }
        composable(
            route = Routes.ArticleDetail,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ArticleDetailScreen(navController, id)
        }
        composable(Routes.Activities) {
            // Could be a separate list screen for activities; for now we reuse SelfCare
            SelfCareScreen(navController)
        }
        composable(
            route = Routes.ActivityDetail,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ActivityDetailScreen(navController, id)
        }
    }
}