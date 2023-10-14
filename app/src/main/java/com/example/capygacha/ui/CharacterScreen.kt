package com.example.capygacha.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.capygacha.data.Image

@Composable
fun CharacterScreen(
    img: Image,
    modifier: Modifier = Modifier
) {
    val resourceId = getDrawableResourceId(LocalContext.current,img.resFile)
    Image(
        painter = painterResource(resourceId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}