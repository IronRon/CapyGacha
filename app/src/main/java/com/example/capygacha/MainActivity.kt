package com.example.capygacha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.capygacha.ui.AudioPlay
import com.example.capygacha.ui.AudioPlay.pauseAudio
import com.example.capygacha.ui.theme.CapyGachaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapyGachaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CapyGachaApp()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        AudioPlay.continuePlaying(this)
    }

    override fun onStop() {
        super.onStop()
        AudioPlay.pauseAudio()
    }
}

