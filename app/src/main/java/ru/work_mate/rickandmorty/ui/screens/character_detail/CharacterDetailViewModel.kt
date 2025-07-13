package ru.work_mate.rickandmorty.ui.screens.character_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.work_mate.rickandmorty.domain.model.CharacterInfo
import ru.work_mate.rickandmorty.domain.repository.CharacterRepository

@HiltViewModel(assistedFactory = CharacterDetailViewModel.Factory::class)
class CharacterDetailViewModel @AssistedInject constructor(
    private val repository: CharacterRepository,
    @Assisted
    private val characterId: Int
) : ViewModel() {

    private val _character = MutableStateFlow<CharacterInfo?>(null)
    val character = _character.asStateFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            _character.update { null }
            _character.update { repository.getCharacter(characterId) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): CharacterDetailViewModel
    }
}
