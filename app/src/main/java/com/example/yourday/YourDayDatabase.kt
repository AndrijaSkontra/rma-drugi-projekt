package com.example.yourday

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [YourDay::class], version = 1)
@TypeConverters(Converters::class)
abstract class YourDayDatabase : RoomDatabase() {
    abstract fun yourDayDao(): YourDayDao

    companion object {
        @Volatile
        private var INSTANCE: YourDayDatabase? = null

        fun getDatabase(context: Context): YourDayDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YourDayDatabase::class.java,
                    "your_day_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}