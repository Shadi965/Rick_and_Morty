package ru.work_mate.rickandmorty.domain.model

data class CharacterFilter(
    val name: String? = null,
    val status: CharacterStatus? = null,
    val species: CharacterSpecies? = null,
    val type: String? = null,
    val gender: CharacterGender? = null
) {
    fun isEmpty(): Boolean {
        return name.isNullOrBlank() &&
                status == null &&
                species == null &&
                type.isNullOrBlank() &&
                gender == null
    }

    fun hasAdvancedFilters(): Boolean {
        return status != null ||
                species != null ||
                !type.isNullOrBlank() ||
                gender != null
    }
}
