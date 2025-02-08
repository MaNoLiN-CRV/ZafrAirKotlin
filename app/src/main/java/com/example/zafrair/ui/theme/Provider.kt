package com.example.zafrair.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

val LocalAppTheme = compositionLocalOf<AppTheme> {
    error("No theme provided")
}


@Composable
fun provideAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val theme = remember(darkTheme) {
        if (darkTheme) ThemeImpl.DarkTheme else ThemeImpl.LightTheme
    }

    CompositionLocalProvider(LocalAppTheme provides theme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = theme.colors.background
        ) {
            content()
        }
    }
}