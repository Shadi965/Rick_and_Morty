package ru.work_mate.rickandmorty.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.work_mate.rickandmorty.data.model.ApiResponse

const val BASE_URL = "https://rickandmortyapi.com/api/"

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @QueryMap filters: Map<String, String> = emptyMap()
    ): ApiResponse
}