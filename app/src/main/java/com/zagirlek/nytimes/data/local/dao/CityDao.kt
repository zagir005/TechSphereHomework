package com.zagirlek.nytimes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagirlek.nytimes.data.local.entity.CityEntity


@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(cityEntity: CityEntity): Long

    @Query("SELECT * FROM city WHERE name = :name COLLATE NOCASE LIMIT 1")
    suspend fun findCityByName(name: String): CityEntity?

    @Query("SELECT * FROM City WHERE name LIKE :query || '%' COLLATE NOCASE ORDER BY name ASC")
    suspend fun getCitiesByName(query: String): List<CityEntity>

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun getCityById(id: Long): CityEntity?
}