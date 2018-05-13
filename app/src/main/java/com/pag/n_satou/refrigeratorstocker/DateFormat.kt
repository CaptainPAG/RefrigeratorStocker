package com.pag.n_satou.refrigeratorstocker

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by N-Satou on 2018/05/13.
 */
object DateFormat {

    fun strToDate(dateStr: String): Date?{
        try {
            val pattern = stringToDate(dateStr, "yy/MM/dd")
        } catch (e: ParseException) {
            try {
                return stringToDate(dateStr, "yyyy/MM/dd")
            } catch (e: ParseException) {
                try {
                    return  stringToDate(dateStr, "yy.MM.dd")
                } catch (e: ParseException) {
                    try {
                        return stringToDate(dateStr, "yyyy.MM.dd")
                    } catch (e: ParseException) {

                    }
                }
            }
        }
        return null
    }

    private fun stringToDate(dateStr: String, format: String): Date {
        val sdf = SimpleDateFormat(format, Locale.JAPAN)
        return sdf.parse(dateStr)
    }

    fun dateToString(date: Date, format: String = "yyyy/MM/dd"): String {
        val sdf = SimpleDateFormat(format, Locale.JAPAN)
        return sdf.format(date)
    }

}