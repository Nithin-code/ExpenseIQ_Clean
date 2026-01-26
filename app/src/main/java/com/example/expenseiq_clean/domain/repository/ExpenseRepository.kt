package com.example.expenseiq_clean.domain.repository

import com.example.expenseiq_clean.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun addExpense(expense: Expense)

    fun getTotalExpenses() : Flow<List<Expense>>

}