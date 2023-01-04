package org.example.webserver

import org.slf4j.LoggerFactory
import java.net.ServerSocket
import java.util.concurrent.ConcurrentHashMap

const val DEFAULT_PORT = 8080

fun main(args: Array<String>) {
    val port = args.getOrNull(0)?.toInt() ?: DEFAULT_PORT

    val logger = LoggerFactory.getLogger("WebServer")
    val users: MutableMap<String, User> = ConcurrentHashMap<String, User>()

    logger.info("Web Application Server started {} port.", port)

    ServerSocket(port).use {
        while (true) {
            val connection = it.accept()
            RequestHandler(connection, users).start()
        }
    }
}
