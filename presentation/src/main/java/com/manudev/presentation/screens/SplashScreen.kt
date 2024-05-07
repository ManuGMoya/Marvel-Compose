package com.manudev.presentation.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manudev.presentation.R
import kotlinx.coroutines.delay

private const val ANIMATION_DELAY = 500L
private const val ROTATION_DURATION = 2000
private const val NAVIGATION_DELAY = 3000L

@Composable
fun SplashScreen(navController: NavHostController) {
    Splash()
    LaunchedEffect(true) {
        delay(NAVIGATION_DELAY)
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }
}

@Composable
fun Splash() {
    var isAnimating by remember { mutableStateOf(true) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isAnimating) 360f else 0f,
        animationSpec = tween(durationMillis = ROTATION_DURATION, easing = LinearEasing), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .rotate(rotationAngle),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = R.drawable.logo,
            contentDescription = stringResource(R.string.logo)
        )
        Text(text = stringResource(R.string.welcome_to_marvel_world))
    }

    LaunchedEffect(key1 = true) {
        delay(ANIMATION_DELAY)
        isAnimating = false
    }
}
