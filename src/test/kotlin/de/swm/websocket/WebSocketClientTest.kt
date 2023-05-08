package de.swm.websocket

import org.junit.jupiter.api.Test

class WebSocketClientTest {

    @Test
    fun main() {
        val client = WebSocketClient()
        client.start()
    }
}