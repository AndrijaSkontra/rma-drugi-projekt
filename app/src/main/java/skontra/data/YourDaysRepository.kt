package skontra.data

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

    suspend fun createYourDay(context: Context, newYourDay: YourDay) {
        val database = YourDayDatabase.getDatabase(context.applicationContext)
        return database.yourDayDao().insert(newYourDay)
    }

    suspend fun deleteYourDay(context: Context, yourDay: YourDay) {
        val database = YourDayDatabase.getDatabase(context.applicationContext)
        return database.yourDayDao().delete(yourDay)
    }

    suspend fun updateYourDay(context: Context, yourDay: YourDay) {
        val database = YourDayDatabase.getDatabase(context.applicationContext)
        return database.yourDayDao().update(yourDay)
    }
}