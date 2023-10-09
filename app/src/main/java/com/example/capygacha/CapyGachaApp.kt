package com.example.capygacha

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.capygacha.data.Image
import com.example.capygacha.data.defaultImage
import com.example.capygacha.ui.CollectionScreen
import com.example.capygacha.ui.GachaViewModel
import com.example.capygacha.ui.MainScreen
import com.example.capygacha.ui.SummonScreen
import kotlinx.coroutines.launch

/**
 * enum values that represent the screens in the app
 */
enum class CapyGachaScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Summon(title = R.string.summon_screen),
    Collection(title = R.string.collection_screen),
}

@Composable
fun CapyGachaAppBar(
    currentScreen: CapyGachaScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CapyGachaApp(
    viewModel: GachaViewModel = viewModel(factory = GachaViewModel.factory),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val imgCollection by viewModel.getAllImage().collectAsState(emptyList())
    // Get the name of the current screen
    val currentScreen = CapyGachaScreen.valueOf(
        backStackEntry?.destination?.route ?: CapyGachaScreen.Start.name
    )
    val coroutineScope = rememberCoroutineScope()
    var img by mutableStateOf(defaultImage)


    Scaffold(
        topBar = {
            CapyGachaAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CapyGachaScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = CapyGachaScreen.Start.name) {
                MainScreen(
                    onCollectionClick = {
                        coroutineScope.launch {
                            viewModel.getAllImage()
                        }
                        navController.navigate(CapyGachaScreen.Collection.name)
                    },
                    onSummonClick = {
//                        coroutineScope.launch {
//                            viewModel.insertImage(it)
//                        }
                        navController.navigate(CapyGachaScreen.Summon.name)

                    }
                )
            }
            composable(route = CapyGachaScreen.Summon.name) {
                SummonScreen(
                    img = img,
                    summon = {
                        coroutineScope.launch {
                            img = viewModel.getImage()
                        }
                    }
                )
            }
            composable(route = CapyGachaScreen.Collection.name) {
                CollectionScreen(
                    imgList = imgCollection,
                    summon = {
                        coroutineScope.launch {
                            img = viewModel.getImage()
                        }
                    }
                )
            }
        }
    }
}