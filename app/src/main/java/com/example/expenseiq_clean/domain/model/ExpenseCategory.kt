package com.example.expenseiq_clean.domain.model

sealed interface ChipType{
    data object ExpenseCategory : ChipType

    data object DebitedCategory : ChipType

    data object SpentViaCategory : ChipType
}

enum class ExpenseCategory(val type: String) {
    BILL_PAYMENT("Bill Payment"),
    FOOD("Food"),
    FUEL("Fuel"),
    RENT("Rent"),
    HOSPITAL("Hospital"),
    OTHERS("Others"),
}

enum class DebitedCategory(val type: String) {
    DEBITED("Debited"),
    CREDITED("Credited"),
}

enum class SpentViaCategory(val type: String)  {
    UPI("UPI"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    BANK("BANK"),
    OTHERS("Others"),
}