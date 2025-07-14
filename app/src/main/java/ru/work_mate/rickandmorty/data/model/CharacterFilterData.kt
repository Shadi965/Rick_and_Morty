package ru.work_mate.rickandmorty.data.model

data class CharacterFilterData(
    val name: String? = null,
    val status: CharacterStatusData? = null,
    val species: CharacterSpeciesData? = null,
    val type: String? = null,
    val gender: CharacterGenderData? = null
) {
    fun toQueryMap(): Map<String, String> {
        val queryMap = mutableMapOf<String, String>()
        name?.let { queryMap["name"] = it }
        status?.let { queryMap["status"] = it.name }
        species?.let { queryMap["species"] = it.str }
        type?.let { queryMap["type"] = it }
        gender?.let { queryMap["gender"] = it.name }
        return queryMap
    }

    fun hasFilters(): Boolean {
        return !name.isNullOrBlank() || status != null || species != null || !type.isNullOrBlank() || gender != null
    }
}