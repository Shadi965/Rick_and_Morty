package ru.work_mate.rickandmorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.work_mate.rickandmorty.data.model.CharacterFilter
import ru.work_mate.rickandmorty.data.model.RMCharacter

interface CharacterRepository {

    fun getCharacters(filter: CharacterFilter = CharacterFilter()): Flow<PagingData<RMCharacter>>
}
