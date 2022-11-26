package org.example.webserver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.*
import java.net.Socket
import java.nio.file.Files

class RequestHandler(
    private val connection: Socket,
): Thread() {
    companion object {
        val log: Logger = LoggerFactory.getLogger(RequestHandler::class.java)
    }

    override fun run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.inetAddress, connection.port)

        try {
            BufferedReader(InputStreamReader(connection.getInputStream())).use { reader ->
                val firstLine: String? = reader.readLine()
                if (firstLine == null) {
                    connection.responseHello()
                    return@use
                }
                log.info(firstLine)
                while (true) {
                    val line: String = reader.readLine() ?: break
                    if (line == "") break
                    log.info(line)
                }
                val (method, path) = parseFirstLine(firstLine)
                if (method == "GET") {
                    val body = Files.readAllBytes( File("./webapp${path}").toPath())
                    connection.responseBody(body)
                    return@use
                }
                connection.responseHello()
            }
        } catch (e: IOException) {
            log.error(e.message)
        } catch (e: RuntimeException) {
            log.error(e.message)
        }
    }

    private fun parseFirstLine(firstLine: String): Pair<String, String> {
        val split = firstLine.split(" ")
        val method = split.getOrNull(0) ?: throw RuntimeException("Method error")
        val path = split.getOrNull(1) ?: throw RuntimeException("path error")
        return method to path
    }

    private fun Socket.responseHello() {
        getOutputStream().use { outputStream ->
            val dos = DataOutputStream(outputStream)
            val body = "Hello World".toByteArray()
            dos.response200Header(body.size)
            dos.responseBody(body)
        }
    }

    private fun Socket.responseBody(body: ByteArray) {
        getOutputStream().use { outputStream ->
            val dos = DataOutputStream(outputStream)
            dos.response200Header(body.size)
            dos.responseBody(body)
        }
    }
    private fun DataOutputStream.response200Header(lengthOfBodyContent: Int) {
        try {
            writeBytes("HTTP/1.1 200 OK\r\n")
            writeBytes("Content-Type: text/html;charset=utf-8\r\n")
            writeBytes("Content-Length: $lengthOfBodyContent\r\n")
            writeBytes("\r\n")
        } catch (e: IOException) {
            log.error(e.message)
        }
    }

    private fun DataOutputStream.responseBody(body: ByteArray) {
        try {
            write(body, 0, body.size)
            writeBytes("\r\n")
            flush()
        } catch (e: IOException) {
            log.error(e.message)
        }
    }
}
