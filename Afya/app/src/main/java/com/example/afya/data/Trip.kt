package com.example.afya.data

import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.afya.data.Step
import java.sql.Time
import java.util.*

@Keep
@Entity(tableName = "sortie")
data class Trip(
   // @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val tripNum: Int?= null,
    @ColumnInfo(name = "num_util")
    val numUtil: Int?= null,
    @ColumnInfo(name = "date_trip")
    val dateTrip: Date?= null,
    @ColumnInfo(name = "start_time")
    val startTime: Time?= null,
    @ColumnInfo(name = "end_time")
    val endTime: Time?= null,
    @ColumnInfo(name = "distance")
    val distance: Double?= null,
    @ColumnInfo(name = "steps")
    val steps: List<Step>
): java.io.Serializable, BaseObservable()

