package com.zagirlek.remote.news.dto

import com.google.gson.annotations.SerializedName

data class ArticleDTO(
    @SerializedName("article_id") val articleId: String,
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("description") val description: String?,
    @SerializedName("source_id") val sourceId: String?,
    @SerializedName("source_name") val sourceName: String,
    @SerializedName("source_icon") val sourceIconUrl: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("creator") val creator: List<String?>?,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("category") val category: List<NewsCategoryDTO>?
)
