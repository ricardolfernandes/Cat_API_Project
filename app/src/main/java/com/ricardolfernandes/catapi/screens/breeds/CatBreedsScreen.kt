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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ricardolfernandes.catapi.ui.theme.CatAPIProjectTheme

@Composable
fun CatBreedScreen(modifier: Modifier) {
    val viewModel = CatBreedsViewModel()
    val breedsDetails by viewModel.breedsDetails.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier
    ) {
        items(breedsDetails) { breedDetails ->
            CatBreedItem(breedDetails.breeds[0].name, breedDetails.url, modifier)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCatBreeds()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatBreedItem(breedName: String, breedImageUrl: String, modifier: Modifier) {
    Card(onClick = {
        // TODO -> go to details screen
    },
        modifier = modifier.padding(4.dp, 0.dp)) {
        Icon(Icons.Outlined.Favorite, contentDescription = "fav icon outlined", modifier = Modifier.align(Alignment.End).padding(6.dp))
        //TODO -> logic to handle favourites
        //Icon(Icons.Default.FavoriteBorder, contentDescription = "fav icon outlined", modifier = Modifier.align(Alignment.End).padding(6.dp))
        GlideImage(
            model =  breedImageUrl,
            contentDescription = "cat",
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .size(100.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = breedName,
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatAPIProjectTheme {
        CatBreedItem("Test breed", "https://media.istockphoto.com/id/1443562748/photo/cute-ginger-cat.jpg?s=612x612&w=0&k=20&c=vvM97wWz-hMj7DLzfpYRmY2VswTqcFEKkC437hxm3Cg=", modifier = Modifier.padding(16.dp))
    }
}