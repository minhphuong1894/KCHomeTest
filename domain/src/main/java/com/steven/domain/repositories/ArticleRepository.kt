package com.steven.domain.repositories

import com.steven.domain.models.Article
import com.steven.domain.usecases.SortType
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    suspend fun getArticle(sortBy: SortType): Flow<List<Article>>

}