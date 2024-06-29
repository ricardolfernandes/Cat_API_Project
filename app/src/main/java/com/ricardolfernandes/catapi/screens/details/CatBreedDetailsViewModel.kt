package com.ricardolfernandes.catapi.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardolfernandes.catapi.database.CatBreed
import com.ricardolfernandes.catapi.database.DBRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedDetailsViewModel @Inject constructor(
    private val favouritesRepository: DBRepoImpl
) : ViewModel() {
    private val _favourites = MutableStateFlow<List<CatBreed>>(emptyList())
    val favourites: StateFlow<List<CatBreed>> = _favourites.asStateFlow()

    init {
        getFavourites()
    }

    fun addToFavorites(id: String) {
        viewModelScope.launch {
            val catBreed = favouritesRepository.getCatBreedById(id).firstOrNull()
            if (catBreed != null) {
                catBreed.isFavourite = true
                favouritesRepository.update(catBreed)
            }
            getFavourites()
        }
    }

    private fun getFavourites() {
        viewModelScope.launch {
            favouritesRepository.getAllFavouritesCatBreeds().collect {
                _favourites.value = it
            }

        }
    }

    fun removeFromFavorites(id: String) {
        viewModelScope.launch {
            val catBreed = favouritesRepository.getCatBreedById(id).firstOrNull()
            if (catBreed != null) {
                catBreed.isFavourite = false
                favouritesRepository.update(catBreed)
            }
            getFavourites()
        }
    }
}