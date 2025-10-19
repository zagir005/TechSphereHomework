package com.zagirlek.remote.news.source

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.remote.news.dto.ArticleDTO
import com.zagirlek.remote.news.dto.NewsCategoryDTO
import com.zagirlek.remote.news.dto.NewsPageDTO

class MockNewsSource: RemoteNewsSource {
    var currPage = 0

    override suspend fun getLatestNews(
        category: NewsCategory?,
        page: String?,
        titleQuery: String?,
        id: String?
    ): Result<NewsPageDTO> =
        Result.success(generateArticle())

    private fun generateArticle(): NewsPageDTO {
        currPage += 1
        return NewsPageDTO(
            totalResults = 10,
            newsList = List(10) {
                ArticleDTO(
                    articleId = (it * currPage).toString(),
                    title = "Lorem Ipsum",
                    link = "link",
                    description = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...",
                    sourceId = "Lorem Ipsum",
                    sourceName = "Lorem Ipsum",
                    sourceIconUrl = null,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0e/Nytimes_hq.jpg",
                    creator = listOf("Zagir"),
                    pubDate = "2025-10-19 02:33:00",
                    category = listOf(NewsCategoryDTO.entries.toTypedArray().random())
                )
            },
            nextPage = currPage.toString()
        )
    }
}