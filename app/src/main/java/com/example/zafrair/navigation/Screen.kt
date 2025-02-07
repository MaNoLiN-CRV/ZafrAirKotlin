package com.example.zafrair.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home");
    object ACs : Screen("ACs");
    object Settings : Screen("settings");

}