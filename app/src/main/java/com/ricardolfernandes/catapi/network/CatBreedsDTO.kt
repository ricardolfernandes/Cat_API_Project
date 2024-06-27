package com.ricardolfernandes.catapi.network

import com.google.gson.annotations.SerializedName

data class CatBreedDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)