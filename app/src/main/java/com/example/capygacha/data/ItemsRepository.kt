package com.example.capygacha.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface ItemsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Image>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Image?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Image)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Image)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Image)
}