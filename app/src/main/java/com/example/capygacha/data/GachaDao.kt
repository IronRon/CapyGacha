package com.example.capygacha.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GachaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: Image)

    @Update
    suspend fun update(image: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("SELECT * from image WHERE id = :id")
    fun getItem(id: Int): Flow<Image>

    @Query("SELECT * from image ORDER BY name ASC")
    fun getAllItems(): Flow<List<Image>>
}