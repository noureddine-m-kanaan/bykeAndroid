package com.example.afya.data

import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Keep
@Entity(tableName = "sortie")
class Trip(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "num_util")
    var lastname: Long? = null,
    @ColumnInfo(name = "date_sortie")
    var dateSortie: Date? = null,
    @ColumnInfo(name = "heure_depart")
    var heureDepart: Time? = null,
    @ColumnInfo(name = "heure_arrive")
    var heureArrive: Time? = null,
    @ColumnInfo(name = "lieu_depart")
    var lieuDepart: String? = null,
    @ColumnInfo(name = "distance_parcourue")
    var distanceParcourue: Float? = null
) : java.io.Serializable, BaseObservable()
