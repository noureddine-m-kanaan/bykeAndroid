package com.example.afya.api
import com.example.afya.data.model.LoggedInUser
import com.example.afya.data.model.RegisterRequest
import com.example.afya.data.model.Trip
import com.example.afya.data.model.User
import retrofit2.Response
import retrofit2.http.*
interface API {
    @POST("authentification/login")
    suspend fun login(@Body user: User): Response<LoggedInUser>
    @POST("authentification/register")
    suspend fun register(@Body regReq: RegisterRequest): Response<Void>
    @GET("sortie/getSorties")
    suspend fun getTrips(@Header("Authorization") token: String, @Query("id") id: Int): Response<List<Trip>>
    @POST("sortie/creation")
    suspend fun addTrip(@Body trip: Trip): Response<Void>
}