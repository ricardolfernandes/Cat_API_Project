package com.ricardolfernandes.catapi.screens.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardolfernandes.catapi.database.CatBreed
import com.ricardolfernandes.catapi.database.DBRepoImpl
import com.ricardolfernandes.catapi.network.CatApiServicesImpl
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import com.ricardolfernandes.catapi.utils.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(
    private val impl: CatApiServicesImpl,
    private val favouritesRepository: DBRepoImpl
) : ViewModel() {

    private val _favourites = MutableStateFlow<List<CatBreed>>(emptyList())
    val favourites: StateFlow<List<CatBreed>> = _favourites.asStateFlow()

    private val _breedsDetailsState = MutableStateFlow<States<List<CatBreedsDetailsDTO>>>(States.Loading())
    val breedsDetailsState: StateFlow<States<List<CatBreedsDetailsDTO>>> = _breedsDetailsState.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private var currentPage = 1
    var isLastPage = false

    fun getCatBreeds() {
        viewModelScope.launch {
            if (!isLastPage && !_isLoadingMore.value) {
                _isLoadingMore.value = true
                impl.getCatBreedsWithDetails(10, currentPage).collect { state ->
                    when(state) {
                        is States.Success -> {
                            val newBreeds = state.data ?: emptyList()
                            if (newBreeds.isEmpty()) {
                                isLastPage = true
                                _breedsDetailsState.value = States.Success(newBreeds.toList())
                                _isLoadingMore.value = false
                            } else {
                                val currentBreeds = _breedsDetailsState.value.data?.toMutableList() ?: mutableListOf()
                                currentBreeds.addAll(newBreeds)
                                _breedsDetailsState.value = States.Success(currentBreeds.toList())
                                currentPage++
                                _isLoadingMore.value = false
                            }
                        }
                        is States.Error -> {
                            _breedsDetailsState.value = state
                            _isLoadingMore.value = false
                        }
                        is States.Loading -> {
                            if (currentPage == 1) {
                                _breedsDetailsState.value = state
                            }
                        }
                    }

                }
            }
        }
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

    fun getFavourites() {
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