package com.example.expenseiq_clean.domain.usecases

import com.example.expenseiq_clean.domain.model.Expense
import com.example.expenseiq_clean.domain.model.Sender
import com.example.expenseiq_clean.domain.repository.ExpenseRepository

class ParseSmsUseCase(
    private val repository: ExpenseRepository,
) {

    private var expense = Expense(
        amount = 0.0,
        fromAccount = "",
        toAccount = "",
        isCredited = false,
        date = "",
        category = "",
        dateMillis = 0L
    )

    suspend fun execute(sender: String, body: String) {
        if (!isBankTransaction(sender)) return
        parseExpense(sender, body)
        repository.addExpense(expense)
    }

    private suspend fun parseExpense(sender: String, body: String) {
        extractAmount(messageBody = body)
        extractDate(messageBody = body)
        extractFromAccount(messageBody = body)
        isCredited(messageBody = body)
    }

    private suspend fun extractAmount(messageBody: String) {
        val amountRegex = Regex("(?:INR|Rs\\.?|₹)\\s*([0-9]+(?:,[0-9]{3})*(?:\\.[0-9]{1,2})?)")
        val amount = amountRegex.find(messageBody)?.groupValues?.get(1)
            ?.replace(",", "")
            ?.toDoubleOrNull() ?: 0.0
        expense = expense.copy(
            amount = amount
        )
    }

    private suspend fun extractDate(messageBody: String) {
        val dateRegex = Regex("(\\d{2}[/-]\\d{2}[/-]\\d{2,4})(?:,\\s*(\\d{2}:\\d{2}:\\d{2}))?")
        val date = dateRegex.find(messageBody)?.groupValues?.get(1)
            ?.replace(",", "") ?: ""
        expense = expense.copy(
            date = date
        )
    }

    private suspend fun extractFromAccount(messageBody: String) {
        val fromAccountRegex = Regex("(?:A\\/c(?:ount)?\\s*(?:no\\.?)?\\s*)?([Xx*]+\\d{2,6})")
        val fromAccount = fromAccountRegex.find(messageBody)?.groupValues?.get(1)
            ?.replace(",", "") ?: ""
        expense = expense.copy(
            fromAccount = fromAccount
        )
    }

    private suspend fun isCredited(messageBody: String) {
        expense = if (messageBody.contains("sent to", ignoreCase = true)
            || messageBody.contains("debited", ignoreCase = true)
        ) {
            expense.copy(
                isCredited = false
            )
        } else {
            expense.copy(
                isCredited = true
            )
        }
    }

    private fun isBankTransaction(sender: String?): Boolean {
        if (sender == null) {
            return false
        }
        return sender.contains(Sender.BANK.value, ignoreCase = true) ||
                sender.contains(Sender.PHONE_PE.value, ignoreCase = true) ||
                sender.contains(Sender.GPAY.value, ignoreCase = true) ||
                sender.contains(Sender.PAYTM.value, ignoreCase = true) ||
                sender.contains(Sender.AXIS.value, ignoreCase = true) ||
                sender.contains(Sender.HDFC.value, ignoreCase = true)

    }

}