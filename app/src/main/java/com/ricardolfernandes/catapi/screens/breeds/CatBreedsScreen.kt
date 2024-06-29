package com.ricardolfernandes.catapi.screens.breeds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import com.ricardolfernandes.catapi.database.CatBreed
import com.ricardolfernandes.catapi.navigation.NavigationItem
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO

@Composable
fun CatBreedScreen(viewModel: CatBreedsViewModel, navController: NavController, modifier: Modifier) {
    val breedsDetails by viewModel.breedsDetails.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (searchText.isNotBlank()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear Icon"
                        )
                    }
                }
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(breedsDetails.filter { if(searchText.isNotEmpty()) ( it.breeds?.get(0)?.name?.contains(searchText, ignoreCase = true) == true) else true }) { breedsDetails ->
                CatBreedItem(breedsDetails, modifier, navController, viewModel)
            }
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
    navController: NavController,
    viewModel: CatBreedsViewModel
) {
    Card(onClick = {
        val _breedDetails = Gson().toJson(breedDetails)
        navController.navigate(NavigationItem.Details.route + "?details=${_breedDetails}")
    },
        modifier = modifier.padding(4.dp, 0.dp)) {
//        Icon(Icons.Outlined.Favorite, contentDescription = "fav icon outlined", modifier = Modifier
//            .align(Alignment.End)
//            .padding(6.dp))
        //TODO -> logic to handle favourites
        IconButton(onClick = {
            viewModel.addToFavorites(CatBreed(breedDetails.id!!, breedDetails.breeds?.get(0)?.name, breedDetails.breeds?.get(0)?.origin, breedDetails.breeds?.get(0)?.temperament, breedDetails.breeds?.get(0)?.lifeSpan, breedDetails.breeds?.get(0)?.description, breedDetails.url))
        }) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = "fav icon outlined",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(6.dp)
            )
        }
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