package com.example.zafrair.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

class BottomNavItem(
    val screen: Screen,
    @StringRes val titleResId: Int,
    val icon : ImageVector
)
