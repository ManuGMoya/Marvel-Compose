package com.manudev.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.manudev.presentation.screens.detail.Detail
import com.manudev.presentation.screens.home.HomeScreen

sealed class Screen(val route: String) {

    data object Splash : Screen(route = "splash")
    data object Home : Screen(route = "home")
    data object Detail : Screen(route = "detail/{id}") {
        fun createRoute(id: Int): String = "detail/$id"
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Screen {
        NavHost(navController = navController, startDestination = Screen.Splash.route) {

            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onItemClick = {
                        navController.navigate(Screen.Detail.createRoute(it))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id: Int = backStackEntry.arguments?.getInt("id") ?: -1
                Detail(
                    id = id,
                    navController = navController
                )
            }
        }

    }
}