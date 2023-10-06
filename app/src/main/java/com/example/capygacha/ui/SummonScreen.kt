package com.example.capygacha.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    summon: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
//            AsyncImage(
//                model = ImageRequest.Builder(context = LocalContext.current)
//                    .data(info.imgSrc)
//                    .crossfade(true)
//                    .build(),
//                error = painterResource(R.drawable.ic_broken_image),
//                placeholder = painterResource(R.drawable.loading_img),
//                contentDescription = stringResource(R.string.pull_photo),
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxWidth()
//            )
        }
        Button(
            onClick = {},
            modifier = modifier.widthIn(min = 150.dp)
        ) {
            Text(stringResource(R.string.summon_button))
        }
    }
}