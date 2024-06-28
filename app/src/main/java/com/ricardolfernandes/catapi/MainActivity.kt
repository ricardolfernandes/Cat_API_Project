package com.ricardolfernandes.catapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ricardolfernandes.catapi.screens.MainScreen
import com.ricardolfernandes.catapi.ui.theme.CatAPIProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CatAPIProjectTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    ) {
                    MainScreen(navController = navController)
                }
            }
        }
    }
}