package ru.netology.nmedia.error

sealed class AppError(message: String) : Exception(message)

class ApiError(val code: Int, message: String) : AppError(message)

object NetworkError : AppError("network_error")

object UnknownError : AppError("unknown_error")