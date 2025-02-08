package com.example.zafrair

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.zafrair.ui.MainScreen
import com.example.zafrair.ui.theme.provideAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            provideAppTheme {
                MainScreen()
            }
        }
    }
}