package ru.work_mate.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.work_mate.rickandmorty.data.database.AppDatabase
import ru.work_mate.rickandmorty.data.database.CharacterDao
import ru.work_mate.rickandmorty.data.model.CharacterFilter
import ru.work_mate.rickandmorty.data.model.RMCharacter
import ru.work_mate.rickandmorty.data.network.ApiService
import ru.work_mate.rickandmorty.utils.NetworkUtils
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val characterDao: CharacterDao,
    private val networkUtils: NetworkUtils,
    private val filter: CharacterFilter = CharacterFilter()
) : RemoteMediator<Int, RMCharacter>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RMCharacter>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {

                val currentPage = (characterDao.getCharacterCount() / state.config.pageSize)
                currentPage + 1
            }
        }

        if (!networkUtils.isNetworkAvailable()) {
            return MediatorResult.Success(endOfPaginationReached = false)
        }

        try {
            val response = apiService.getCharacters(
                page = page,
                filters = filter.toQueryMap()
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.clearAll()
                }
                characterDao.insertCharacters(response.results)
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
