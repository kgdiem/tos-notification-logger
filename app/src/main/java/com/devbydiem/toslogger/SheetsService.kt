package com.devbydiem.toslogger

import android.content.Context
import androidx.room.EmptyResultSetException
import androidx.room.Room
import com.devbydiem.toslogger.data.AppDatabase
import com.devbydiem.toslogger.data.Preference

// https://developers.google.com/sheets/api/guides/libraries#java

class SheetsService {
    private val db: AppDatabase

    constructor(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "tos-logger"
        ).build()
    }

    public fun authorize() {
        // TODO: Perform authentication for Google Drive
    }

    public fun saveSpreadsheetName(sheetName: String) {
        println("Saving sheet name: $sheetName")
        // Write spreadsheet name to the database
        val preferenceDao = db.preferenceDao()

        try {
            val sheetPreference = preferenceDao.findByName("sheetName")

            if(sheetPreference != null) {
                sheetPreference.value = sheetName

                preferenceDao.update(sheetPreference)
            } else {
                val sheetPreference = Preference(0, "sheetName", sheetName)

                preferenceDao.insert(sheetPreference)
            }
        } catch (error: EmptyResultSetException) {
            val sheetPreference = Preference(0, "sheetName", sheetName)

            preferenceDao.insert(sheetPreference)
        }
    }

    public fun getSheetName(): String {
        // Return the sheet name from the databasee
        val preferenceDao = db.preferenceDao()

        return try {
            val sheetPreference = preferenceDao.findByName("sheetName")

            sheetPreference?.value.orEmpty()
        } catch (error: EmptyResultSetException) {
            ""
        }
    }

    public fun writeNotification(title: String?, longTitle: String?, text: String?, longText: String?) {
        // TODO: Write the notification to the sheet
    }

    private fun getSpreadsheet() {
        // TODO: Get the sheet ID
    }

    private fun getFirstWorkbook() {
        // TODO: Get first workbook in the sheet
    }

    private fun getLastRow() {
        // TODO: Get last row
    }
}