package com.example.expenseiq_clean.navigation

sealed class Screens(val route: String) {

    data object DashboardScreen : Screens("dashboard_screen")

    data object AddExpenseScreen: Screens("add_expense")

}