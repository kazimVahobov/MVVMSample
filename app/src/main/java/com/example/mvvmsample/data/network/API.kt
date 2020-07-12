package com.example.mvvmsample.data.network

import com.example.mvvmsample.data.network.responses.AuthResponse
import com.example.mvvmsample.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {

    @FormUrlEncoded
    @POST(Constants.LOGIN_API)
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST(Constants.SIGN_UP_API)
    suspend fun userSignUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Response<AuthResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): API {

            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}