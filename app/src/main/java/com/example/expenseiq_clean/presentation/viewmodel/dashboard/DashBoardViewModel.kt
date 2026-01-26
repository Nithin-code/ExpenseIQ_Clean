package com.example.expenseiq_clean.presentation.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.repository.ExpenseRepository
import com.example.expenseiq_clean.domain.utils.CommonUtils
import com.example.expenseiq_clean.domain.utils.CommonUtils.toReadableAmount
import com.example.expenseiq_clean.domain.utils.CommonUtils.toReadableDate
import com.example.expenseiq_clean.presentation.model.dashbaord.DashBoardScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashBoardViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel(){

    private val _dashboardScreenState = MutableStateFlow(
        value = DashBoardScreenUIState(totalAmount = "",topExpensesList = emptyList())
    )

    val dashBoardScreenState = _dashboardScreenState
        .onStart {
            getTotalExpenses()
        }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashBoardScreenUIState(topExpensesList = emptyList())
    )


    private fun getTotalExpenses() {
        viewModelScope.launch {
            expenseRepository.getTotalExpenses().collect { value ->
                val totalAmount = value.sumOf { it.amount }
                _dashboardScreenState.update { state->
                    state.copy(
                        currentDate = "",
                        totalAmount = totalAmount.toReadableAmount(),
                        topExpensesList = value.map { it.copy(date = it.date.toReadableDate()) }
                    )
                }
            }
        }
    }
}