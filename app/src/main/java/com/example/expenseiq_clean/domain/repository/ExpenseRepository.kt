package com.example.expenseiq_clean.domain.repository

import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun addExpense(expense: Expense) : Result<Boolean>

    fun getTotalExpenses() : Result<Flow<List<Expense>>>

}