package com.example.expenseiq_clean.domain.utils

sealed interface Result<out T> {

    data object Loading : Result<Nothing>

    data class Success<T>(val data: T) : Result<T>

    data class Error(val message: String, val throwable: Throwable?=null) : Result<Nothing>

}