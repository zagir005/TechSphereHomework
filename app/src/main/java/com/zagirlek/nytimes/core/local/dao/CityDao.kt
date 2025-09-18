package com.zagirlek.nytimes.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zagirlek.nytimes.core.local.entity.CityEntity


@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityEntity: CityEntity): Long

    @Query("SELECT * FROM City WHERE name LIKE :query || '%' ORDER BY name ASC")
    suspend fun getCitiesByName(query: String): List<CityEntity>

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun getCityById(id: Long): CityEntity?


}