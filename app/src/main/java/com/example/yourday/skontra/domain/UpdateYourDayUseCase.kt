package com.example.yourday.skontra.domain

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.yourday.skontra.data.YourDay
import com.example.yourday.skontra.data.YourDaysRepository
import javax.inject.Inject

class UpdateYourDayUseCase @Inject constructor() {

    @Inject
    lateinit var repository: YourDaysRepository

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun update(context: Context, newYourDay: YourDay) {
        return repository.updateYourDay(context, newYourDay)
    }
}
