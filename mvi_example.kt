// ===== БАЗОВЫЕ ИНТЕРФЕЙСЫ =====

interface MviStore<Intent, State, Effect> {
    val state: StateFlow<State>
    val effects: SharedFlow<Effect>
    
    fun dispatch(intent: Intent)
    fun getCurrentState(): State
}

interface MviReducer<Intent, State, Effect> {
    suspend fun reduce(currentState: State, intent: Intent): MviResult<State, Effect>
}

data class MviResult<State, Effect>(
    val newState: State,
    val effects: List<Effect> = emptyList()
)

// ===== РЕАЛИЗАЦИЯ СТОРА =====

class DefaultMviStore<Intent, State, Effect>(
    private val reducer: MviReducer<Intent, State, Effect>,
    initialState: State
) : MviStore<Intent, State, Effect> {
    
    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<State> = _state.asStateFlow()
    
    private val _effects = MutableSharedFlow<Effect>(replay = 0)
    override val effects: SharedFlow<Effect> = _effects.asSharedFlow()
    
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun dispatch(intent: Intent) {
        scope.launch {
            val currentState = _state.value
            val result = reducer.reduce(currentState, intent)
            
            // Обновляем состояние
            _state.value = result.newState
            
            // Отправляем эффекты
            result.effects.forEach { effect ->
                _effects.emit(effect)
            }
        }
    }
    
    override fun getCurrentState(): State = _state.value
}

// ===== ПРИМЕР ИСПОЛЬЗОВАНИЯ =====

// 1. Определяем Intent'ы
sealed class NewsIntent {
    object LoadNews : NewsIntent()
    object RefreshNews : NewsIntent()
    data class AddToFavorites(val newsId: String) : NewsIntent()
    data class RemoveFromFavorites(val newsId: String) : NewsIntent()
    object ClearError : NewsIntent()
}

// 2. Определяем State
data class NewsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val news: List<NewsItem> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val error: String? = null
)

// 3. Определяем Effect'ы
sealed class NewsEffect {
    object ShowLoading : NewsEffect()
    object HideLoading : NewsEffect()
    data class ShowError(val message: String) : NewsEffect()
    data class ShowSuccess(val message: String) : NewsEffect()
    object NavigateToFavorites : NewsEffect()
    data class ShowSnackbar(val message: String) : NewsEffect()
}

// 4. Модель данных
data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val publishedDate: String,
    val isFavorite: Boolean = false
)

// 5. Реализуем Reducer
class NewsReducer(
    private val newsRepository: NewsRepository
) : MviReducer<NewsIntent, NewsState, NewsEffect> {
    
    override suspend fun reduce(
        currentState: NewsState, 
        intent: NewsIntent
    ): MviResult<NewsState, NewsEffect> {
        return when (intent) {
            is NewsIntent.LoadNews -> handleLoadNews(currentState)
            is NewsIntent.RefreshNews -> handleRefreshNews(currentState)
            is NewsIntent.AddToFavorites -> handleAddToFavorites(currentState, intent.newsId)
            is NewsIntent.RemoveFromFavorites -> handleRemoveFromFavorites(currentState, intent.newsId)
            is NewsIntent.ClearError -> handleClearError(currentState)
        }
    }
    
    private suspend fun handleLoadNews(currentState: NewsState): MviResult<NewsState, NewsEffect> {
        return try {
            MviResult(
                newState = currentState.copy(isLoading = true, error = null),
                effects = listOf(NewsEffect.ShowLoading)
            )
        } catch (e: Exception) {
            MviResult(
                newState = currentState.copy(isLoading = false),
                effects = listOf(
                    NewsEffect.HideLoading,
                    NewsEffect.ShowError("Failed to load news: ${e.message}")
                )
            )
        }
    }
    
    private suspend fun handleRefreshNews(currentState: NewsState): MviResult<NewsState, NewsEffect> {
        return try {
            val news = newsRepository.getNews()
            MviResult(
                newState = currentState.copy(
                    isRefreshing = false,
                    news = news,
                    error = null
                ),
                effects = listOf(NewsEffect.ShowSuccess("News refreshed successfully"))
            )
        } catch (e: Exception) {
            MviResult(
                newState = currentState.copy(isRefreshing = false),
                effects = listOf(NewsEffect.ShowError("Failed to refresh news"))
            )
        }
    }
    
    private fun handleAddToFavorites(currentState: NewsState, newsId: String): MviResult<NewsState, NewsEffect> {
        val newFavorites = currentState.favorites + newsId
        val updatedNews = currentState.news.map { news ->
            if (news.id == newsId) news.copy(isFavorite = true) else news
        }
        
        return MviResult(
            newState = currentState.copy(
                news = updatedNews,
                favorites = newFavorites
            ),
            effects = listOf(NewsEffect.ShowSnackbar("Added to favorites"))
        )
    }
    
    private fun handleRemoveFromFavorites(currentState: NewsState, newsId: String): MviResult<NewsState, NewsEffect> {
        val newFavorites = currentState.favorites - newsId
        val updatedNews = currentState.news.map { news ->
            if (news.id == newsId) news.copy(isFavorite = false) else news
        }
        
        return MviResult(
            newState = currentState.copy(
                news = updatedNews,
                favorites = newFavorites
            ),
            effects = listOf(NewsEffect.ShowSnackbar("Removed from favorites"))
        )
    }
    
    private fun handleClearError(currentState: NewsState): MviResult<NewsState, NewsEffect> {
        return MviResult(
            newState = currentState.copy(error = null)
        )
    }
}

