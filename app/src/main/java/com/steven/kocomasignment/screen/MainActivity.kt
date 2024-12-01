package com.steven.kocomasignment.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.steven.kocomasignment.common.Constants
import com.steven.kocomasignment.navigation.NavigationItem
import com.steven.kocomasignment.screen.articleDetails.ArticleDetailsScreen
import com.steven.kocomasignment.screen.articleList.ArticleListScreen
import com.steven.kocomasignment.ui.theme.KOCOMAsignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KOCOMAsignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.ArticleScreen.route
                    ) {
                        composable(route = NavigationItem.ArticleScreen.route) {
                            ArticleListScreen(navController)
                        }
                        composable(
                            route = NavigationItem.ArticleDetailsScreen.route,
                            arguments = listOf(
                                navArgument(Constants.PARAM_ARTICLE) {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val articleJson =
                                backStackEntry.arguments?.getString(Constants.PARAM_ARTICLE)
                            if (articleJson != null) {
                                ArticleDetailsScreen(navController, articleJson)
                            }
                        }
                    }
                }
            }
        }
    }
}
