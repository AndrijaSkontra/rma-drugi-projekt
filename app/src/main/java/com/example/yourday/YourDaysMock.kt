package com.example.yourday

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
suspend fun generateYourDayList(context: Context): List<YourDay> {
    val database = YourDayDatabase.getDatabase(context.applicationContext)
    return database.yourDayDao().getAllYourDays()
}