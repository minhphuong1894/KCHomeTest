package com.steven.data.mappers

import com.steven.data.models.ArticleDTO
import com.steven.domain.models.Article

fun ArticleDTO.toDomain(): Article {
    return Article(
        index = index,
        title = title,
        date = date,
        description = description
    )
}