package com.example.expenseiq_clean.di

import androidx.room.Room
import com.example.expenseiq_clean.data.local.ExpenseDataBase
import com.example.expenseiq_clean.data.repository.ExpenseRepositoryImpl
import com.example.expenseiq_clean.domain.repository.ExpenseRepository
import com.example.expenseiq_clean.domain.usecases.ParseSmsUseCase
import com.example.expenseiq_clean.presentation.viewmodel.add_expense.AddExpenseViewModel
import com.example.expenseiq_clean.presentation.viewmodel.dashboard.DashBoardViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(get(), ExpenseDataBase::class.java,"my_expenses_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<ExpenseDataBase>().expenseDao() }

    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }

    single<ParseSmsUseCase> { ParseSmsUseCase(get()) }

    viewModelOf(::DashBoardViewModel)

    viewModelOf(::AddExpenseViewModel)
}