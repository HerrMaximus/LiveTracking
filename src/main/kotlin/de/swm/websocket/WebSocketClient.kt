package de.swm.websocket

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

class WebSocketClient {

    fun login(username: String, password: String): Boolean {
        val client = WebClient.create("http://mischiefsmp.com:8080")
        return client.post()
            .uri("/join") {
                it.queryParam("username", username)
                    .queryParam("password", password)
                    .build()
            }
            .retrieve()
            .bodyToMono<String>()
            .map { it.contains("\"title\":\"OK\"") }
            .block() ?: false
    }

    fun sendMessage(username: String, password: String, message: String) {
    }

    fun receiveMessage() {
        //TODO: Implement it
    }
}