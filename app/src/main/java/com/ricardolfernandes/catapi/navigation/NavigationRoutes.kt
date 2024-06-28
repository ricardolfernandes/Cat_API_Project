package com.ricardolfernandes.catapi.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import com.ricardolfernandes.catapi.screens.breeds.CatBreedScreen
import com.ricardolfernandes.catapi.screens.details.CatBreedDetailsScreen
import com.ricardolfernandes.catapi.screens.favourites.FavouritesScreen

@Composable
fun NavigationRoutes(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.List.route) {
        composable(NavigationItem.List.route) {
            CatBreedScreen(navController, modifier = Modifier.padding(16.dp))
        }
        composable(NavigationItem.Details.route + "?details={details}",
            arguments = listOf(navArgument("catBreedDetails") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val catBreedDetails = backStackEntry.arguments?.getString("details")
            val catBreedDetailsObject = Gson().fromJson(catBreedDetails, CatBreedsDetailsDTO::class.java)
            CatBreedDetailsScreen(catBreedDetailsObject)
        }

    }
}