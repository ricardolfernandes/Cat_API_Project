package com.ricardolfernandes.catapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL


suspend fun downloadImageAsByteArray(imageUrl: String): ByteArray? {
    return withContext(Dispatchers.IO) {
        try {
            URL(imageUrl).openStream().use { input ->
                input.readBytes()
            }
        } catch (e: Exception) {
            // Handle exceptions (e.g., network errors)
            null}
    }
}

fun diplayImage() {
//        val entity = myDao.getEntity(id)
//        val bitmap = BitmapFactory.decodeByteArray(entity.imageData, 0, entity.imageData?.size ?: 0)
//        imageView.setImageBitmap(bitmap)
}