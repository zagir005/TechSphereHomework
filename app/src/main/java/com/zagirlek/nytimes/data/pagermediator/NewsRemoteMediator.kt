package com.zagirlek.nytimes.data.pagermediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.RemoteKeyDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.local.news.entity.RemoteKeyEntity
import com.zagirlek.nytimes.data.newsmanager.NewsManager
import com.zagirlek.nytimes.domain.model.NewsFilter
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val filters: NewsFilter,
    private val database: NyTimesDatabase,
    private val newsManager: NewsManager
) : RemoteMediator<Int, ArticleLiteEntity>() {

    private val remoteKeyDao: RemoteKeyDao = database.remoteKeyDao()
    private val articleDao: ArticleLiteDao = database.articleLiteDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleLiteEntity>
    ): MediatorResult {
        val pageKey = when (loadType) {
            LoadType.REFRESH -> {
                // Для курсорной пагинации стартуем с пустой строки или из фильтра, если он задан явно
                filters.page.ifEmpty { "" }
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val key = remoteKeyDao.remoteKeysByFilters(filters)?.nextPageKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                key
            }
        }

        val response = try {
            newsManager.getLatestNews(
                category = filters.category?.let { listOf(it) } ?: emptyList(),
                page = pageKey,
                titleQuery = filters.titleQuery
            )
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }

        return try {
            val nextPageKey = response.nextPage.ifEmpty { null }
            val prevPageKey: String? = null // API не возвращает prev

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // Кэш статей не трогаем, только сбросим remote key под эти фильтры
                    remoteKeyDao.deleteByFilters(filters)
                }

                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        filters = filters,
                        pageKey = pageKey,
                        nextPageKey = nextPageKey,
                        previousPageKey = prevPageKey
                    )
                )

                articleDao.insertArticles(
                    response.articleLiteList.map { it.toArticleLiteEntity() }
                )
            }

            MediatorResult.Success(endOfPaginationReached = nextPageKey == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}