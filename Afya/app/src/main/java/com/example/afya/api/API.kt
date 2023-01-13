package com.example.afya.api

import com.example.afya.data.model.LoggedInUser
import com.example.afya.data.model.RegisterRequest
import com.example.afya.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface API {
    @POST("authentification/login")
    suspend fun login(@Body user: User): Response<LoggedInUser>
    @POST("authentification/register")
    suspend fun register(@Body regReq: RegisterRequest): Response<Void>
}