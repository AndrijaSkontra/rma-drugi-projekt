package skontra.domain

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import skontra.data.YourDay
import skontra.data.YourDaysRepository
import javax.inject.Inject

class AddYourDayUseCase @Inject constructor() {

    @Inject
    lateinit var repository: YourDaysRepository

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun add(context: Context, newYourDay: YourDay) {
        return repository.createYourDay(context, newYourDay)
    }
}