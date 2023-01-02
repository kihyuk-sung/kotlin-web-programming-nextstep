package org.example.webserver.utils

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class PathUtilsKtTest: FunSpec({
    test("parsePath") {
        val path = "/user/create?userId=javajigi&password=password&name=JaeSung&email=javajigi%40slipp.net"
        parseQueryString(path) shouldBe mapOf(
            "userId" to listOf("javajigi"),
            "password" to listOf("password"),
            "name" to listOf("JaeSung"),
            "email" to listOf("javajigi%40slipp.net")
        )
    }
})
