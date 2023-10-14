package com.example.capygacha

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.capygacha.data.defaultImage
import com.example.capygacha.ui.CharacterScreen
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
    Character(title = R.string.character_screen)
}

@Composable
fun CapyGachaAppBar(
    currentScreen: CapyGachaScreen,
    canNavigateBack: Boolean,
    canSaveCharacter: Boolean,
    characterName: String,
    navigateUp: () -> Unit,
    addToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            if (currentScreen == CapyGachaScreen.Character) {
                Text(stringResource(currentScreen.title, characterName))
            } else {
                Text(stringResource(currentScreen.title))
            }
                },
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
        },
        actions = {
            if (canSaveCharacter) {
                IconButton(onClick = addToHome) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_to_home_screen_24),
                        contentDescription = "Add to Home Screen"
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
    var summonImg by mutableStateOf(defaultImage)
    var characterImg by remember {mutableStateOf(defaultImage)}
    //var homeImg by remember {mutableStateOf(defaultImage.resFile)}
    val homeImg = viewModel.uiState.collectAsState().value.homeImage
    var canSaveBoolean by remember { mutableStateOf(false) }
    val mMediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.zanza)

    //mMediaPlayer.start()

    Scaffold(
        topBar = {
            CapyGachaAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                canSaveCharacter = canSaveBoolean,
                characterName = characterImg.name,
                navigateUp = { navController.navigateUp() },
                addToHome = {
                    //homeImg = characterImg.resFile
                    viewModel.selectHomeImage(characterImg.resFile)
                    canSaveBoolean = false
                }
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
                Box {
                    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    Image(
                        painter = painterResource(id = R.drawable.bakkthing),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    MainScreen(
                        img = homeImg,
                        onCollectionClick = {
                            coroutineScope.launch {
                                viewModel.getAllImage()
                            }
                            navController.navigate(CapyGachaScreen.Collection.name)
                        },
                        onSummonClick = {
//                               coroutineScope.launch {
//                                   viewModel.insertImage(it)
//                               }
                            navController.navigate(CapyGachaScreen.Summon.name)

                        }
                    )
                }
            }
            composable(route = CapyGachaScreen.Summon.name) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.bacofground),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    SummonScreen(
                        img = summonImg,
                        summon = {
                            coroutineScope.launch {
                                summonImg = viewModel.getImage()
                            }
                        }
                    )
                }
            }
            composable(route = CapyGachaScreen.Collection.name) {
                Box {
                    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    Image(
                        painter = painterResource(id = R.drawable.backgroundgacha),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    CollectionScreen(
                        imgList = imgCollection,
                        onCardClick = {
                            characterImg = it
                            canSaveBoolean = true
                            navController.navigate(CapyGachaScreen.Character.name)
                        }
                    )
                }
            }
            composable(route = CapyGachaScreen.Character.name) {
                Box {
                    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    Image(
                        painter = painterResource(id = R.drawable.backgroundgacha),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    CharacterScreen(
                        img = characterImg
                    )
                }
            }
        }
    }
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}