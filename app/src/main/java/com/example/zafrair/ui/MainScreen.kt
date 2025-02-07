package com.example.zafrair.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zafrair.navigation.Screen
import com.example.zafrair.ui.Screens.ACScreen
import com.example.zafrair.ui.Screens.HomeScreen
import com.example.zafrair.ui.Screens.SettingsScreen
import com.example.zafrair.ui.components.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.ACs.route) { ACScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}