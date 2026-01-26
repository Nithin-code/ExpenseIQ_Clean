package com.example.expenseiq_clean.domain.utils

import android.os.Build
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object CommonUtils {

    fun String.toReadableDate(): String {

        if (Build.VERSION.SDK_INT < 26) {
            val inputFormats = listOf(
                SimpleDateFormat("dd-MM-yy", Locale.ENGLISH),
                SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            )

            val outputFormat = SimpleDateFormat("dd MMM''yy", Locale.ENGLISH)

            for (format in inputFormats) {
                try {
                    val date = format.parse(this)
                    if (date != null) {
                        return outputFormat.format(date)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return this
        } else {
            val inputFormats = listOf(
                DateTimeFormatter.ofPattern("dd-MM-yy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )

            val outputFormatter = DateTimeFormatter.ofPattern("dd MMM''yy")

            for (formatter in inputFormats) {
                try {
                    val date = LocalDate.parse(this, formatter)
                    return date.format(outputFormatter)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return this
        }

    }

    fun Double.toReadableAmount() : String{
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("en","In"))
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(this) ?: ""
    }

}