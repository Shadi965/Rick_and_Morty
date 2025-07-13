package ru.work_mate.rickandmorty.data.model

import ru.work_mate.rickandmorty.domain.model.CharacterGender
import ru.work_mate.rickandmorty.domain.model.CharacterInfo
import ru.work_mate.rickandmorty.domain.model.CharacterSpecies
import ru.work_mate.rickandmorty.domain.model.CharacterStatus

fun CharacterData.toCharacterInfo() = CharacterInfo(
        id = id,
        name = name,
        status = status.toCharacterStatus(),
        species = species.toCharacterSpecies(),
        type = type,
        gender = gender.toCharacterGender(),
        origin = origin.name,
        location = location.name,
        image = image,
        episode = episode,
    )

fun CharacterStatusData.toCharacterStatus(): CharacterStatus = when (this) {
    CharacterStatusData.Alive -> CharacterStatus.Alive
    CharacterStatusData.Dead -> CharacterStatus.Dead
    CharacterStatusData.Unknown -> CharacterStatus.Unknown
}

fun CharacterSpeciesData.toCharacterSpecies(): CharacterSpecies = when (this) {
    CharacterSpeciesData.Human -> CharacterSpecies.Human
    CharacterSpeciesData.Humanoid -> CharacterSpecies.Humanoid
    CharacterSpeciesData.Alien -> CharacterSpecies.Alien
    CharacterSpeciesData.Animal -> CharacterSpecies.Animal
    CharacterSpeciesData.Cronenberg -> CharacterSpecies.Cronenberg
    CharacterSpeciesData.Disease -> CharacterSpecies.Disease
    CharacterSpeciesData.MythologicalCreature -> CharacterSpecies.MythologicalCreature
    CharacterSpeciesData.Poopybutthole -> CharacterSpecies.Poopybutthole
    CharacterSpeciesData.Robot -> CharacterSpecies.Robot
    CharacterSpeciesData.Unknown -> CharacterSpecies.Unknown
}

fun CharacterGenderData.toCharacterGender(): CharacterGender = when (this) {
    CharacterGenderData.Male -> CharacterGender.Male
    CharacterGenderData.Female -> CharacterGender.Female
    CharacterGenderData.Genderless -> CharacterGender.Genderless
    CharacterGenderData.Unknown -> CharacterGender.Unknown
}

// Обратное преобразование

fun CharacterStatus.toCharacterStatusData(): CharacterStatusData = when (this) {
    CharacterStatus.Alive -> CharacterStatusData.Alive
    CharacterStatus.Dead -> CharacterStatusData.Dead
    CharacterStatus.Unknown -> CharacterStatusData.Unknown
}

fun CharacterSpecies.toCharacterSpeciesData(): CharacterSpeciesData = when (this) {
    CharacterSpecies.Human -> CharacterSpeciesData.Human
    CharacterSpecies.Humanoid -> CharacterSpeciesData.Humanoid
    CharacterSpecies.Alien -> CharacterSpeciesData.Alien
    CharacterSpecies.Animal -> CharacterSpeciesData.Animal
    CharacterSpecies.Cronenberg -> CharacterSpeciesData.Cronenberg
    CharacterSpecies.Disease -> CharacterSpeciesData.Disease
    CharacterSpecies.MythologicalCreature -> CharacterSpeciesData.MythologicalCreature
    CharacterSpecies.Poopybutthole -> CharacterSpeciesData.Poopybutthole
    CharacterSpecies.Robot -> CharacterSpeciesData.Robot
    CharacterSpecies.Unknown -> CharacterSpeciesData.Unknown
}

fun CharacterGender.toCharacterGenderData(): CharacterGenderData = when (this) {
    CharacterGender.Male -> CharacterGenderData.Male
    CharacterGender.Female -> CharacterGenderData.Female
    CharacterGender.Genderless -> CharacterGenderData.Genderless
    CharacterGender.Unknown -> CharacterGenderData.Unknown
}