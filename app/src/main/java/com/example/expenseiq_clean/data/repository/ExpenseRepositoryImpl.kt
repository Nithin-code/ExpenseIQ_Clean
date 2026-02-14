package com.example.expenseiq_clean.data.repository

import com.example.expenseiq_clean.data.local.ExpenseDao
import com.example.expenseiq_clean.data.mappers.toExpense
import com.example.expenseiq_clean.data.mappers.toExpenseEntity
import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.repository.ExpenseRepository
import com.example.expenseiq_clean.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ExpenseRepositoryImpl(
    val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun addExpense(expense: Expense) : Result<Boolean> {
        return try {
            expenseDao.insertExpense(expense.toExpenseEntity())
            Result.Success(true)
        }catch (t : Exception){
            Result.Error(message = t.message.toString())
        }
    }

    override fun getTotalExpenses() : Result<Flow<List<Expense>>>{
        return try {
            Result.Success(
                data = expenseDao
                    .getTotalExpenses()
                    .map { expenseEntities ->
                        expenseEntities.map { entity -> entity.toExpense() }
                    }
            )
        }catch (t: Exception){
            Result.Error(message = t.message.toString())
        }
    }

}