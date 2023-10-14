package com.example.capygacha.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.capygacha.GachaApplication
import com.example.capygacha.R
import com.example.capygacha.data.GachaDao
import com.example.capygacha.data.Image
import com.example.capygacha.data.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class GachaViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    val gachaDao: GachaDao,
): ViewModel() {

    val uiState: StateFlow<GachaUiState> =
        userPreferencesRepository.homeImage.map { homeImage ->
            GachaUiState(homeImage)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = GachaUiState()
            )


//  fun insertImage(image: Image) {
//      viewModelScope.launch(Dispatchers.Default) {
//          gachaDao.insert(image)
//      }
//  }

    fun getAllImage(): Flow<List<Image>> = gachaDao.getAllItems()

    suspend fun getImage(): Image {
        val rarity = when(Random.nextInt(1, 100)) {
            in 1..34 -> "Common"
            in 35..59 -> "Uncommon"
            in 60..79 -> "Rare"
            in 80..94 -> "Mythical"
            in 95..100 -> "Legendary"
            else -> "Common"
        }

        val numOfImg = gachaDao.getNumberOfImages(rarity)

        val randomInt = Random.nextInt(0, numOfImg)
        val img = gachaDao.getItem(randomInt, rarity)
        gachaDao.update(img.copy(summoned = true))

        return img
    }

    fun selectHomeImage(homeImage: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveHomeImagePreference(homeImage)
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GachaApplication)
                GachaViewModel(application.userPreferencesRepository, application.database.gachaDao())
            }
        }
    }
}

data class GachaUiState(
    val homeImage: String = "loading_img"
)