package de.swm.websocket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import org.springframework.web.reactive.socket.client.WebSocketClient
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import org.springframework.web.socket.config.annotation.EnableWebSocket
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration
import java.util.*

@Configuration
@EnableWebSocket
class WebSocketConfig {

    @Autowired
    private lateinit var webSocketHandler: WebSocketHandler
    @Bean
    fun webSocketHandlerMapping(): HandlerMapping {
        val mapping = SimpleUrlHandlerMapping()
        mapping.urlMap = Collections.singletonMap("/echo", webSocketHandler)
        mapping.order = Ordered.HIGHEST_PRECEDENCE
        return mapping
    }

    @Bean
    fun handlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }
}



@Component
class EchoHandler : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        val output: Flux<WebSocketMessage> = session.receive()
            .map { msg -> session.textMessage("ECHO -> ${msg.payloadAsText}") }
        return session.send(output)
    }
}



class ReactiveWebSocketClient {
    fun start() {
        val client: WebSocketClient = ReactorNettyWebSocketClient()
        client.execute(
            URI.create("wss://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV")
        ) { session ->
            session.send(Flux.just(session.textMessage("Hello")))
                .thenMany(session.receive().take(1).log())
                .then()
        }.block(Duration.ofMillis(5000))
    }
}