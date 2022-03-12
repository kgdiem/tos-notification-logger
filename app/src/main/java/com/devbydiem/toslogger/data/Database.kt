package com.devbydiem.toslogger.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Preference::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preferenceDao(): PreferenceDao
}