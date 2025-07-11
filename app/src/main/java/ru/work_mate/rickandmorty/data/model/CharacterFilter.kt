package ru.work_mate.rickandmorty.data.model

data class CharacterFilter(
    val name: String? = null,
    val status: CharacterStatus? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: CharacterGender? = null
) {
    fun toQueryMap(): Map<String, String> {
        val queryMap = mutableMapOf<String, String>()
        name?.let { queryMap["name"] = it }
        status?.let { queryMap["status"] = it.name }
        species?.let { queryMap["species"] = it }
        type?.let { queryMap["type"] = it }
        gender?.let { queryMap["gender"] = it.name }
        return queryMap
    }

    fun hasFilters(): Boolean {
        return name != null || status != null || species != null || type != null || gender != null
    }
}
