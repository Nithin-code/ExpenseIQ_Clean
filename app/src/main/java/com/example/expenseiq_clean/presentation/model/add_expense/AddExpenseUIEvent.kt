package com.example.expenseiq_clean.presentation.model.add_expense

import com.example.expenseiq_clean.domain.model.NavigationEvent

sealed interface AddExpenseUIEvent {
    data object OnSaveButtonClick : AddExpenseUIEvent
    data object OnBackClicked : AddExpenseUIEvent
}