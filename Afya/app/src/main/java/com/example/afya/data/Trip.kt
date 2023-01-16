package com.example.afya.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Trip(var num_sortie: Int?, var num_util: Int?, var date_sortie: String, var heure_depart: String?, var heure_arrivee: String?, var lieur_depart: String?, var distance_parcourue: Double, var etapesByNum_sortie: List<Step>?) {
    @SuppressLint("SimpleDateFormat")
    fun formatDate() {
        val dateSortie = SimpleDateFormat("dd MMMM yyyy", Locale("fr")).format(
            SimpleDateFormat("yyyy-MM-dd").parse(date_sortie!!)!!
        )
        println(dateSortie)
        this.date_sortie = dateSortie
    }

    override fun toString(): String {
        return "Trip(num_sortie=$num_sortie, num_util=$num_util, date_sortie='$date_sortie', heure_depart=$heure_depart, heure_arrivee=$heure_arrivee, lieur_depart=$lieur_depart, distance_parcourue=$distance_parcourue, etapesByNum_sortie=$etapesByNum_sortie)"
    }
}