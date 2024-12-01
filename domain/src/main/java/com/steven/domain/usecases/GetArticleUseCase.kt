package com.steven.domain.usecases

import com.steven.domain.models.Article
import com.steven.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend fun execute(sortBy: SortType): Flow<List<Article>> {
        return repository.getArticle(sortBy)
    }
}

enum class SortType {
    INDEX, TITLE, DATE
}