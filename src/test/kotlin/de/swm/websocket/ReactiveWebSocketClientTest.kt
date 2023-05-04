package de.swm.websocket

import org.junit.jupiter.api.Test

class ReactiveWebSocketClientTest {

    @Test
    fun main() {
        val client = ReactiveWebSocketClient()
        client.start()
    }
}