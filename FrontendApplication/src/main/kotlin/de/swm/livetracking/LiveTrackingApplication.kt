package de.swm.livetracking

import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        println("Please start application with either -vehicle or -frontend!")
        exitProcess(0)
    }

    when(args[0]) {
        "-vehicle" -> {
            val context = SpringApplicationBuilder(VehicleApplication::class.java).web(WebApplicationType.NONE).run(*args)
            val gateway = context.getBean(VehicleGateway::class.java)
            Thread {
                while(true) {
                    Thread.sleep(1000)
                    gateway.sendToMqtt("Status: Bus lol!")
                }
            }.start()
        }
        "-frontend" -> {
            val context = SpringApplicationBuilder(FrontendApplication::class.java).web(WebApplicationType.NONE).run(*args)
        }
    }



}
