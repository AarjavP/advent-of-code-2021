package io.github.aarjavp.aoc.day02

import io.github.aarjavp.aoc.readFromClasspath

class Day02 {
    enum class Direction {
        UP, DOWN, FORWARD
    }
    data class Location(var x: Int = 0, var y: Int = 0, var aim: Int = 0) {
        fun advance(direction: Direction, amount: Int) = when(direction) {
            Direction.UP -> y -= amount
            Direction.DOWN -> y += amount
            Direction.FORWARD -> x += amount
        }
        fun advance2(direction: Direction, amount: Int) = when(direction) {
            Direction.UP -> aim -= amount
            Direction.DOWN -> aim += amount
            Direction.FORWARD -> {
                x += amount
                y += aim * amount
            }
        }
    }

    fun move(instructions: Sequence<String>, using: Location.(Direction, Int) -> Unit = Location::advance): Location {
        val location = Location()
        for (instruction in instructions) {
            val (directionRaw, amountRaw) = instruction.split(' ')
            val direction = Direction.valueOf(directionRaw.uppercase())
            val amount = amountRaw.toInt()
            location.using(direction, amount)
        }
        return location
    }

}

fun main() {
    val solution = Day02()
    readFromClasspath("Day02.txt").useLines { lines ->
        val endLocation = solution.move(lines)
        println(endLocation)
        println(endLocation.x * endLocation.y)
    }
    readFromClasspath("Day02.txt").useLines { lines ->
        val endLocation = solution.move(lines, Day02.Location::advance2)
        println(endLocation)
        println(endLocation.x * endLocation.y)
    }
}
