package ru.work_mate.rickandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.work_mate.rickandmorty.data.model.CharacterGender
import ru.work_mate.rickandmorty.data.model.CharacterSpecies
import ru.work_mate.rickandmorty.data.model.CharacterStatus
import ru.work_mate.rickandmorty.data.model.RMCharacter

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getCharactersPaged(): PagingSource<Int, RMCharacter>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacters(characters: List<RMCharacter>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(character: RMCharacter)

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Query(
        """
        SELECT * FROM characters 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:status IS NULL OR status = :status)
        AND (:species IS NULL OR species = :species)
        AND (:type IS NULL OR type = :type)
        AND (:gender IS NULL OR gender = :gender)
        ORDER BY id ASC
    """
    )
    fun getFilteredCharactersPaged(
        name: String?,
        status: CharacterStatus?,
        species: CharacterSpecies?,
        type: String?,
        gender: CharacterGender?
    ): PagingSource<Int, RMCharacter>

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharacterCount(): Int
}
