package de.swm.livetracking

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.messaging.MessageHandler

@SpringBootApplication
@IntegrationComponentScan
class VehicleApplication {
    @Bean
    fun mqttClientFactory() = DefaultMqttPahoClientFactory().apply {
        connectionOptions = MqttConnectOptions().apply {
            serverURIs = arrayOf(ApplicationSettings.url)
        }
    }
    @Bean
    fun mqttOutboundChannel() = DirectChannel()


    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    fun mqttOutbound(): MessageHandler {
        return MqttPahoMessageHandler("frontend-changeme", mqttClientFactory()).apply {
            setAsync(true)
            setDefaultTopic(ApplicationSettings.topic + "/${ApplicationSettings.id}")
        }
    }
}

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
interface VehicleGateway {
    fun sendToMqtt(data: String)
}