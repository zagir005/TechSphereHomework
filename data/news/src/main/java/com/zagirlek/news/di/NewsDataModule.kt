package com.zagirlek.news.di

import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.local.news.dao.ArticleFullDao
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.local.news.dao.ArticleStatusDao
import com.zagirlek.news.repository.ArticleRepository
import com.zagirlek.news.repository.ArticleRepositoryImpl
import com.zagirlek.news.repository.ArticleStatusRepository
import com.zagirlek.news.repository.ArticleStatusRepositoryImpl
import com.zagirlek.news.repository.NewsRepository
import com.zagirlek.news.repository.NewsRepositoryImpl
import com.zagirlek.remote.extractor.RemoteNewsExtractorSource
import com.zagirlek.remote.news.RemoteNewsSource

class NewsDataModule(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val articleFullDao: ArticleFullDao,
    private val articleStatusDao: ArticleStatusDao,
    private val articleLiteDao: ArticleLiteDao,
    private val remoteNewsSource: RemoteNewsSource,
    private val remoteExtractorNewsSource: RemoteNewsExtractorSource,
) {
    val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            networkConnectionChecker = networkConnectionChecker,
            articleFullDao = articleFullDao,
            articleStatusDao = articleStatusDao,
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource,
            remoteExtractorNewsSource= remoteExtractorNewsSource,
        )
    }

    val articleStatusRepository: ArticleStatusRepository by lazy {
        ArticleStatusRepositoryImpl(
            articleStatusDao = articleStatusDao,
            articleRepository = articleRepository
        )
    }

    val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource
        )
    }
}