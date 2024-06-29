package com.ricardolfernandes.catapi.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class ImageUtils {

    companion object {
        suspend fun downloadImageAsByteArray(imageUrl: String?): ByteArray? {
            return withContext(Dispatchers.IO) {
                try {
                    URL(imageUrl).openStream().use { input ->
                        input.readBytes()
                    }
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

}