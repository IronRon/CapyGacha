package com.example.capygacha.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Image::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gachaDao(): GachaDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "image_database")
                    .createFromAsset("database/image_database.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}