package ru.work_mate.rickandmorty.domain.model

data class CharacterInfo(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: CharacterSpecies,
    val type: String,
    val gender: CharacterGender,
    val origin: String,
    val location: String,
    val image: String,
    val episode: List<String>
)