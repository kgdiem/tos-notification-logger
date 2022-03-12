package com.devbydiem.toslogger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Preference(
    @PrimaryKey(true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "value") var value: String
)