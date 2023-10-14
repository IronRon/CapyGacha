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

    @Query("SELECT * from image WHERE rarity = :rarity LIMIT 1 OFFSET :row")
    suspend fun getItem(row: Int, rarity: String): Image

    @Query("SELECT COUNT(*) from image WHERE rarity = :rarity GROUP BY rarity")
    suspend fun getNumberOfImages(rarity: String): Int

    @Query("SELECT * from image WHERE summoned = 1 ORDER BY id ASC")
    fun getAllItems(): Flow<List<Image>>
}