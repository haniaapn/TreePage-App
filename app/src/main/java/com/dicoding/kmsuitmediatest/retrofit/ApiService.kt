package com.dicoding.kmsuitmediatest.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UserResponse>

    @GET("users/{id}")
    fun getUserName(
        @Path("id") id: Int
    ): Call<DataItem>
}
