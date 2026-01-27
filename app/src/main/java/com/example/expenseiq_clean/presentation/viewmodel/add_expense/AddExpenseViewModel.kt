package com.example.expenseiq_clean.presentation.viewmodel.add_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseiq_clean.domain.model.ChipType
import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.model.ExpenseCategory
import com.example.expenseiq_clean.domain.model.SpentViaCategory
import com.example.expenseiq_clean.domain.repository.ExpenseRepository
import com.example.expenseiq_clean.presentation.model.add_expense.AddExpenseScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    val repository: ExpenseRepository
): ViewModel() {

    private val _addExpenseScreenUIState = MutableStateFlow(
        value = AddExpenseScreenState()
    )

    val addExpenseScreenUIState = _addExpenseScreenUIState

    fun onAmountChange(amount : String) {
        viewModelScope.launch {
            _addExpenseScreenUIState.update { state->
                state.copy(
                    enteredAmount = amount
                )
            }
        }
    }

    fun onDatePickerSelected(selectedDate : String?){
        viewModelScope.launch {
            _addExpenseScreenUIState.update { state ->
                state.copy(
                    shouldShowDatePickerDialog = !state.shouldShowDatePickerDialog,
                    selectedDate = selectedDate ?: ""
                )
            }
        }
    }

    fun onChipSelected(index: Int , chipType: ChipType){
        viewModelScope.launch {
            when(chipType){
                is ChipType.ExpenseCategory -> {
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            selectedExpenseTypeIndex = index
                        )
                    }
                }
                is ChipType.DebitedCategory -> {
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            selectedDebitTypeIndex = index
                        )
                    }
                }
                is ChipType.SpentViaCategory -> {
                    _addExpenseScreenUIState.update { state ->
                        state.copy(
                            selectedSpentViaIndex = index
                        )
                    }
                }
            }
        }
    }

    fun onSaveButtonClicked(){
        viewModelScope.launch {
            val state = _addExpenseScreenUIState.value
            repository.addExpense(
                Expense(
                    amount = if (state.enteredAmount.toDoubleOrNull() == null) 0.0 else state.enteredAmount.toDouble(),
                    fromAccount = SpentViaCategory.entries.toList()[state.selectedSpentViaIndex].type,
                    toAccount = ExpenseCategory.entries.toList()[state.selectedExpenseTypeIndex].type,
                    isCredited = state.selectedDebitTypeIndex != 0,
                    date = state.selectedDate,
                    category = ""
                )
            )
        }
    }

}