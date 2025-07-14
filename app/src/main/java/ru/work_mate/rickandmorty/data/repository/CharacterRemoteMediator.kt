package ru.work_mate.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.work_mate.rickandmorty.data.database.AppDatabase
import ru.work_mate.rickandmorty.data.database.CharacterDao
import ru.work_mate.rickandmorty.data.model.CharacterData
import ru.work_mate.rickandmorty.data.model.CharacterFilterData
import ru.work_mate.rickandmorty.data.network.ApiService
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val characterDao: CharacterDao,
    private val pageStore: CharacterRepositoryImpl.PageStore,
    private val filter: CharacterFilterData = CharacterFilterData()
) : RemoteMediator<Int, CharacterData>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                pageStore.nextPage = 1
                1
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                pageStore.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        // TODO:
        //if (!networkUtils.isNetworkAvailable()) {
        //    return MediatorResult.Success(endOfPaginationReached = false)
        //}

        try {
            val response = apiService.getCharacters(
                page = page,
                filters = filter.toQueryMap()
            )

            pageStore.nextPage = if (response.info.next == null) null else page + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.clearAll()
                }
                characterDao.upsertCharacters(response.results)
            }

            return MediatorResult.Success(
                endOfPaginationReached = response.info.next == null
            )
        } catch (ex: IOException) {
            return MediatorResult.Error(ex)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }
    }
}