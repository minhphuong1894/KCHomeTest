package com.steven.kocomasignment.screen.articleList

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.steven.domain.usecases.SortType
import com.steven.kocomasignment.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    navController: NavController,
    articleListViewModel: ArticleListViewModel = hiltViewModel()
) {
    val articles = articleListViewModel.articles.collectAsState().value
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSortType by remember { mutableStateOf(SortType.INDEX) } // default sorting

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Article List",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Sort Articles"
                        )
                    }

                    if (isExpanded) {
                        Popup(
                            alignment = Alignment.TopEnd,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            // A Box to hold the dropdown menu items
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .border(
                                        1.dp,
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .shadow(4.dp, shape = MaterialTheme.shapes.medium, clip = false)
                                    .padding(8.dp)
                                    .width(200.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(MaterialTheme.shapes.medium)
                                ) {
                                    SortType.values().forEach { sortType ->
                                        Text(
                                            text = sortType.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    selectedSortType = sortType
                                                    articleListViewModel.fetchArticles(sortType)
                                                    isExpanded = false
                                                }
                                                .padding(12.dp)
                                                .background(
                                                    if (selectedSortType == sortType)
                                                        MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.1f
                                                        )
                                                    else Color.Transparent
                                                )
                                                .padding(horizontal = 16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.Transparent)
        ) {
            if (articles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No articles available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(articles.size) { index ->
                        ArticleItem(
                            article = articles[index],
                            onClick = {
                                val articleJson = Uri.encode(Gson().toJson(articles[index]))
                                navController.navigate(
                                    NavigationItem.ArticleDetailsScreen.createRoute(articleJson)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}