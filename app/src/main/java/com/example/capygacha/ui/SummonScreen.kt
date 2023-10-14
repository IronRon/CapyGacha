package com.example.capygacha.ui

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .fillMaxWidth(),
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
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (img.summoned == false) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.new_summon),
                            contentDescription = null
                        )
                        Spacer(modifier = modifier.weight(1f))
                    }
                }
                Spacer(modifier = modifier.weight(1f))
                if (img.id != -1) {
                    TextColor(
                        name = img.name,
                        rarity = img.rarity,
                        modifier = modifier.padding(bottom = 12.dp)
                    )
                }
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

@Composable
fun TextColor(
    name: String,
    rarity: String,
    modifier: Modifier = Modifier
) {
    val color = when (rarity) {
        "Common" -> Color(0xFFFFFFFF)
        "Uncommon" -> Color(0xFF0000FF)
        "Rare" -> Color(0xFFFF0000)
        "Mythical" -> Color(0xFF800080)
        "Legendary" -> Color(0xFFFFD700)

        else -> Color(0xFFFFFFFF)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.summonbanner),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = null
        )
        Text(
            text = name,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = modifier.padding(12.dp)
        )
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