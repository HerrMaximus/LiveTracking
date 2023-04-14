package de.swm.livetracking

import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder

object ApplicationSettings {
    var url = ""
    var topic = ""
}

fun main(args: Array<String>) {
    ApplicationSettings.url = args[0]
    ApplicationSettings.topic = args[1]
    SpringApplicationBuilder(FrontendApplication::class.java).web(WebApplicationType.NONE).run(*args)
}
