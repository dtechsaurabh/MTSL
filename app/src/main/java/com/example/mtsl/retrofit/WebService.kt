package com.agatsa.sanketriskusers.network.api

import com.agatsa.sanketriskusers.network.api.apiUtils.ApiConstants
import com.example.mtsl.utils.Myapp
import com.example.mtsl.retrofit.ApiInterface
import com.example.mtsl.retrofit.apiUtils.AuthTokenInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    // Create logging interceptor for debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Authorization Interceptor
    private val authorizationInterceptor = Interceptor { chain ->
       // val token = "Bearer " + Myapp.instance!!.getToken()  // Fetch from SharedPreferences
        val token = "Bearer " + ""  // Fetch from SharedPreferences
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", token)
            .build()

        chain.proceed(request)
    }

    // Common OkHttp Client
    private val httpClient = OkHttpClient.Builder()
        .callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthTokenInterceptor(Myapp.instance!!))
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .build()

    // First Retrofit Instance (Main API)
    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(ApiConstants.BASE_URL)
        .build()

    // Second Retrofit Instance (Symptom API)
    private val retrofitSymptom = Retrofit.Builder()
        .client(httpClient)  // Reusing the same OkHttp client
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(ApiConstants.BASE_URL_TWO)
        .build()

    // Public API Instances
    val webService: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    val symptomService: ApiInterface by lazy {
        retrofitSymptom.create(ApiInterface::class.java)
    }
}
