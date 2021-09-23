package com.matdev.firestoreflowexample

sealed class ResultPrueba<out R> {
    data class Success<T>(val data: T): ResultPrueba<T>()
    data class Error(val error: Throwable): ResultPrueba<Nothing>()
}