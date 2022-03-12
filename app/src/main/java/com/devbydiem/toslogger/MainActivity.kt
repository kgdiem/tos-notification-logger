package com.devbydiem.toslogger

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        populateSheetName()
    }

    public fun saveSheetNameHandler(view: View) {
        val input = findViewById<EditText>(R.id.editTextSheetName)

        val sheetName = input.text.toString()

        saveSheetName(sheetName)
    }

    public fun authorizeDriveHandler(view: View) {
        // TODO: Kick off google authorization flow
    }

    private fun saveSheetName(name: String) {
        val backgroundExecutor = Executors.newSingleThreadScheduledExecutor()

        backgroundExecutor.execute {
            val sheetsService = SheetsService(applicationContext)

            sheetsService.saveSpreadsheetName(name)

            backgroundExecutor.shutdown()
        }
    }

    private fun populateSheetName() {
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)
        val backgroundExecutor = Executors.newSingleThreadScheduledExecutor()

        backgroundExecutor.execute {
            val sheetsService = SheetsService(applicationContext)

            val sheetName = sheetsService.getSheetName()

            println("Retreived sheet name: $sheetName")

            mainExecutor.execute {
                val input = findViewById<EditText>(R.id.editTextSheetName)

                input.setText(sheetName)
            }

            backgroundExecutor.shutdown()
        }
    }
}