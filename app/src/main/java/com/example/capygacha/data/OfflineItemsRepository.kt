package com.example.capygacha.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val gachaDao: GachaDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Image>> = gachaDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Image?> = gachaDao.getItem(id)

    override suspend fun insertItem(item: Image) = gachaDao.insert(item)

    override suspend fun deleteItem(item: Image) = gachaDao.delete(item)

    override suspend fun updateItem(item: Image) = gachaDao.update(item)
}