// 6. Мок репозитория
class NewsRepository {
    suspend fun getNews(): List<NewsItem> {
        delay(1000) // Имитация загрузки
        return listOf(
            NewsItem("1", "Breaking News 1", "Summary 1", "2024-01-15"),
            NewsItem("2", "Breaking News 2", "Summary 2", "2024-01-14"),
            NewsItem("3", "Breaking News 3", "Summary 3", "2024-01-13")
        )
    }
}

// ===== ИСПОЛЬЗОВАНИЕ В COMPOSE =====

@Composable
fun NewsScreen() {
    val store = remember {
        DefaultMviStore(
            reducer = NewsReducer(NewsRepository()),
            initialState = NewsState()
        )
    }
    
    val state by store.state.collectAsState()
    val effects = store.effects
    
    // Обработка эффектов
    LaunchedEffect(effects) {
        effects.collect { effect ->
            when (effect) {
                is NewsEffect.ShowLoading -> {
                    // Показать loading
                }
                is NewsEffect.HideLoading -> {
                    // Скрыть loading
                }
                is NewsEffect.ShowError -> {
                    // Показать ошибку
                }
                is NewsEffect.ShowSuccess -> {
                    // Показать успех
                }
                is NewsEffect.NavigateToFavorites -> {
                    // Навигация
                }
                is NewsEffect.ShowSnackbar -> {
                    // Показать snackbar
                }
            }
        }
    }
    
    // UI
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(
            error = state.error,
            onRetry = { store.dispatch(NewsIntent.LoadNews) }
        )
        else -> NewsListScreen(
            news = state.news,
            onRefresh = { store.dispatch(NewsIntent.RefreshNews) },
            onFavoriteClick = { newsId ->
                if (state.favorites.contains(newsId)) {
                    store.dispatch(NewsIntent.RemoveFromFavorites(newsId))
                } else {
                    store.dispatch(NewsIntent.AddToFavorites(newsId))
                }
            }
        )
    }
}

// ===== ДОПОЛНИТЕЛЬНЫЕ ВОЗМОЖНОСТИ =====

// Middleware для логирования
class LoggingMiddleware<Intent, State, Effect> : MviReducer<Intent, State, Effect> {
    private val originalReducer: MviReducer<Intent, State, Effect>
    
    constructor(originalReducer: MviReducer<Intent, State, Effect>) {
        this.originalReducer = originalReducer
    }
    
    override suspend fun reduce(currentState: State, intent: Intent): MviResult<State, Effect> {
        println("MVI: Intent $intent dispatched")
        val result = originalReducer.reduce(currentState, intent)
        println("MVI: State updated to ${result.newState}")
        println("MVI: Effects emitted: ${result.effects}")
        return result
    }
}

// Middleware для обработки ошибок
class ErrorHandlingMiddleware<Intent, State, Effect> : MviReducer<Intent, State, Effect> {
    private val originalReducer: MviReducer<Intent, State, Effect>
    
    constructor(originalReducer: MviReducer<Intent, State, Effect>) {
        this.originalReducer = originalReducer
    }
    
    override suspend fun reduce(currentState: State, intent: Intent): MviResult<State, Effect> {
        return try {
            originalReducer.reduce(currentState, intent)
        } catch (e: Exception) {
            // Обработка ошибок
            MviResult(
                newState = currentState,
                effects = listOf() // Здесь можно добавить эффект ошибки
            )
        }
    }
}