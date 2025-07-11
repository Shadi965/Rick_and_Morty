package ru.work_mate.rickandmorty.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.work_mate.rickandmorty.data.database.AppDatabase
import ru.work_mate.rickandmorty.data.database.CharacterDao
import ru.work_mate.rickandmorty.data.database.Converters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, moshi: Moshi): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "rick_morty.db"
        )
            .addTypeConverter(Converters(moshi))
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideCharacterDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }
}