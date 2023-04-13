package de.swm.livetracking

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.messaging.MessageHandler

object ApplicationSettings {
    val clientName = "LiveTrackingApplication"
    val url = "tcp://broker.mqttdashboard.com"
    val topic = "sven"
}

@SpringBootApplication
@IntegrationComponentScan
class LiveTrackingApplication {
    @Bean
    fun mqttClientFactory() = DefaultMqttPahoClientFactory().apply {
        connectionOptions = MqttConnectOptions().apply {
            serverURIs = arrayOf(ApplicationSettings.url)
        }
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    fun mqttOutbound(): MessageHandler {
        return MqttPahoMessageHandler(ApplicationSettings.clientName, mqttClientFactory()).apply {
            setAsync(true)
            setDefaultTopic(ApplicationSettings.topic)
        }
    }

    @Bean
    fun mqttOutboundChannel() = DirectChannel()
}

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
interface MyGateway {
    fun sendToMqtt(data: String)
}

fun main(args: Array<String>) {
    val context = SpringApplicationBuilder(LiveTrackingApplication::class.java).web(WebApplicationType.NONE).run(*args)
    val gateway = context.getBean(MyGateway::class.java)
    gateway.sendToMqtt("Hello max! 8)")
    gateway.sendToMqtt("ich komm von spring boot lol! haha! ${System.currentTimeMillis()}")
}
