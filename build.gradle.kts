val rootBuildDir = File(buildDir, "libs")

tasks.register("clean") {
    group = "build"
    subprojects.forEach {
        dependsOn(it.tasks.getByName("clean"))
    }
    doLast {
        rootProject.buildDir.deleteRecursively()
    }
}

tasks.register("build") {
    group = "build"
    subprojects.forEach {
        dependsOn(it.tasks.getByName("build"))
    }
    doLast {
        subprojects.forEach {
            println(it.buildDir)
            File(it.buildDir, "libs").copyRecursively(rootBuildDir, true)
        }
    }
}