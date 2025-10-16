package com.zagirlek.ui.elements

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.ui.R

enum class NewsCategoryUi(val resId: Int) {
    BUSINESS(R.string.business),
    CRIME(R.string.crime),
    DOMESTIC(R.string.domestic),
    EDUCATION(R.string.education),
    ENTERTAINMENT(R.string.entertainment),
    ENVIRONMENT(R.string.environment),
    FOOD(R.string.food),
    HEALTH(R.string.health),
    LIFESTYLE(R.string.lifestyle),
    POLITICS(R.string.politics),
    SCIENCE(R.string.science,),
    SPORTS(R.string.sports),
    TECHNOLOGY(R.string.technology),
    TOP(R.string.top),
    TOURISM(R.string.tourism),
    WORLD(R.string.world),
    OTHER(R.string.other)
}

fun NewsCategoryUi.toCategory(): NewsCategory =
    NewsCategory.valueOf(this.name)

fun NewsCategory.toUiCategory(): NewsCategoryUi =
    NewsCategoryUi.valueOf(this.name)