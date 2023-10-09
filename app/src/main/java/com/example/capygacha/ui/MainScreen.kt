package com.example.capygacha.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.capygacha.R
import com.example.capygacha.data.Image
import com.example.capygacha.data.images

@Composable
fun MainScreen(
    onCollectionClick:() -> Unit,
    onSummonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f, true))
        Row {
            Button(
                onClick = onSummonClick,
                modifier = modifier.widthIn(min = 150.dp)
            ) {
                Text(stringResource(R.string.summon_button))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick =  onCollectionClick ,
                modifier = modifier.widthIn(min = 150.dp)
            ) {
                Text(stringResource(R.string.collection_button))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
