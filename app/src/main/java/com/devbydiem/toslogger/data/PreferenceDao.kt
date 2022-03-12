package com.devbydiem.toslogger.data

import androidx.room.*
import com.devbydiem.toslogger.data.Preference

@Dao
interface PreferenceDao {
    @Query("SELECT * FROM preference WHERE name = :name")
    fun findByName(name: String): Preference?

    @Insert
    fun insert(preference: Preference)

    @Insert
    fun insertAll(vararg preferences: Preference)

    @Update
    fun update(preference: Preference)

    @Update
    fun updateAll(vararg preferences: Preference)
}
