package com.example.reclaim.chatgpt

import com.squareup.okhttp.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    private const val BASE_URL = "https://api.openai.com/"

    private val httpClient = okhttp3.OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(httpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val apiService : OpenAiApi = retrofit.create(OpenAiApi::class.java)

}