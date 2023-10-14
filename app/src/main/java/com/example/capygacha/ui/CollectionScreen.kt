package com.example.capygacha.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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

@Composable
fun CollectionScreen(
    imgList: List<Image>,
    onCardClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = imgList,
                key = { img -> img.id }
            ) { img ->
                val resourceId = getDrawableResourceId(LocalContext.current,img.resFile)
                Card(
                    onClick = { onCardClick(img) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.height(110.dp),
                    shape = MaterialTheme.shapes.medium,
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
                }
            }
        }
    }
}