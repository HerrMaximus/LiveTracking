package de.swm.websocket

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConversionException
import org.springframework.messaging.simp.stomp.*
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

class Websocket(private val username: String, private val password: String) {

    data class MessageResponse(var username: String? = null, var message: String? = null, var time: Long? = null)


    fun login(): Boolean {
        val client = WebClient.create("http://mischiefsmp.com:8080")
        return client.post()
            .uri("/join") {
                it.queryParam("username", username) //Needed for URL Params
                    .queryParam("password", password) //Needed for URL Params
                    .build()
            }
            .retrieve()
            .bodyToMono<String>()
            .map { it.contains("\"title\":\"OK\"") } //Return true if title == OK
            .block() ?: false //Return false if something went wrong
    }

    data class Message(val username: String, val password: String, val message: String)

    private val session: StompSession //Connection to WebSocket until I say goodby
    private val listeners = ArrayList<(MessageResponse) -> (Unit)>()

    fun addListener(listener: ((Websocket.MessageResponse) -> (Unit))) {
        listeners.add(listener)
    }

    init {
        login()
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.initialize()

        val client = WebSocketStompClient(StandardWebSocketClient())
        client.messageConverter = MappingJackson2MessageConverter()
        client.taskScheduler = scheduler

        val headers = StompHeaders()
        headers.add("username", username)
        headers.add("password", password)

        session = client.connectAsync("ws://89.58.39.209:8080/chat", null, headers, object : StompSessionHandler {
            override fun getPayloadType(headers: StompHeaders) = String::class.java
            override fun handleFrame(headers: StompHeaders, payload: Any?) {}
            override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
                println("Connected!")
                Thread.sleep(100)
                session.subscribe("/topic/messages", object : StompFrameHandler {
                    override fun getPayloadType(headers: StompHeaders) = String::class.java
                    override fun handleFrame(headers: StompHeaders, payload: Any?) = println("payload...")
                })
                session.setAutoReceipt(true)
            }

            override fun handleException(
                session: StompSession,
                command: StompCommand?,
                headers: StompHeaders,
                payload: ByteArray,
                exception: Throwable
            ) {
                if (exception is MessageConversionException) {
                    listeners.forEach {
                        it.invoke(jacksonObjectMapper().readValue(String(payload), MessageResponse::class.java))
                    }
                }

            }

            override fun handleTransportError(session: StompSession, exception: Throwable) =
                println("handleTransportError: ${exception.message}")
        }).get()
    }

    fun send(message: String) {
        session.send("/app/chat", Message(username, password, message)) //To JSON
    }
}