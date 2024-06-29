package com.ricardolfernandes.catapi.screens.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricardolfernandes.catapi.network.CatBreedD
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import com.ricardolfernandes.catapi.screens.breeds.CatBreedItem

@Composable
fun FavouritesScreen(viewModel: FavouritesViewModel, navController: NavController, modifier: Modifier) {
    val favourites by viewModel.favourites.collectAsState()

    if (favourites.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No favourites yet!", style = MaterialTheme.typography.headlineSmall)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = modifier
        ) {
            items(favourites) { favourite ->
                var breeds = emptyList<CatBreedD>().toMutableList()
                breeds.add(
                    CatBreedD(
                        favourite.name,
                        favourite.origin,
                        favourite.temperament,
                        favourite.life_span,
                        favourite.description
                    )
                )
                var catBreedDetails = CatBreedsDetailsDTO(favourite.id, favourite.url, favourite.imagePath,breeds)
                favourite.url?.let {
                    CatBreedItem(
                        catBreedDetails,
                        modifier,
                        navController,
                        null,
                        viewModel,
                        favourites
                    )
                }
            }
        }
    }
}