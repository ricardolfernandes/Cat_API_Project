package com.ricardolfernandes.catapi.screens.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardolfernandes.catapi.network.CatApiServicesImpl
import com.ricardolfernandes.catapi.network.CatBreedDTO
import com.ricardolfernandes.catapi.network.CatBreedsDetailsDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatBreedsViewModel : ViewModel() {
    private val _breedsDetails = MutableStateFlow<List<CatBreedsDetailsDTO>>(emptyList())
    val breedsDetails: StateFlow<List<CatBreedsDetailsDTO>> = _breedsDetails.asStateFlow()

    fun getCatBreeds() {
        viewModelScope.launch {
            val fetchedBreeds = CatApiServicesImpl().getCatBreedsList(10, 1)
            for(breed in fetchedBreeds) {
                getCatBreedDetails(breed.id)
            }

        }
    }

    private fun getCatBreedDetails(id: String) {
        viewModelScope.launch {
            val fetchedBreedDetails = CatApiServicesImpl().getCatBreedDetails(id)
            if (fetchedBreedDetails != null) {
                _breedsDetails.value += fetchedBreedDetails
            }
        }
    }
}