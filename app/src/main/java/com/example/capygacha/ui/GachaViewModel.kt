package com.example.capygacha.ui

import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.capygacha.GachaApplication
import com.example.capygacha.data.GachaDao
import com.example.capygacha.data.Image
import com.example.capygacha.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GachaViewModel(
    //private val itemsRepository: ItemsRepository,
    val gachaDao: GachaDao,
): ViewModel() {


    fun insertImage(image: Image) {
        viewModelScope.launch {
            gachaDao.insert(image)
        }
    }

    fun getAllImage() {
        viewModelScope.launch {
            gachaDao.getAllItems()
        }
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