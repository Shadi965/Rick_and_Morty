package ru.work_mate.rickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.work_mate.rickandmorty.data.model.RMCharacter

@Database(
    entities = [RMCharacter::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
