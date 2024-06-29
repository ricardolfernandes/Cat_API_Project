package com.ricardolfernandes.catapi.screens.breeds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.gson.Gson
import com.ricardolfernandes.catapi.R
import com.ricardolfernandes.catapi.database.CatBreed
import com.ricardolfernandes.catapi.navigation.NavigationItem
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import com.ricardolfernandes.catapi.screens.favourites.FavouritesViewModel
import com.ricardolfernandes.catapi.utils.States

@Composable
fun CatBreedScreen(viewModel: CatBreedsViewModel, navController: NavController, modifier: Modifier) {
    var searchText by remember { mutableStateOf("") }
    val favourites by viewModel.favourites.collectAsState()
    val breedsDetailsState by viewModel.breedsDetailsState.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    var shouldLoadMore by remember { mutableStateOf(true) }


    Column(modifier = modifier.fillMaxSize()) {
        when (breedsDetailsState) {
            is States.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is States.Success -> {
                val breedsDetails = breedsDetailsState.data ?:emptyList()
                if(breedsDetails.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.empty_data_list), style = MaterialTheme.typography.headlineSmall)
                    }
                } else {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        label = { Text(stringResource(R.string.general_search)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,contentDescription = stringResource(
                                    R.string.search_icon
                                )
                            )
                        },
                        trailingIcon = {
                            if (searchText.isNotBlank()) {
                                IconButton(onClick = { searchText = "" }) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = stringResource(R.string.clear_icon)
                                    )
                                }
                            }
                        }
                    )

                    val filteredBreeds = breedsDetails.filter {
                        if (searchText.isNotEmpty()) {
                            it.breeds?.get(0)?.name?.contains(searchText, ignoreCase = true) == true
                        } else {
                            true
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        modifier = modifier.fillMaxSize()
                    ) {
                        itemsIndexed(filteredBreeds) { index, breedsDetails ->
                            CatBreedItem(breedsDetails, modifier, navController, viewModel, null,favourites)
                            if (breedsDetailsState is States.Success &&
                                !viewModel.isLastPage &&
                                !isLoadingMore &&
                                index == filteredBreeds.lastIndex) {
                                shouldLoadMore = true
                            }
                        }

                        item {
                            if (isLoadingMore) {
                                CircularProgressIndicator(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                                )

                            }
                        }
                    }
                }
            }
            is States.Error -> {
                Text(
                    text = stringResource(R.string.error_with_description, "" + breedsDetailsState.message),
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.getCatBreeds()
            shouldLoadMore = false
        }
        viewModel.getFavourites()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatBreedItem(
    breedDetails: CatBreedsDetailsDTO,
    modifier: Modifier,
    navController: NavController,
    viewModel: CatBreedsViewModel?,
    favouritesViewModel: FavouritesViewModel?,
    favourites: List<CatBreed>?
) {
    key(breedDetails.id + (favourites?.any { it.id == breedDetails.id } ?: false)) {
        Card(
            onClick = {
                val _breedDetails = Gson().toJson(breedDetails)
                navController.navigate(NavigationItem.Details.route + "?details=${_breedDetails}")
            },
            modifier = modifier.padding(4.dp, 0.dp)
        ) {
            IconButton(onClick = {
                if (favouritesViewModel != null)
                    if (favouritesViewModel.favourites.value.any { it.id == breedDetails.id })
                        favouritesViewModel.removeFromFavorites(breedDetails.id!!)
                    else
                        favouritesViewModel.addToFavorites(breedDetails.id!!)

                if (viewModel != null)
                    if (viewModel.favourites.value.any { it.id == breedDetails.id })
                        viewModel.removeFromFavorites(breedDetails.id!!)
                    else
                        viewModel.addToFavorites(breedDetails.id!!)

            }) {
                if (favouritesViewModel != null)
                    if (favouritesViewModel.favourites.value.any { it.id == breedDetails.id })
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = stringResource(R.string.favourite_icon_outlined),
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(6.dp)
                        )
                    else
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favourite_icon_default),
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(6.dp)
                        )

                if (viewModel != null)
                    if (viewModel.favourites.value.any { it.id == breedDetails.id })
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = stringResource(R.string.favourite_icon_outlined),
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(6.dp)
                        )
                    else
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favourite_icon_default),
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(6.dp)
                        )
            }
            GlideImage(
                model = breedDetails.imagePath,
                contentDescription = stringResource(R.string.picture_of_a) + breedDetails.breeds?.get(0)?.name + stringResource(
                    R.string.cat
                ),
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
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
            }
            if (favouritesViewModel != null) {
                breedDetails.breeds?.get(0)?.lifeSpan?.let {
                    Text(
                        text = stringResource(R.string.avg_lifespan) + it.split('-')[0] + stringResource(
                            R.string.years
                        ),
                        modifier = Modifier
                            .padding(16.dp, 0.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}