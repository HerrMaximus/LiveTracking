package de.swm.livetracking

import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder

object ApplicationSettings {
    var url = ""
    var topic = ""
    var id = ""
}

data class LOC(var lat: Double, var lon: Double)

fun main(args: Array<String>) {
    ApplicationSettings.url = args[0]
    ApplicationSettings.topic = args[1]
    ApplicationSettings.id = args[2]

    val context = SpringApplicationBuilder(VehicleApplication::class.java).web(WebApplicationType.NONE).run(*args)
    val gateway = context.getBean(VehicleGateway::class.java)
    val pos = LOC((0..5).random().toDouble(), (0..5).random().toDouble())
    Thread {
        while(true) {
            Thread.sleep(1000)
            gateway.sendToMqtt("$pos")
            pos.lat += listOf(0.0001, 0.0005, 0.00025, 0.0008).random()
            pos.lon += listOf(0.0001, 0.0005, 0.00025, 0.0008).random()
        }
    }.start()
}
