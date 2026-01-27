package com.example.expenseiq_clean.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenseiq_clean.presentation.view.add_expense.AddExpenseScreen
import com.example.expenseiq_clean.presentation.view.dashboard.DashboardScreen

@Composable
fun NavigationGraph(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.DashboardScreen.route
    ){
        composable(
            route = Screens.DashboardScreen.route,
        ){
            DashboardScreen(
                onAddButtonClicked = {
                    navController.navigate(Screens.AddExpenseScreen.route)
                }
            )
        }


        composable(
            route = Screens.AddExpenseScreen.route,
        ){
            AddExpenseScreen(
                onBackArrowClicked = {
                    navController.navigateUp()
                }
            )
        }
    }

}