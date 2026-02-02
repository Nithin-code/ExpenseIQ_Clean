package com.example.expenseiq_clean.domain.model

data class Expense(
    val amount: Double,
    val fromAccount: String,
    val toAccount: String,
    val isCredited: Boolean,
    val date : String,
    val category: String,
    val dateMillis: Long
)
