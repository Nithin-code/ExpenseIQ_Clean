package com.example.expenseiq_clean.presentation.model.dashbaord

import com.example.expenseiq_clean.domain.model.Expense

data class DashBoardScreenUIState(
    val totalAmount : String = "",
    val topExpensesList : List<Expense>,
    val currentDate : String = ""
)
