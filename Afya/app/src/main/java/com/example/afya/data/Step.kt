package com.example.afya.data

import com.beust.klaxon.Json

data class Step(
    @Json(ignored = true)
    var id : Int,
    @Json(ignored = true)
    var numEtape : Int,
    @Json(ignored = true)
    var num_sortie: Int?,
    @Json(ignored = true)
    var nom_etape: String?,
    val latitude: Double,
    val longitude : Double,
) : java.io.Serializable {
    override fun toString(): String {
        return "Step(id=$id, numEtape=$numEtape, latitude=$latitude, longitude=$longitude)"
    }
}
