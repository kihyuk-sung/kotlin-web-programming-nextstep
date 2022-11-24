package com.example.calculator

class StringCalculator {
    fun add(text: String?): Int = text
        ?.ifEmpty { null }
        ?.let {
            it.toIntOrNull() ?: it.split(",", ":").map(String::toInt).sum()
        }
        ?: 0
}
