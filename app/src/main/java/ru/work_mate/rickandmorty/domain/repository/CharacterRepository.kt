package ru.work_mate.rickandmorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.work_mate.rickandmorty.domain.model.CharacterFilter
import ru.work_mate.rickandmorty.domain.model.CharacterInfo

interface CharacterRepository {

    fun getCharacters(
        characterFilter: CharacterFilter? = null
    ): Flow<PagingData<CharacterInfo>>

    suspend fun getCharacter(id: Int): CharacterInfo
}