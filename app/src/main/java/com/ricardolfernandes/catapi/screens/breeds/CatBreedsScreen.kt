package com.ricardolfernandes.catapi.screens.breeds

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import com.ricardolfernandes.catapi.navigation.NavigationItem
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO

@Composable
fun CatBreedScreen(navController: NavController, modifier: Modifier) {
    val viewModel = CatBreedsViewModel()
    val breedsDetails by viewModel.breedsDetails.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier
    ) {
        items(breedsDetails) { breedDetails ->
            CatBreedItem(breedDetails, modifier, navController)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCatBreeds()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatBreedItem(
    breedDetails: CatBreedsDetailsDTO,
    modifier: Modifier,
    navController: NavController
) {
    Card(onClick = {
        val _breedDetails = Gson().toJson(breedDetails)
        navController.navigate(NavigationItem.Details.route + "?details=${_breedDetails}")
    },
        modifier = modifier.padding(4.dp, 0.dp)) {
        Icon(Icons.Outlined.Favorite, contentDescription = "fav icon outlined", modifier = Modifier
            .align(Alignment.End)
            .padding(6.dp))
        //TODO -> logic to handle favourites
        //Icon(Icons.Default.FavoriteBorder, contentDescription = "fav icon outlined", modifier = Modifier.align(Alignment.End).padding(6.dp))
        GlideImage(
            model =  breedDetails.url,
            contentDescription = "cat",
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .size(100.dp),
            contentScale = ContentScale.Fit
        )
        breedDetails.breeds?.get(0)?.name?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

    }
}