package com.example.mtsl.retrofit.apiUtils

import android.content.Context
import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Hardcoded username and password
        val username = "admin"
        val password = "admin!@#123\$dg33drhd)(^%&<>/;ahgdtr*&^%"

        // Create Basic authentication header
        val basicAuth = Credentials.basic(username, password)

        val requestBuilder = originalRequest.newBuilder()
            .header(ApiConstants.AUTHORIZATION, basicAuth)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

