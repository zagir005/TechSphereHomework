package com.zagirlek.remote.news

import com.zagirlek.common.error.NewsApiError
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.utils.mapError
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.remote.news.dto.NewsCategoryDTO
import com.zagirlek.remote.news.dto.NewsPageDTO
import com.zagirlek.remote.news.service.NewsService
import retrofit2.HttpException

internal class RemoteNewsSourceImpl(
    private val newsService: NewsService,
    private val newsDomains: String
): RemoteNewsSource {
    override suspend fun getLatestNews(
        category: NewsCategory?,
        page: String?,
        titleQuery: String?,
        id: String?
    ): Result<NewsPageDTO> = runCatchingCancellable {
        if (id != null)
            newsService.latest(
                id = id
            )
        else
            newsService.latest(
                category = category?.let { listOf(category.toDTO().toString()) },
                page = page,
                titleQuery = titleQuery,
                domain = newsDomains,
            )
    }.mapError {
        it.toNewsApiError()
    }

    private fun NewsCategory.toDTO() =
        NewsCategoryDTO.valueOf(this.name)

    private fun Throwable.toNewsApiError(): NewsApiError =
        if(this is HttpException)
            when(this.code()){
                401 -> NewsApiError.Unauthorized
                422 -> NewsApiError.InvalidId
                429 -> NewsApiError.TooManyRequests
                500 -> NewsApiError.ServerError
                else -> NewsApiError.UnknownApiError(cause = this)
            }
        else NewsApiError.UnknownError(cause = this)
}