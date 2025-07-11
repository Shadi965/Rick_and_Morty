package ru.work_mate.rickandmorty.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ru.work_mate.rickandmorty.data.model.CharacterGender
import ru.work_mate.rickandmorty.data.model.CharacterStatus
import ru.work_mate.rickandmorty.data.model.Location
import ru.work_mate.rickandmorty.data.model.Origin

class Converters(private val moshi: Moshi) {

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
    fun fromCharacterStatus(status: CharacterStatus): String {
        return status.name
    }

    @TypeConverter
    fun toCharacterStatus(value: String): CharacterStatus {
        return try {
            CharacterStatus.valueOf(value)
        } catch (e: IllegalArgumentException) {
            CharacterStatus.Unknown
        }
    }

    @TypeConverter
    fun fromCharacterGender(gender: CharacterGender): String {
        return gender.name
    }

    @TypeConverter
    fun toCharacterGender(value: String): CharacterGender {
        return try {
            CharacterGender.valueOf(value)
        } catch (e: IllegalArgumentException) {
            CharacterGender.Unknown
        }
    }
}
