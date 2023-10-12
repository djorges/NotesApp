package com.example.notes.data.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    fun getCurrentTime(): Date {
        return Calendar.getInstance().time
    }

    fun getStringFromDate(date:Date):String{
        return SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US).format(date)
    }
}