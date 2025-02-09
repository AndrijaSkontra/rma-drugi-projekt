package com.example.yourday

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "your_day")
@TypeConverters(Converters::class)
data class YourDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productivity: Int,
    val stress: Int,
    val leftComfortZone: Boolean,
    val note: String? = null,
    val overallDayRating: Int,
    val pictureUrl: Uri? = null,
    val date: Long
) {
    init {
        require(productivity in 1..5) { "Productivity must be between 1 and 5" }
        require(stress in 1..5) { "Stress must be between 1 and 5" }
        require(overallDayRating in 1..10) { "Overall day rating must be between 1 and 10" }
    }
}
