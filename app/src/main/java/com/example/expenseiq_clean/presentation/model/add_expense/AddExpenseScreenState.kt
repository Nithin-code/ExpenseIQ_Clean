package com.example.expenseiq_clean.presentation.model.add_expense

import android.accessibilityservice.GestureDescription

data class AddExpenseScreenState(
    val enteredAmount : String = "",
    //to Account
    val selectedExpenseType : String = "",
    val selectedDebitType : String = "",
    //from Account
    val selectedSpentVia : String = "",
    val selectedDate: String = "",
    val selectedDateMillis: Long=0L,
    val shouldShowDatePickerDialog: Boolean = false,
    val showBanner: Boolean = false,
    val showSuccessBanner: Boolean = false,
    val bannerText: String = "",
    val selectedDayDate: String = "Day",
    val selectedMonth: String = "Mon",
    val selectedYear: String = "year",
    val otherAccountSpentToDescription : String = "",
    val otherAccountSpentFromDescription : String = "",
)