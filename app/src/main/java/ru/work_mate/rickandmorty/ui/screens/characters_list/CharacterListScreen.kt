package ru.work_mate.rickandmorty.ui.screens.characters_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import ru.work_mate.rickandmorty.ui.components.LoadingIndicator
import ru.work_mate.rickandmorty.ui.components.NetworkStatusIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    val currentFilter by viewModel.filter.collectAsState()
    val isFilterExpanded by viewModel.isFilterExpanded.collectAsState()
    val isNetworkAvailable by viewModel.networkAvailable.collectAsState()

    val isApiError = characters.loadState.refresh is LoadState.Error
    val isLoading = characters.loadState.refresh is LoadState.Loading
            && characters.itemCount != 0

    val isEmpty = characters.itemCount == 0 &&
            characters.loadState.refresh is LoadState.NotLoading &&
            characters.loadState.append.endOfPaginationReached

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            NetworkStatusIndicator(
                hasNetwork = isNetworkAvailable,
                hasApiError = isApiError
            )
        }

        CharacterFilterSection(
            filter = currentFilter,
            isExpanded = isFilterExpanded,
            onFilterChange = viewModel::updateFilter,
            onClearFilter = viewModel::clearFilter,
            onToggleExpanded = viewModel::toggleFilterExpanded
        )

        when {
            isLoading -> {
                LoadingIndicator(
                    modifier = Modifier.fillMaxSize(),
                    message = "Loading characters..."
                )
            }
            isEmpty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("There is nothing to show here")
                }
            }
            else -> {
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = { characters.refresh() },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            characters.itemCount,
                            key = characters.itemKey { it.id }
                        ) { index ->
                            characters[index]?.let {
                                CharacterCard(
                                    character = it,
                                    onClick = { onCharacterClick(it.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
