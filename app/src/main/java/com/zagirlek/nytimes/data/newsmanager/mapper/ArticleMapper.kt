package com.zagirlek.nytimes.data.newsmanager.mapper

import com.zagirlek.nytimes.data.network.news.dto.ArticleDTO
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.model.NewsPage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun NewsPageDTO.toNewsPage(): NewsPage = NewsPage(
    nextPage = nextPage,
    articleLiteList = newsList.map { it.toArticleLite() }
)

fun ArticleDTO.toArticleLite(): ArticleLite = ArticleLite(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = LocalDateTime.parse(pubDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
)

fun ArticleLite.toArticleFull(fullText: String): ArticleFull = ArticleFull(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate,
    fullText = fullText
)