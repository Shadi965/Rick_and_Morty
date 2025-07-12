package ru.work_mate.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.work_mate.rickandmorty.data.database.AppDatabase
import ru.work_mate.rickandmorty.data.database.CharacterDao
import ru.work_mate.rickandmorty.data.model.CharacterFilter
import ru.work_mate.rickandmorty.data.model.RMCharacter
import ru.work_mate.rickandmorty.data.network.ApiService
import ru.work_mate.rickandmorty.data.paging.CharacterRemoteMediator
import ru.work_mate.rickandmorty.domain.repository.CharacterRepository
import ru.work_mate.rickandmorty.utils.NetworkUtils
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
    private val database: AppDatabase,
    private val networkUtils: NetworkUtils
) : CharacterRepository {

    override fun getCharacters(filter: CharacterFilter): Flow<PagingData<RMCharacter>> {
        return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 20
                ),
                remoteMediator = CharacterRemoteMediator(
                    apiService = apiService,
                    database = database,
                    characterDao = characterDao,
                    networkUtils = networkUtils,
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
            ).flow
    }
}
