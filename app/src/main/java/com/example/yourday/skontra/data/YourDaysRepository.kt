package com.example.yourday.skontra.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class YourDaysRepository @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllDays(context: Context): List<YourDay> {
        val database = YourDayDatabase.getDatabase(context.applicationContext)
        return database.yourDayDao().getAllYourDays()
    }
}