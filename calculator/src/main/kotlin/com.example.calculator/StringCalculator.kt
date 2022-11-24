package com.example.calculator

class StringCalculator {
    fun add(text: String?): Int = text
        ?.ifEmpty { null }
        ?.let {
            it.toIntOrNull() ?: 1
        }
        ?: 0
}
