package ru.work_mate.rickandmorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.work_mate.rickandmorty.domain.model.CharacterGender
import ru.work_mate.rickandmorty.domain.model.CharacterInfo
import ru.work_mate.rickandmorty.domain.model.CharacterSpecies
import ru.work_mate.rickandmorty.domain.model.CharacterStatus

interface CharacterRepository {

    fun getCharacters(
        name: String? = null,
        type: String? = null,
        status: CharacterStatus? = null,
        species: CharacterSpecies? = null,
        gender: CharacterGender? = null
    ): Flow<PagingData<CharacterInfo>>

    suspend fun getCharacter(id: Int): CharacterInfo
}