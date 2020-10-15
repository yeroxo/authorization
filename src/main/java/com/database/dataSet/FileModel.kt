package com.database.dataSet

import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class FileModel internal constructor(val file: File) {
    val timeCreated: String
    val size: String

    fun isFile(): Boolean {
        return file.isFile
    }

    val isDirectory: Boolean
        get() = file.isDirectory

    val isHidden: Boolean
        get() = file.isHidden

    val absolutePath: String
        get() = file.absolutePath.replace("\\","/")

    val name: String
        get() = file.name

    private fun timeFormat(time: Long): String {
        return SimpleDateFormat("d/M/yy, h:mm:ss").format(Date(time))
    }

    private fun formatSize(size: Long): String {
        val df = DecimalFormat()
        return df.format(size) + "B"
    }

    init {
        timeCreated = timeFormat(file.lastModified())
        size = formatSize(file.length())
    }
}