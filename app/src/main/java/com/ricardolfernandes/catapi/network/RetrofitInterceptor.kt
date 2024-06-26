package com.ricardolfernandes.catapi.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetrofitInterceptor: Interceptor {
    private val API_KEY = "live_V02bbCy4wSmKvpO02pBMOe27ihTdEifvmTkUq7ZB2YWciqkbKjxX45i20fGl2np4"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("x-api-key", API_KEY)
            .build()
        val response: Response = chain.proceed(request)
        return response
    }
}