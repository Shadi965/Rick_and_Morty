package ru.work_mate.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.work_mate.rickandmorty.data.database.AppDatabase
import ru.work_mate.rickandmorty.data.database.CharacterDao
import ru.work_mate.rickandmorty.data.model.CharacterFilter
import ru.work_mate.rickandmorty.data.model.toCharacterGenderData
import ru.work_mate.rickandmorty.data.model.toCharacterInfo
import ru.work_mate.rickandmorty.data.model.toCharacterSpeciesData
import ru.work_mate.rickandmorty.data.model.toCharacterStatusData
import ru.work_mate.rickandmorty.data.network.ApiService
import ru.work_mate.rickandmorty.domain.model.CharacterGender
import ru.work_mate.rickandmorty.domain.model.CharacterInfo
import ru.work_mate.rickandmorty.domain.model.CharacterSpecies
import ru.work_mate.rickandmorty.domain.model.CharacterStatus
import ru.work_mate.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
    private val database: AppDatabase
) : CharacterRepository {

    override fun getCharacters(
        name: String?,
        type: String?,
        status: CharacterStatus?,
        species: CharacterSpecies?,
        gender: CharacterGender?,
    ): Flow<PagingData<CharacterInfo>> {
        val filter = CharacterFilter(
            name = name,
            status = status?.toCharacterStatusData(),
            species = species?.toCharacterSpeciesData(),
            type = type,
            gender = gender?.toCharacterGenderData()
        )

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            remoteMediator = CharacterRemoteMediator(
                apiService = apiService,
                database = database,
                characterDao = characterDao,
                pageStore = PageStore,
                filter = filter
            ),
            pagingSourceFactory = {
                if (filter.hasFilters()) {
                    characterDao.getFilteredCharactersPaged(
                        name = filter.name,
                        status = filter.status,
                        species = filter.species,
                        type = filter.type,
                        gender = filter.gender
                    )
                } else {
                    characterDao.getCharactersPaged()
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toCharacterInfo() }
        }
    }

    override suspend fun getCharacter(id: Int) = characterDao.getCharacter(id).toCharacterInfo()

    object PageStore {
        var nextPage: Int? = null
    }
}