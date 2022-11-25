package org.example.webserver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class RequestHandler(
    private val connection: Socket,
): Thread() {
    companion object {
        val log: Logger = LoggerFactory.getLogger(RequestHandler::class.java)
    }

    override fun run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.inetAddress, connection.port)

        try {
            connection.getInputStream().use { inputStream ->
                connection.getOutputStream().use { outputStream ->
                    val dos = DataOutputStream(outputStream)
                    val body = "Hello World".toByteArray()
                    dos.response200Header(body.size)
                    dos.responseBody(body)
                }
            }
        } catch (e: IOException) {
            log.error(e.message)
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
