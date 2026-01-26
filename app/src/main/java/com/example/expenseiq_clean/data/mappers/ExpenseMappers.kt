package com.example.expenseiq_clean.data.mappers

import com.example.expenseiq_clean.data.local.ExpenseEntity
import com.example.expenseiq_clean.domain.model.Expense

fun Expense.toExpenseEntity() = ExpenseEntity(
    amount = this.amount,
    fromAccount = this.fromAccount,
    toAccount = this.toAccount,
    isCredited = this.isCredited,
    date = this.date,
    category = this.category
)

fun ExpenseEntity.toExpense() = Expense(
    amount = this.amount,
    fromAccount = this.fromAccount,
    toAccount = this.toAccount,
    isCredited = this.isCredited,
    date = this.date,
    category = this.category
)
