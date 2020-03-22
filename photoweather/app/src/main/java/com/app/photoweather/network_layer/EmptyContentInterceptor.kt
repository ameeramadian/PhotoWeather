package com.app.photoweather.network_layer

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class EmptyContentInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val response = chain.proceed(requestBuilder.build())

        // Throw specific Exception on HTTP 204 response code
        if (response.code == 204) {
            throw NoContentException("There is no content")
        }

        return response // Carry on with the response
    }
}

class NoContentException(reason: String) : Exception(reason)