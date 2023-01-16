package com.example.afya.data

import com.google.gson.annotations.Expose


data class Step(
    var id : Int,
    var numEtape : Int,
    var num_sortie: Int?,
    var nom_etape: String?,
    @Expose
    val latitude: Double,
    @Expose
    val longitude : Double,
) : java.io.Serializable {
    override fun toString(): String {
        return "Step(id=$id, numEtape=$numEtape, latitude=$latitude, longitude=$longitude)"
    }
}
