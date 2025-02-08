package com.example.zafrair.ui.components

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zafrair.ui.theme.LocalAppTheme

data class CardItem(
    val id: String,
    val title: String,
    val description: String,
)

sealed class VerticalCardsUiState {
    object Loading : VerticalCardsUiState()
    data class Success(val cards: List<CardItem>) : VerticalCardsUiState()
    data class Error(val message: String) : VerticalCardsUiState()
}

class VerticalCardsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<VerticalCardsUiState>(VerticalCardsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10

    fun loadMoreCards(allCards: List<CardItem>) {
        viewModelScope.launch {
            val startIndex = currentPage * pageSize
            val endIndex = minOf(startIndex + pageSize, allCards.size)

            if (startIndex < allCards.size) {
                val newCards = allCards.subList(startIndex, endIndex)
                val currentCards = (_uiState.value as? VerticalCardsUiState.Success)?.cards ?: emptyList()
                _uiState.value = VerticalCardsUiState.Success(currentCards + newCards)
                currentPage++
            }
        }
    }
}

@Composable
fun VerticalCardScrollView(
    cards: List<CardItem>,
    onCardClick: (CardItem) -> Unit,
    viewModel: VerticalCardsViewModel = viewModel(),
    theme: Resources.Theme
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val cardHeight = screenHeight * 0.3

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                if (lastIndex != null && lastIndex >= (listState.layoutInfo.totalItemsCount - 3)) {
                    viewModel.loadMoreCards(cards)
                }
            }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (uiState) {
            is VerticalCardsUiState.Success -> {
                items(
                    items = (uiState as VerticalCardsUiState.Success).cards,
                    key = { it.id }
                ) { card ->
                    VerticalCard(
                        card = card,
                        onClick = { onCardClick(card) },
                        theme = theme,
                        modifier = Modifier.height(cardHeight.dp)
                    )
                }
            }
            is VerticalCardsUiState.Loading -> {
                item { LoadingIndicator() }
            }
            is VerticalCardsUiState.Error -> {
                item {
                    ErrorMessage(message = (uiState as VerticalCardsUiState.Error).message)
                }
            }
        }
    }
}

@Composable
private fun VerticalCard(
    card: CardItem,
    onClick: () -> Unit,
    theme: Resources.Theme,
    modifier: Modifier = Modifier
) {
    val theme = LocalAppTheme.current
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            theme.colors.primary,
                            theme.colors.secondary
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = card.title,
                    style = theme.typography.headlineLarge,
                    color = theme.colors.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = card.description,
                    style = theme.typography.bodyMedium,
                    color = theme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}