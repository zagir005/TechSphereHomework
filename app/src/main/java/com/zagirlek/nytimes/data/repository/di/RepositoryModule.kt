package com.zagirlek.nytimes.data.repository.di

import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.local.news.dao.ArticleFullDao
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.local.news.dao.ArticleStatusDao
import com.zagirlek.news.repository.ArticleRepository
import com.zagirlek.news.repository.ArticleStatusRepository
import com.zagirlek.news.repository.NewsRepository
import com.zagirlek.nytimes.data.repository.ArticleRepositoryImpl
import com.zagirlek.nytimes.data.repository.ArticleStatusRepositoryImpl
import com.zagirlek.nytimes.data.repository.NewsRepositoryImpl
import com.zagirlek.remote.extractor.RemoteNewsExtractorSource
import com.zagirlek.remote.news.RemoteNewsSource

class RepositoryModule(
    private val remoteNewsSource: RemoteNewsSource,
    private val remoteNewsExtractorSource: RemoteNewsExtractorSource,
    private val articleFullDao: ArticleFullDao,
    private val articleLiteDao: ArticleLiteDao,
    private val articleStatusDao: ArticleStatusDao,
    private val connectionChecker: NetworkConnectionChecker
) {
    val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource
        )
    }
    val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            articleFullDao = articleFullDao,
            articleStatusDao = articleStatusDao,
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource,
            remoteExtractorNewsSource = remoteNewsExtractorSource,
            networkConnectionChecker = connectionChecker
        )
    }

    val articleStatusRepository: ArticleStatusRepository by lazy {
        ArticleStatusRepositoryImpl(
            articleStatusDao = articleStatusDao,
            articleRepository = articleRepository
        )
    }
}