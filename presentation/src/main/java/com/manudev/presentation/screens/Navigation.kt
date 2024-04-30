package com.manudev.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manudev.presentation.screens.detail.Detail
import com.manudev.presentation.screens.home.Home

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home")
    data object Detail : Screen(route = "detail")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Screen {
        NavHost(navController = navController, startDestination = "home") {
            composable(Screen.Home.route) {
                Home()
            }
            composable(Screen.Detail.route) {
                Detail()
            }
        }

    }
}