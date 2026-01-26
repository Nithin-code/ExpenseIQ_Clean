package com.example.expenseiq_clean.data.repository

import com.example.expenseiq_clean.data.local.ExpenseDao
import com.example.expenseiq_clean.data.mappers.toExpense
import com.example.expenseiq_clean.data.mappers.toExpenseEntity
import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ExpenseRepositoryImpl(
    val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toExpenseEntity())
    }

    override fun getTotalExpenses() : Flow<List<Expense>>{
        return expenseDao
            .getTotalExpenses()
            .map { expenseEntities ->
                expenseEntities.map { entity -> entity.toExpense() }
            }
    }


}