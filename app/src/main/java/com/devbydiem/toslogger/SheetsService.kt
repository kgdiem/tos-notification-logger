package com.devbydiem.toslogger

import java.io.File
import java.io.FileWriter

class SheetsService {
    val sheetName = "tos-log.csv"
    val dirName = "spreadsheets"

    fun openSheet(filesDir: File): File {
        val sheetPath = File(filesDir, dirName)

        if(!sheetPath.exists()){
            sheetPath.mkdir();
        }

        val file = File(sheetPath.path, sheetName)

        file.createNewFile()

        return file
    }

    fun writeNotification(filesDir: File, title: String?, longTitle: String?, text: String?, longText: String?) {
        val file = openSheet(filesDir)

        val writer = FileWriter(file)

        writer.append("$title,$longTitle,$text,$longText")

        writer.flush()

        writer.close()
    }
}