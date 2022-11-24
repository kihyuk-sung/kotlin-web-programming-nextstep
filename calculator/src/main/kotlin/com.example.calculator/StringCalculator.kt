package com.example.calculator

class StringCalculator {
    companion object {
        const val COMMA = ","
        const val COLON = ":"
        val CUSTOM_DELIMITERS_PATTERN = "//(.)\n(.*)".toRegex()
    }

    fun add(text: String?): Int {
        if (text.isNullOrEmpty()) return 0

        val numbers = (CUSTOM_DELIMITERS_PATTERN.find(text)
            ?.let {
                val (delimiter, str) = it.destructured
                str.split(delimiter)
            }
            ?: text.split(COMMA, COLON)).map(String::toInt)

        if (numbers.any { it < 0 }) {
            throw RuntimeException("음수 존재")
        }

        return numbers.sum()
    }
}
