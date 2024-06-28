package com.ricardolfernandes.catapi.network

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CatBreedsDetailsDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("breeds")
    val breeds: List<CatBreedD>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(CatBreedD)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(url)
        parcel.writeTypedList(breeds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CatBreedsDetailsDTO> {
        override fun createFromParcel(parcel: Parcel): CatBreedsDetailsDTO {
            return CatBreedsDetailsDTO(parcel)
        }

        override fun newArray(size: Int): Array<CatBreedsDetailsDTO?> {
            return arrayOfNulls(size)
        }
    }
}

data class CatBreedD(
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @SerializedName("description")
    val description: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(origin)
        parcel.writeString(temperament)
        parcel.writeString(lifeSpan)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CatBreedD> {
        override fun createFromParcel(parcel: Parcel): CatBreedD {
            return CatBreedD(parcel)
        }

        override fun newArray(size: Int): Array<CatBreedD?> {
            return arrayOfNulls(size)
        }
    }
}