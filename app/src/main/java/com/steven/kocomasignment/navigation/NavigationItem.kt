package com.steven.kocomasignment.navigation

import com.steven.kocomasignment.common.Constants

sealed class NavigationItem(val route: String) {

    data object ArticleScreen : NavigationItem("article_screen")

    data object ArticleDetailsScreen :
        NavigationItem("article_details_screen/{${Constants.PARAM_ARTICLE}}") {
        fun createRoute(articleJson: String) = "article_details_screen/$articleJson"
    }
}