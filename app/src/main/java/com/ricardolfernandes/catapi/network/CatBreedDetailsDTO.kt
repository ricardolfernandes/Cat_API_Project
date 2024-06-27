package com.ricardolfernandes.catapi.network

import com.google.gson.annotations.SerializedName

data class CatBreedsDetailsDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("breeds")
    val breeds: List<CatBreedD>
)
data class CatBreedD(
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("description")
    val description: String
)