package com.example.afya.data

import com.example.afya.api.API
import com.example.afya.data.model.LoggedInUser
import com.example.afya.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val resp = Retrofit.Builder()
                .baseUrl(ApiAdress.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
                .login(User(username, password))
            if (resp.isSuccessful) {
                Result.Success(resp.body()!!)
            } else {
                Result.Error(IOException("Nom d'utilisateur ou mot de passe incorrect"))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Erreur de connexion au serveur", e))
        }
    }
}