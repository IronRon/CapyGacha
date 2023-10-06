package com.example.capygacha

import android.app.Application
import com.example.capygacha.data.AppContainer
import com.example.capygacha.data.AppDataContainer
import com.example.capygacha.data.AppDatabase
import com.example.capygacha.data.ItemsRepository
import com.example.capygacha.data.OfflineItemsRepository

class GachaApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    //lateinit var container: AppContainer

    override fun onCreate() {
        //container = AppDataContainer(this)
        super.onCreate()
    }
}