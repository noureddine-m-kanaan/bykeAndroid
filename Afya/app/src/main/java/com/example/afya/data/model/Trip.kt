package com.example.afya.data.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Trip(var num_sortie: Int?, var num_util: Int?, var date_sortie: String?,
           var heure_depart: String?, var heure_arrivee: String?, var lieu_depart: String?,
           var distance_parcourue: Double?, var etapesByNum_sortie: List<Step>?
           ) : Serializable {
    fun formatDate() {
        val dateSortie = SimpleDateFormat("dd MMMM yyyy", Locale("fr")).format(
            SimpleDateFormat("yyyy-MM-dd").parse(date_sortie!!)!!
        )
        this.date_sortie = dateSortie
    }

    fun formatHeure() {
        val heureDepart = SimpleDateFormat("H'h'mm").format(
            SimpleDateFormat("HH:mm:ss").parse(heure_depart!!)!!
        )
        this.heure_depart = heureDepart

        val heureArrivee = SimpleDateFormat("H'h'mm").format(
            SimpleDateFormat("HH:mm:ss").parse(heure_arrivee!!)!!
        )
        this.heure_arrivee = heureArrivee
    }

    fun format() {
        formatDate()
        formatHeure()
    }
}