package ru.work_mate.rickandmorty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object CharacterListRoute

@Serializable
data class CharacterDetailRoute(
    val characterId: Int
)

//@Serializable
//@ConsistentCopyVisibility
//data class FilterRoute private constructor(
//    private val name: String?,
//    private val status: String?,
//    private val species: String?,
//    private val type: String?,
//    private val gender: String?
//) {
//    constructor(currentFilter: CharacterFilter = CharacterFilter()) : this(
//        currentFilter.name,
//        currentFilter.status?.name,
//        currentFilter.species?.name,
//        currentFilter.type,
//        currentFilter.gender?.name
//    )
//    val currentFilter: CharacterFilter
//        get() = CharacterFilter(
//            name = name,
//            status = status?.let { CharacterStatus.valueOf(status) },
//            species = species?.let { CharacterSpecies.valueOf(species) },
//            type = type,
//            gender = gender?.let { CharacterGender.valueOf(gender) }
//        )
//}
