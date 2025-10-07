package com.zagirlek.nytimes.core.model

import com.google.gson.annotations.SerializedName
import com.zagirlek.nytimes.R

enum class NewsCategory(val resId: Int, val serializedName: String) {
    @SerializedName("business")
    BUSINESS(R.string.business, "business"),

    @SerializedName("crime")
    CRIME(R.string.crime, "crime"),

    @SerializedName("domestic")
    DOMESTIC(R.string.domestic, "domestic"),

    @SerializedName("education")
    EDUCATION(R.string.education, "education"),

    @SerializedName("entertainment")
    ENTERTAINMENT(R.string.entertainment, "entertainment"),

    @SerializedName("environment")
    ENVIRONMENT(R.string.environment, "environment"),

    @SerializedName("food")
    FOOD(R.string.food, "food"),

    @SerializedName("health")
    HEALTH(R.string.health, "health"),

    @SerializedName("lifestyle")
    LIFESTYLE(R.string.lifestyle, "lifestyle"),

    @SerializedName("politics")
    POLITICS(R.string.politics, "politics"),

    @SerializedName("science")
    SCIENCE(R.string.science, "science"),

    @SerializedName("sports")
    SPORTS(R.string.sports, "sports"),

    @SerializedName("technology")
    TECHNOLOGY(R.string.technology, "technology"),

    @SerializedName("top")
    TOP(R.string.top, "top"),

    @SerializedName("tourism")
    TOURISM(R.string.tourism, "tourism"),

    @SerializedName("world")
    WORLD(R.string.world, "world"),

    @SerializedName("other")
    OTHER(R.string.other, "other")
}

