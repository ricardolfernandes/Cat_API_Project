package com.ricardolfernandes.catapi.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ricardolfernandes.catapi.R
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import com.ricardolfernandes.catapi.screens.MyTopAppBar

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatBreedDetailsScreen(
    viewModel: CatBreedDetailsViewModel,
    breedDetails: CatBreedsDetailsDTO?,
    navController: NavController
) {
    val favorites by viewModel.favourites.collectAsState()
    Scaffold(
        topBar = {
            breedDetails?.breeds?.get(0)?.name?.let {
                MyTopAppBar(
                    title = it,
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(onClick = {
                if (favorites.any { it.id == breedDetails?.id })
                    viewModel.removeFromFavorites(breedDetails?.id!!)
                else
                    viewModel.addToFavorites(breedDetails?.id!!)
            }, modifier = Modifier.align(Alignment.End)) {
            if (favorites.any { it.id == breedDetails?.id })
                Icon(
                    Icons.Outlined.Favorite,
                    contentDescription = stringResource(R.string.favourite_icon_outlined),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(6.dp)
                        .size(48.dp)
                )
            else
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favourite_icon_default),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(6.dp)
                        .size(48.dp)
                )
            }

            GlideImage(
                model = breedDetails?.imagePath,
                contentDescription = stringResource(R.string.picture_of_a) + breedDetails?.breeds?.get(0)?.name + stringResource(R.string.cat),
                modifier = Modifier
                    .padding(0.dp, 26.dp)
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )
            breedDetails?.breeds?.get(0)?.let { it.description?.let { it1 -> Text(text = it1, modifier = Modifier.padding(16.dp, 0.dp)) } }
            HorizontalDivider(modifier = Modifier.padding(16.dp))
            Text(text = stringResource(R.string.origin) + (breedDetails?.breeds?.get(0)?.origin ?: ""), modifier = Modifier.padding(16.dp, 0.dp))
            HorizontalDivider(modifier = Modifier.padding(16.dp))
            Text(text = stringResource(R.string.temperament), modifier = Modifier.padding(16.dp, 0.dp))
            var list = breedDetails?.breeds?.get(0)?.temperament?.split(",")
            list?.let {
                for (item in list)
                    Text(text = "• $item", fontSize = 16.sp, modifier = Modifier.padding(16.dp, 0.dp))
            }
        }
    }

}