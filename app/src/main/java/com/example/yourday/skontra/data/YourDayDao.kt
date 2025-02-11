package com.example.yourday.skontra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface YourDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(yourDay: YourDay)

    @Query("SELECT * FROM your_day ORDER BY date DESC")
    suspend fun getAllYourDays(): List<YourDay>

    @Delete
    suspend fun delete(yourDay: YourDay)

    @Update
    suspend fun update(yourDay: YourDay)
}