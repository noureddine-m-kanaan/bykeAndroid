package com.example.afya.database

import androidx.room.*
import com.example.afya.data.Trip

@Dao
interface TripDao {

    @Insert
    fun insert(trip : Trip) : Int

    @Delete
    fun delete(trip: Trip)

    @Query("SELECT * from sortie WHERE id = :key")
    fun get(key: Int): Trip?

    @Query("SELECT * FROM sortie ORDER BY id DESC LIMIT 1")
    fun getLastUser(): Trip?

    @Query("SELECT * FROM sortie")
    fun getUsers(): List<Trip>?

}