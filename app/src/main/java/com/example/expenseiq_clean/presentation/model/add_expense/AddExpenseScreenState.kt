package com.example.expenseiq_clean.presentation.model.add_expense

data class AddExpenseScreenState(
    val enteredAmount : String = "",
    val selectedExpenseTypeIndex : Int = 0,
    val selectedDebitTypeIndex : Int = 0,
    val selectedSpentViaIndex : Int = 0,
    val selectedDate: String = ""
)
