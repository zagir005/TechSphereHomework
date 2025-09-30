package com.zagirlek.nytimes.core.model

import com.google.gson.annotations.SerializedName
import com.zagirlek.nytimes.R

enum class NewsCategory(val resId: Int) {
    @SerializedName("business")
    BUSINESS(R.string.business),

    @SerializedName("crime")
    CRIME(R.string.crime),

    @SerializedName("domestic")
    DOMESTIC(R.string.domestic),

    @SerializedName("education")
    EDUCATION(R.string.education),

    @SerializedName("entertainment")
    ENTERTAINMENT(R.string.entertainment),

    @SerializedName("environment")
    ENVIRONMENT(R.string.environment),

    @SerializedName("food")
    FOOD(R.string.food),

    @SerializedName("health")
    HEALTH(R.string.health),

    @SerializedName("lifestyle")
    LIFESTYLE(R.string.lifestyle),

    @SerializedName("politics")
    POLITICS(R.string.politics),

    @SerializedName("science")
    SCIENCE(R.string.science),

    @SerializedName("sports")
    SPORTS(R.string.sports),

    @SerializedName("technology")
    TECHNOLOGY(R.string.technology),

    @SerializedName("business")
    TOP(R.string.top),

    @SerializedName("tourism")
    TOURISM(R.string.tourism),

    @SerializedName("world")
    WORLD(R.string.world),

    @SerializedName("other")
    OTHER(R.string.other)
}

