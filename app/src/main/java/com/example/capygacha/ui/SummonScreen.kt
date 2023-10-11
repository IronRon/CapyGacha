package com.example.capygacha.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.capygacha.R
import com.example.capygacha.data.Image
import com.example.capygacha.data.images

@Composable
fun SummonScreen(
    img: Image,
    summon: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val resourceId = getDrawableResourceId(LocalContext.current,img.resFile)
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(resourceId)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.pull_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Column(modifier = modifier) {
                Spacer(modifier = modifier.weight(1f))
                Button(
                    onClick = summon,
                    modifier = modifier.widthIn(min = 150.dp)
                ) {
                    Text(stringResource(R.string.summon_button))
                }
            }
        }
        //Spacer(modifier = modifier.weight(1f))
    }
}

fun getDrawableResourceId(context: Context, drawableName: String): Int {
    return try {
        val packageName = context.packageName
        val res = context.resources
        res.getIdentifier(drawableName, "drawable", packageName)
    } catch (e: Exception) {
        // Handle exceptions here, such as the resource not found
        // You can log the error or return a default value
        e.printStackTrace()
        0 // Return a default value (0) or a sentinel value as needed
    }
}