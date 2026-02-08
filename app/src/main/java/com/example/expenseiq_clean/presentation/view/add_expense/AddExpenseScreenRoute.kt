package com.example.expenseiq_clean.presentation.view.add_expense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.expenseiq_clean.presentation.model.add_expense.AddExpenseUIEvent
import com.example.expenseiq_clean.presentation.viewmodel.add_expense.AddExpenseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddExpenseScreenRoute(
    navController: NavHostController
){
    val viewModel = koinViewModel<AddExpenseViewModel>()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event){
                is AddExpenseUIEvent.OnSaveButtonClick -> {
                    navController.navigateUp()
                }
                is AddExpenseUIEvent.OnBackClicked -> {
                    navController.navigateUp()
                }
            }
        }
    }

    AddExpenseScreen(
        viewModel = viewModel
    )
}