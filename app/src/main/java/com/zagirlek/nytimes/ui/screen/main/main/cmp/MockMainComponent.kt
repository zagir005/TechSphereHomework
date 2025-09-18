package com.zagirlek.nytimes.ui.screen.main.main.cmp

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.MutableValue
import com.zagirlek.nytimes.ui.screen.main.main.MainComponent

//class MockMainComponent(): MainComponent {
//    private val _pages = MutableValue(
//        ChildPages(
//            items = listOf(
//                ChildPages.Child(
//                    configuration = "weather",
//                    instance = MainComponent.Child.Weather(MockWeatherComponent())
//                ),
//                ChildPages.Child(
//                    configuration = "news",
//                    instance = MainComponent.Child.News(MockNewsComponent())
//                ),
//                ChildPages.Child(
//                    configuration = "favorites",
//                    instance = MainComponent.Child.Favorites(MockFavoritesComponent())
//                )
//            ),
//            selectedIndex = 0
//        )
//    )
//
//    override val pages: Value<ChildPages<*, MainComponent.Child>> = _pages
//
//    override fun selectPage(index: Int) {
//        _pages.value = _pages.value.copy(selectedIndex = index)
//    }
//}