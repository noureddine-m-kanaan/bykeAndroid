package com.example.afya.data

import com.beust.klaxon.Json

data class Step(
    @Json(name = "id")
    val id : Int,
    val numEtape : Int,
    val latitude: Double,
    val longitude : Double,
) : java.io.Serializable {
    override fun toString(): String {
        return "Etape(int=$id, numEtape=$numEtape, latitude=$latitude, longitude=$longitude)"
    }
}
