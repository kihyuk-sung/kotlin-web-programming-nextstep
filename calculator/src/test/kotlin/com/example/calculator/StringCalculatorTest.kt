package com.example.calculator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.lang.RuntimeException

internal class StringCalculatorTest: FunSpec({
    val cal = StringCalculator()

    test("빈 문자열 또는 null을 입력할 경우 0을 반환해야 한다.") {
        cal.add("") shouldBe 0
        cal.add(null) shouldBe 0
    }

    test("숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환한다.") {
        cal.add("1") shouldBe 1
        cal.add("100") shouldBe 100
    }

    test("구분자로 , : 을 사용할 수 있다.") {
        cal.add("1,2:3") shouldBe 6
        cal.add("4:6,4") shouldBe 14
    }

    test("// 와 \\n 문자 사이에 커스텀 구분자를 지정할 수 있다.") {
        cal.add("//;\n1;2;3") shouldBe 6
    }

    test("음수가 들어오면 RuntimeException이 발생한다") {
        shouldThrow<RuntimeException> {
            cal.add("1:-1")
        }

        shouldThrow<RuntimeException> {
            cal.add("-100")
        }
    }
})
