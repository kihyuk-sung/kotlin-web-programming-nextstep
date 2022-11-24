package com.example.calculator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class CalculatorTest: FunSpec({
    val cal = Calculator()
    test("6 + 3 = 9") {
        cal.add(6, 3) shouldBe 9
    }

    test("6 - 3 = 3") {
        cal.subtract(6, 3) shouldBe 3
    }
})
