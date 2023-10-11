package com.example.capygacha.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.capygacha.GachaApplication
import com.example.capygacha.data.GachaDao
import com.example.capygacha.data.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GachaViewModel(
    //private val itemsRepository: ItemsRepository,
    val gachaDao: GachaDao,
): ViewModel() {


    fun insertImage(image: Image) {
        viewModelScope.launch(Dispatchers.Default) {
            gachaDao.insert(image)
        }
    }

    fun getAllImage(): Flow<List<Image>> = gachaDao.getAllItems()

    suspend fun getImage(): Image {
        val randomInt = Random.nextInt(1, 47)
        val img = gachaDao.getItem(randomInt)
        gachaDao.update(img.copy(summoned = true))
        return img
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GachaApplication)
                GachaViewModel(application.database.gachaDao())
                //GachaViewModel(GachaApplication().container.itemsRepository)
            }
        }
    }

}