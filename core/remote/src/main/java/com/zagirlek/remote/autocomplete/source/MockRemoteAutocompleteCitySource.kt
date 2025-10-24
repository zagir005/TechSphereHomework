package com.zagirlek.remote.autocomplete.source

import com.zagirlek.remote.autocomplete.dto.CityDTO

class MockRemoteAutocompleteCitySource: RemoteAutocompleteCitySource {
    override suspend fun autocompleteSearch(filter: String): List<CityDTO> =
        getMockCityList(filter)

    fun getMockCityList(filter: String? = null): List<CityDTO> {
        val cities = listOf(
            CityDTO(1, "Москва"),
            CityDTO(2, "Махачкала"),
            CityDTO(3, "Санкт-Петербург"),
            CityDTO(4, "Новосибирск"),
            CityDTO(5, "Екатеринбург"),
            CityDTO(6, "Дербент"),
            CityDTO(7, "Нижний Новгород"),
            CityDTO(8, "Челябинск"),
            CityDTO(9, "Самара"),
            CityDTO(10, "Омск"),
            CityDTO(11, "Ростов-на-Дону"),
            CityDTO(12, "Казань")
        )


        return if (!filter.isNullOrBlank()) {
            cities.filter { it.name.contains(filter, ignoreCase = true) }
        } else {
            cities
        }
    }
}