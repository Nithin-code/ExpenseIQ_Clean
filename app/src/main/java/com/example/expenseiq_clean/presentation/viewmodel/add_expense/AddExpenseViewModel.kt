package com.example.expenseiq_clean.presentation.viewmodel.add_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseiq_clean.domain.model.ChipType
import com.example.expenseiq_clean.domain.model.DebitedCategory
import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.model.ExpenseCategory
import com.example.expenseiq_clean.domain.model.SpentViaCategory
import com.example.expenseiq_clean.domain.repository.ExpenseRepository
import com.example.expenseiq_clean.presentation.model.add_expense.AddExpenseScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AddExpenseViewModel(
    val repository: ExpenseRepository
) : ViewModel() {

    private val _addExpenseScreenUIState = MutableStateFlow(
        value = AddExpenseScreenState()
    )

    val addExpenseScreenUIState = _addExpenseScreenUIState

    fun onAmountChange(amount: String) {
        viewModelScope.launch {
            _addExpenseScreenUIState.update { state ->
                state.copy(
                    enteredAmount = amount
                )
            }
        }
    }

    fun onDatePickerSelected(dateMillis: Long?) {
        if (dateMillis == null) {
            viewModelScope.launch {
                _addExpenseScreenUIState.update { state ->
                    state.copy(
                        shouldShowDatePickerDialog = !state.shouldShowDatePickerDialog,
                    )
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    val dateArr = Date(dateMillis).toString().split(" ")
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            shouldShowDatePickerDialog = !state.shouldShowDatePickerDialog,
                            selectedDate = formatDate(dateMillis),
                            selectedDateMillis = dateMillis,
                            selectedDayDate = dateArr[0]+" "+dateArr[2],
                            selectedMonth = dateArr[1],
                            selectedYear = dateArr[dateArr.size-1]
                        )
                    }
                }catch (t : Exception){
                    t.printStackTrace()
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            shouldShowDatePickerDialog = !state.shouldShowDatePickerDialog,
                        )
                    }
                }
            }
        }
    }

    private fun formatDate(millis: Long): String {
        val format = SimpleDateFormat(
            "EEE, dd MMM''yy",
            java.util.Locale.ENGLISH
        )
        return format.format(millis)
    }

    fun onChipSelected(name: String, chipType: ChipType) {
        viewModelScope.launch {
            when (chipType) {
                is ChipType.ExpenseCategory -> {
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            selectedExpenseType = name
                        )
                    }
                }

                is ChipType.DebitedCategory -> {
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            selectedDebitType = name
                        )
                    }
                }

                is ChipType.SpentViaCategory -> {
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            selectedSpentVia = name
                        )
                    }
                }
            }
        }
    }

    fun onSaveButtonClicked(
        navigateBack: () -> Unit
    ) {
        viewModelScope.launch {
            val state = _addExpenseScreenUIState.value
            try {
                if (isValidExpanse(state)) {
                    repository.addExpense(
                        Expense(
                            amount = if (state.enteredAmount.toDoubleOrNull() == null) 0.0 else state.enteredAmount.toDouble(),
                            fromAccount = state.selectedSpentVia,
                            toAccount = state.selectedExpenseType,
                            isCredited = (state.selectedDebitType == DebitedCategory.CREDITED.type),
                            date = state.selectedDate,
                            category = "",
                            dateMillis = state.selectedDateMillis
                        )
                    )
                    navigateBack.invoke()
                } else {
                    displayAutoHideErrorBanner()
                }
            }catch (t : Exception){
                t.printStackTrace()
                displayAutoHideErrorBanner()
            }
        }
    }

    private fun displayAutoHideErrorBanner() {
        viewModelScope.launch {
            _addExpenseScreenUIState.update { screenState ->
                screenState.copy(
                    showBanner = true
                )
            }
            delay(2000)
            _addExpenseScreenUIState.update { screenState ->
                screenState.copy(
                    showBanner = false
                )
            }
        }
    }

    fun isValidExpanse(expense: AddExpenseScreenState): Boolean {
        return expense.selectedDateMillis != 0L && expense.enteredAmount.toDoubleOrNull() != null
    }

}