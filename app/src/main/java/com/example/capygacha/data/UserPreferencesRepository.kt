package com.example.capygacha.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository (
    private val dataStore: DataStore<Preferences>
){
    val homeImage: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[HOME_IMAGE] ?: "loading_img"
        }

    private companion object {
        val HOME_IMAGE = stringPreferencesKey("home_image")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveHomeImagePreference(homeImage: String) {
        dataStore.edit { preferences ->
            preferences[HOME_IMAGE] = homeImage
        }
    }

}