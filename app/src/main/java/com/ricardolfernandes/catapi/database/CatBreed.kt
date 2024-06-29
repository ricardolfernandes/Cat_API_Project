package com.ricardolfernandes.catapi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CatBreed(
    @PrimaryKey val id: String,
    val name: String?,
    val origin: String?,
    val temperament: String?,
    val life_span: String?,
    val description: String?,
    val url: String?

)
