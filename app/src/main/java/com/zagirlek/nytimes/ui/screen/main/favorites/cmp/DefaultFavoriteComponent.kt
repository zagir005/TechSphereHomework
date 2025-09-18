package com.zagirlek.nytimes.ui.screen.main.favorites.cmp

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent

class DefaultFavoriteComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, FavoritesComponent {
}