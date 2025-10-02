package com.zagirlek.nytimes.data.pagermediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zagirlek.nytimes.core.error.NetworkError
import com.zagirlek.nytimes.core.error.ServerError
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleStatusDao
import com.zagirlek.nytimes.data.local.news.dao.RemoteKeyDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.local.news.entity.RemoteKeyEntity
import com.zagirlek.nytimes.data.mapper.toEntity
import com.zagirlek.nytimes.data.newsmanager.NewsManager
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val filters: NewsFilter,
    private val database: NyTimesDatabase,
    private val newsManager: NewsManager
) : RemoteMediator<Int, ArticleLiteEntity>() {

    private val remoteKeyDao: RemoteKeyDao = database.remoteKeyDao()
    private val articleDao: ArticleLiteDao = database.articleDao()
    private val articleStatusDao: ArticleStatusDao = database.articleStatusDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleLiteEntity>
    ): MediatorResult {
        val pageKey = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val key = remoteKeyDao.remoteKeysByFilters(filters)?.nextPageKey
                if (key == null) return MediatorResult.Success(endOfPaginationReached = true)
                key
            }
        }

        return try {
            val response = newsManager.getLatestNews(
                category = filters.category,
                page = pageKey,
                titleQuery = filters.titleQuery
            )
            val nextPageKey = response.nextPage
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    val ids = articleStatusDao.getAllNonDefaultArticleLocalStatus().map { it.articleId }
                    articleDao.deleteAllExcept(ids)
                }

                articleDao.insertArticles(response.newsList.map { it.toEntity() })

                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        filters = filters,
                        nextPageKey = nextPageKey
                    )
                )
            }
            MediatorResult.Success(endOfPaginationReached = nextPageKey == null)
        } catch(e: IOException) {
            MediatorResult.Error(NetworkError())
        }catch (e: ServerError){
            MediatorResult.Error(e)
        } catch (e: Exception){
            MediatorResult.Error(e)
        }
    }
}