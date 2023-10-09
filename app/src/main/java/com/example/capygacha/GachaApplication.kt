package com.example.capygacha

import android.app.Application
import com.example.capygacha.data.AppDatabase

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