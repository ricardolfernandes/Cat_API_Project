package com.ricardolfernandes.catapi.network

import com.google.gson.annotations.SerializedName

data class CatBreedDetailsDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("breeds")
    val breeds: List<Breed>
)