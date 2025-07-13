package ru.work_mate.rickandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.work_mate.rickandmorty.data.model.CharacterData
import ru.work_mate.rickandmorty.data.model.CharacterGenderData
import ru.work_mate.rickandmorty.data.model.CharacterSpeciesData
import ru.work_mate.rickandmorty.data.model.CharacterStatusData

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getCharactersPaged(): PagingSource<Int, CharacterData>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacter(id: Int): CharacterData

    @Upsert
    suspend fun upsertCharacters(characters: List<CharacterData>)

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Query(
        """
        SELECT * FROM characters 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:status IS NULL OR status = :status)
        AND (:species IS NULL OR species = :species)
        AND (:type IS NULL OR type LIKE '%' || :type || '%')
        AND (:gender IS NULL OR gender = :gender)
        ORDER BY id ASC
    """
    )
    fun getFilteredCharactersPaged(
        name: String?,
        status: CharacterStatusData?,
        species: CharacterSpeciesData?,
        type: String?,
        gender: CharacterGenderData?
    ): PagingSource<Int, CharacterData>
}