package com.example.expenseiq_clean.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White

data class ExpenseColors(
    val primary: Color,
    val background: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val incomeGreen: Color, // Custom slot for Income
    val expenseRed: Color,  // Custom slot for Expense
    val isDark: Boolean
)

val LocalExpenseColors = staticCompositionLocalOf {
    ExpenseColors(
        primary = Color.Unspecified,
        background = Color.Unspecified,
        cardBackground = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        incomeGreen = Color.Unspecified,
        expenseRed = Color.Unspecified,
        isDark = false
    )
}

val LightPalette = ExpenseColors(
    primary = Color(0xFF006C4C), // Deep Green
    background = LightGray,
    cardBackground = White,
    textPrimary = Black,
    textSecondary = Color.Gray,
    incomeGreen = Green500,
    expenseRed = Red500,
    isDark = false
)

val DarkPalette = ExpenseColors(
    primary = Color(0xFF4DB6AC), // Lighter Teal for dark mode
    background = DarkGray,
    cardBackground = Charcoal,
    textPrimary = White,
    textSecondary = LightGray,
    incomeGreen = Color(0xFF81C784), // Lighter green for visibility
    expenseRed = Color(0xFFE57373),  // Lighter red for visibility
    isDark = true
)