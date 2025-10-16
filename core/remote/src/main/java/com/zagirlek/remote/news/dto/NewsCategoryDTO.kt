package com.zagirlek.remote.news.dto

import com.google.gson.annotations.SerializedName

enum class NewsCategoryDTO() {
    @SerializedName("business")
    BUSINESS,

    @SerializedName("crime")
    CRIME,

    @SerializedName("domestic")
    DOMESTIC,

    @SerializedName("education")
    EDUCATION,

    @SerializedName("entertainment")
    ENTERTAINMENT,

    @SerializedName("environment")
    ENVIRONMENT,

    @SerializedName("food")
    FOOD,

    @SerializedName("health")
    HEALTH,

    @SerializedName("lifestyle")
    LIFESTYLE,

    @SerializedName("politics")
    POLITICS,

    @SerializedName("science")
    SCIENCE,

    @SerializedName("sports")
    SPORTS,

    @SerializedName("technology")
    TECHNOLOGY,

    @SerializedName("top")
    TOP,

    @SerializedName("tourism")
    TOURISM,

    @SerializedName("world")
    WORLD,

    @SerializedName("other")
    OTHER;


}
