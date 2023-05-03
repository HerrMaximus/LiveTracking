package de.swm.websocket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import org.springframework.web.reactive.socket.client.WebSocketClient

@Configuration
class WebSocketClientConfig {
    @Bean
    fun webSocketClient(): WebSocketClient {
        return ReactorNettyWebSocketClient()
    }

    @Bean
    fun webSocketHandler(): WebSocketHandler {
        return WebSocketHandler {session ->
            session.receive()
                .map{it.payloadAsText}
                .doOnNext{ println("Received message: $it")}
                .then()
        }
    }
}