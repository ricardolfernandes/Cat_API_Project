package com.ricardolfernandes.catapi.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ricardolfernandes.catapi.screens.breeds.CatBreedScreen
import com.ricardolfernandes.catapi.screens.favourites.FavouritesScreen

@Composable
fun NavigationRoutes(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.List.route) {
        composable(NavigationItem.List.route) {
            CatBreedScreen(modifier = Modifier.padding(16.dp))
        }
    }
}