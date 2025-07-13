package ru.work_mate.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "characters")
data class CharacterData(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: CharacterStatusData,
    val species: CharacterSpeciesData,
    val type: String,
    val gender: CharacterGenderData,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@JsonClass(generateAdapter = true)
data class Origin(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val info: Info,
    val results: List<CharacterData>
)

@JsonClass(generateAdapter = true)
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

@JsonClass(generateAdapter = false)
enum class CharacterSpeciesData(val str: String = "") {
    Human,
    Humanoid,
    Alien,
    Animal,
    Cronenberg,
    Disease,
    @Json(name = "Mythological Creature") MythologicalCreature("Mythological Creature"),
    Poopybutthole,
    Robot,
    @Json(name = "unknown") Unknown
}

@JsonClass(generateAdapter = false)
enum class CharacterStatusData {
    Alive,
    Dead,
    @Json(name = "unknown") Unknown
}

@JsonClass(generateAdapter = false)
enum class CharacterGenderData {
    Male,
    Female,
    Genderless,
    @Json(name = "unknown") Unknown
}