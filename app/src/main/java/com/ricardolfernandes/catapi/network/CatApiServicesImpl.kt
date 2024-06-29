package com.ricardolfernandes.catapi.network

import android.content.Context
import com.ricardolfernandes.catapi.database.CatBreed
import com.ricardolfernandes.catapi.database.DBRepoImpl
import com.ricardolfernandes.catapi.utils.ImageUtils
import com.ricardolfernandes.catapi.utils.States
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatApiServicesImpl @Inject constructor(
    private val catApiServices: CatApiServices,
    private val dbRepo: DBRepoImpl,
    private val context: Context
) : CatApiRepository {


    override suspend fun getCatBreedDetails(id: String): Flow<States<CatBreedsDetailsDTO?>> = flow {
        emit(States.Loading())

        try {
            val response = catApiServices.getCatBreedDetails(id).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.id?.let {
                    var image = ImageUtils.downloadImageAsByteArray(response.body()?.url)
                    var imagePath = image?.let { it1 -> saveImageToFile(it1) }
                    response.body()?.imagePath = imagePath
                    CatBreed(
                        it,
                        response.body()?.breeds?.get(0)?.name, response.body()?.breeds?.get(0)?.origin, response.body()?.breeds?.get(0)?.temperament, response.body()?.breeds?.get(0)?.lifeSpan, response.body()?.breeds?.get(0)?.description, response.body()?.url, false, imagePath) }
                    ?.let { dbRepo.insert(it) }
                emit(States.Success(response.body()))
            } else {
                emit(States.Error("Couldn't fetch data from the server"))
            }
        } catch (exception: Exception) {

            val localBreed = dbRepo.getCatBreedById(id).firstOrNull()
            if (localBreed != null) {
                val mappedDetails = CatBreedsDetailsDTO(
                    id = localBreed.id,
                    url = localBreed.url,
                    imagePath = localBreed.imagePath,
                    breeds = listOf(
                        CatBreedD(
                            name = localBreed.name,
                            origin = localBreed.origin,
                            temperament = localBreed.temperament,
                            lifeSpan = localBreed.life_span,
                            description = localBreed.description
                        )
                    )
                )
                emit(States.Success(mappedDetails))
            } else {
                emit(States.Error(exception.message ?: "Unknown error"))
            }
        }
    }

    private suspend fun saveImageToFile(imageBytes: ByteArray): String? {
        return withContext(Dispatchers.IO) {
            val fileName = "cat_image_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            try {
                file.outputStream().use { out ->
                    out.write(imageBytes)
                }
                file.absolutePath
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getCatBreedsWithDetails(
        pageLimit: Int,
        pageNumber: Int
    ): Flow<States<List<CatBreedsDetailsDTO>>> = flow {
        emit(States.Loading())

        try {
            val response = catApiServices.getCatBreedsList(pageLimit, pageNumber, 1, "ASC").awaitResponse()
            if(response.isSuccessful) {
                val networkBreeds = response.body() ?: emptyList()
                val fetchedDetailsList = mutableListOf<CatBreedsDetailsDTO>()
                for (breed in networkBreeds) {
                    getCatBreedDetails(breed.id).collect { state ->
                        if (state is States.Success) {
                            state.data?.let { fetchedDetailsList.add(it) }
                        }
                    }
                }
                emit(States.Success(fetchedDetailsList))
            } else {
                emit(States.Error("Couldn't fetch data from the server"))
            }
        } catch (exception: Exception) {
            val localBreeds = dbRepo.getAllCatBreeds().firstOrNull()
            if (localBreeds != null) {
                val mappedDetailsList = localBreeds.map { localBreed ->
                    CatBreedsDetailsDTO(
                        id = localBreed.id,
                        url = localBreed.url,
                        imagePath = localBreed.imagePath,
                        breeds = listOf(
                            CatBreedD(
                                name= localBreed.name,
                                origin = localBreed.origin,
                                temperament = localBreed.temperament,
                                lifeSpan = localBreed.life_span,
                                description = localBreed.description
                            )
                        )
                    )
                }
                emit(States.Success(mappedDetailsList))
            } else {
                emit(States.Error(exception.message ?: "Unknown error"))
            }
        }
    }
}