package com.manudev.presentation.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manudev.presentation.screens.Screen
import com.manudev.presentation.theme.MangoTestTheme

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel()
) {
    Home()
}

@Composable
fun Home() {
    Screen {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MangoTestTheme {
        Greeting("Android")
    }
}
