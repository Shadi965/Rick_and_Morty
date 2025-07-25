package ru.work_mate.rickandmorty.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ru.work_mate.rickandmorty.data.model.CharacterGenderData
import ru.work_mate.rickandmorty.data.model.CharacterSpeciesData
import ru.work_mate.rickandmorty.data.model.CharacterStatusData
import ru.work_mate.rickandmorty.data.model.Location
import ru.work_mate.rickandmorty.data.model.Origin
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(private val moshi: Moshi) {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(listType)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(listType)
        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromOrigin(origin: Origin): String {
        val adapter = moshi.adapter(Origin::class.java)
        return adapter.toJson(origin)
    }

    @TypeConverter
    fun toOrigin(originString: String): Origin {
        val adapter = moshi.adapter(Origin::class.java)
        return adapter.fromJson(originString) ?: Origin("", "")
    }

    @TypeConverter
    fun fromLocation(location: Location): String {
        val adapter = moshi.adapter(Location::class.java)
        return adapter.toJson(location)
    }

    @TypeConverter
    fun toLocation(locationString: String): Location {
        val adapter = moshi.adapter(Location::class.java)
        return adapter.fromJson(locationString) ?: Location("", "")
    }

    @TypeConverter
    fun fromCharacterStatus(status: CharacterStatusData): String {
        return status.name
    }

    @TypeConverter
    fun toCharacterStatus(value: String): CharacterStatusData {
        return try {
            CharacterStatusData.valueOf(value)
        } catch (_: IllegalArgumentException) {
            CharacterStatusData.Unknown
        }
    }

    @TypeConverter
    fun fromCharacterGender(gender: CharacterGenderData): String {
        return gender.name
    }

    @TypeConverter
    fun toCharacterGender(value: String): CharacterGenderData {
        return try {
            CharacterGenderData.valueOf(value)
        } catch (_: IllegalArgumentException) {
            CharacterGenderData.Unknown
        }
    }

    @TypeConverter
    fun fromCharacterSpecies(species: CharacterSpeciesData): String {
        return species.name
    }

    @TypeConverter
    fun toCharacterSpecies(value: String): CharacterSpeciesData {
        return try {
            CharacterSpeciesData.valueOf(value)
        } catch (_: IllegalArgumentException) {
            CharacterSpeciesData.Unknown
        }
    }
}