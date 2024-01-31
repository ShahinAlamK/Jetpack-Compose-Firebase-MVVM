package com.example.task.data

sealed class AppResponse<out T> {
    data class Success<out T>(val data: T): AppResponse<T>()
    data class Failure<out T>(val msg: Throwable): AppResponse<T>()
    data object Loading: AppResponse<Nothing>()
}