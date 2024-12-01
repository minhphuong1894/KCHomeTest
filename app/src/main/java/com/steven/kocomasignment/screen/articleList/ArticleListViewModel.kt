package com.steven.kocomasignment.screen.articleList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.domain.models.Article
import com.steven.domain.usecases.GetArticleUseCase
import com.steven.domain.usecases.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    init {
        fetchArticles(SortType.INDEX) // Default sort
    }

    // Fetch articles based on selected sort type
    fun fetchArticles(sortBy: SortType) {
        viewModelScope.launch {
            getArticleUseCase.execute(sortBy)
                .collect { articles ->
                    Log.e("ArticleListViewModel", "Articles fetched: $articles")
                    _articles.value = articles
                }
        }
    }
}