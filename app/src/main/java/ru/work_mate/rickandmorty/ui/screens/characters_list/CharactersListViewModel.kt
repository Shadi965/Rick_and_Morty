package ru.work_mate.rickandmorty.ui.screens.characters_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import ru.work_mate.rickandmorty.domain.model.CharacterFilter
import ru.work_mate.rickandmorty.domain.model.CharacterInfo
import ru.work_mate.rickandmorty.domain.repository.CharacterRepository
import ru.work_mate.rickandmorty.utils.NetworkUtils
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepository,
    networkUtils: NetworkUtils
) : ViewModel() {

    private val _filter = MutableStateFlow(CharacterFilter())
    val filter = _filter.asStateFlow()

    private val _isFilterExpanded = MutableStateFlow(false)
    val isFilterExpanded = _isFilterExpanded.asStateFlow()

    val characters: Flow<PagingData<CharacterInfo>> =
        _filter.debounce(300)
            .flatMapLatest { filter ->
            repository.getCharacters(filter)
        }.cachedIn(viewModelScope)

    val networkAvailable = networkUtils.networkAvailable

    fun updateFilter(filter: CharacterFilter) {
        _filter.update { filter }
    }

    fun toggleFilterExpanded() {
        _isFilterExpanded.update { !it }
    }

    fun clearFilter() {
        _filter.update { CharacterFilter() }
    }
}