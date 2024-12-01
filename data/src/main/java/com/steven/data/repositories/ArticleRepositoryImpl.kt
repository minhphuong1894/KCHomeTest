package com.steven.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.steven.data.mappers.toDomain
import com.steven.data.models.ArticleDTO
import com.steven.domain.models.Article
import com.steven.domain.repositories.ArticleRepository
import com.steven.domain.usecases.SortType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ArticleRepository {

    override suspend fun getArticle(sortBy: SortType): Flow<List<Article>> = flow {
        try {
            val jsonString = loadJsonFromAssets()
            val articleDTOs: List<ArticleDTO> =
                Gson().fromJson(jsonString, object : TypeToken<List<ArticleDTO>>() {}.type)
            val articles = articleDTOs.map { it.toDomain() }

            // Sorting
            val sortedList = when (sortBy) {
                SortType.INDEX -> articles.sortedByDescending { it.index }
                SortType.TITLE -> articles.sortedByDescending { it.title }
                SortType.DATE -> articles.sortedByDescending { it.date }
            }

            emit(sortedList)
        } catch (e: IOException) {
            emit(emptyList())
        } catch (e: JsonSyntaxException) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    private fun loadJsonFromAssets(): String {
        return context.assets.open("articles.json").bufferedReader().use { it.readText() }
    }
}