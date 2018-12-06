package ch.mgysel.aoc

import kotlin.system.measureTimeMillis

fun main() {

    val millis = measureTimeMillis {

        val data = openStream().reader().useLines { lines ->
            lines.map(::parse).toList()
        }

        println("Found ${data.size} points")

    }

    println("Calculated solution in ${millis}ms")
}

fun parse(line: String): Point {
    val (x, y) = line.split(", ").map(String::toInt)
    return Point(x, y)
}

data class Point(val x: Int, val y: Int)

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/data.txt")