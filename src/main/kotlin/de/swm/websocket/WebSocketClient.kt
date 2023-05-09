package de.swm.websocket

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

data class Response(
    val type: String? = null,
    val title: String? = null,
    val status: String? = null,
    val detail: String? = null,
    val instance: String? = null
)

class WebSocketClient {

    fun login(username: String, password: String): Boolean {
        val client = WebClient.builder()
            .baseUrl("http://mischiefsmp.com:8080")
            .build()
        return client.post().uri { uriBuilder ->
            uriBuilder.path("/join")
            uriBuilder.queryParam("username", username)
            uriBuilder.queryParam("password", password).build()
        }
            .retrieve()
            .bodyToMono(Response::class.java)
            .map { response ->
                response.title == "OK"
            }.block() ?: false
    }

    fun sendMessage(username: String, password: String, message: String) {
    }

    fun receiveMessage() {
        //TODO: Implement it
    }
}