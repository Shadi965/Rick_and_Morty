package ru.work_mate.rickandmorty.ui.screens.characters_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import ru.work_mate.rickandmorty.domain.model.CharacterInfo
import ru.work_mate.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModel @Inject constructor(
    repository: CharacterRepository,
) : ViewModel() {

    val characters: Flow<PagingData<CharacterInfo>> =
        repository.getCharacters().cachedIn(viewModelScope)

}