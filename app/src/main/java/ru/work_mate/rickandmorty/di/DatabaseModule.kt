package ru.work_mate.rickandmorty.di

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

    // TODO: Проверить другой способ @Inject в Converters
    @Provides
    fun provideConverters(moshi: Moshi): Converters {
        return Converters(moshi)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, converters: Converters): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "rick_morty.db"
        )
            .addTypeConverter(converters)
            .build()
    }

    @Provides
    fun provideCharacterDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }
}