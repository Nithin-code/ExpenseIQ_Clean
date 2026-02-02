package com.example.expenseiq_clean.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val fromAccount: String,
    val toAccount: String,
    val isCredited: Boolean,
    val date : String,
    val category: String,
    val dateMillis: Long
)