package com.example.yourday

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import kotlin.random.Random

data class YourDay(
    val productivity: Int,
    val stress: Int,
    val leftComfortZone: Boolean,
    val foodQuality: Int,
    val note: String? = null,
    val overallDayRating: Int,
    val pictureUrl: String? = null,
    val date: LocalDate
) {
    init {
        require(productivity in 1..5) { "Productivity must be between 1 and 5" }
        require(stress in 1..5) { "Stress must be between 1 and 5" }
        require(foodQuality in 1..10) { "Food quality must be between 1 and 10" }
        require(overallDayRating in 1..10) { "Overall day rating must be between 1 and 10" }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateYourDayList(amount: Int): List<YourDay> {
    return List(amount) { index ->
        YourDay(
            productivity = Random.nextInt(1, 6),
            stress = Random.nextInt(1, 6),
            leftComfortZone = Random.nextBoolean(),
            foodQuality = Random.nextInt(1, 11),
            note = listOf(null, "Had a great day!", "Felt a bit off today", "Productive morning, lazy evening").random(),
            overallDayRating = Random.nextInt(1, 11),
            pictureUrl = listOf(null, "https://example.com/pic1.jpg", "https://example.com/pic2.jpg").random(),
            date = LocalDate.now().minusDays(index.toLong())
        )
    }
}