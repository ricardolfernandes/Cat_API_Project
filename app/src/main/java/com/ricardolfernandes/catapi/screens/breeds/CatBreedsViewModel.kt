package com.ricardolfernandes.catapi.screens.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardolfernandes.catapi.database.CatBreed
import com.ricardolfernandes.catapi.network.CatApiServicesImpl
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedsViewModel @Inject constructor(private val impl: CatApiServicesImpl) : ViewModel() {
    private val _breedsDetails = MutableStateFlow<List<CatBreedsDetailsDTO>>(emptyList())
    val breedsDetails: StateFlow<List<CatBreedsDetailsDTO>> = _breedsDetails.asStateFlow()
    fun getCatBreeds() {
        viewModelScope.launch {
            val fetchedBreeds = impl.getCatBreedsList(10, 1)
            for(breed in fetchedBreeds) {
                getCatBreedDetails(breed.id)
            }
        }
    }

    private fun getCatBreedDetails(id: String) {
        viewModelScope.launch {
            val fetchedBreedDetails = impl.getCatBreedDetails(id)
            if (fetchedBreedDetails != null) {
                _breedsDetails.value += fetchedBreedDetails
            }
        }
    }

    fun addToFavorites(catBreed: CatBreed) {
//        viewModelScope.launch {
//            catRepository.insert(catBreed)
//        }
    }
}