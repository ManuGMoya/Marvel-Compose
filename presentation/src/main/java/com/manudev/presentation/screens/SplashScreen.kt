package com.manudev.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manudev.presentation.R
import kotlinx.coroutines.delay

private const val DELAY_TIME = 2000L

@Composable
fun SplashScreen(navController: NavHostController) {
    Splash()
    LaunchedEffect(true) {
        delay(DELAY_TIME)
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        AsyncImage(
            model = R.drawable.logo,
            contentDescription = stringResource(R.string.logo)
        )
        Text(text = stringResource(R.string.welcome_to_marvel_world))
    }
}
