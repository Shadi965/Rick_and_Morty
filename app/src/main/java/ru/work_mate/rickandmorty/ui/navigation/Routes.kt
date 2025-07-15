package ru.work_mate.rickandmorty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object CharacterListRoute

@Serializable
data class CharacterDetailRoute(
    val characterId: Int
)