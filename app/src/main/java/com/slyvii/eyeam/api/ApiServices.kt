package com.slyvii.eyeam.api

import com.slyvii.eyeam.data.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("everything")
    fun getNews(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>

    @GET("animal")
    fun getAnimalName(
        @Header("Authorization") auth: String,
        @Query("name") name: String
    ): Call<EyeamResponse>

    @Multipart
    @POST("classifier")
    fun postImage(
        @Header("Authorization") auth: String,
        @Part("user_image") user_image: RequestBody
    ): Call<ClassifierResponse>

    @POST("verifyPassword")
    fun postToGetJwt(
        @Query("key") key: String,
        @Body body: LoginRequest
    ): Call<LoginResponse>

    @GET("logs")
    fun getLogs(
        @Header("Authorization") auth: String
    ): Call<HistoryResponse>
}