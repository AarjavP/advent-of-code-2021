package io.github.aarjavp.aoc.day07

import io.github.aarjavp.aoc.readFromClasspath
import java.io.BufferedReader
import kotlin.math.abs

class Day07 {

    fun parseInput(input: BufferedReader): Sequence<Int> {
        return input.readLine().splitToSequence(',').map { it.toInt() }
    }

    fun findMinFuelUsed(positions: Sequence<Int>, distanceToFuel: (Int) -> Int = { it }): Int {
        val sorted = positions.toMutableList().apply { sort() }
        val bruteforce = (sorted.first()..sorted.last()).minOf { calculateFuel(it, sorted, distanceToFuel) }
        return bruteforce
    }

    fun calculateFuel(from: Int, positions: List<Int>, distanceToFuel: (Int) -> Int = { it }): Int {
        return positions.sumOf { distanceToFuel(abs(it - from)) }
    }

}

fun main() {
    val solution = Day07()
    readFromClasspath("Day07.txt").use { input ->
        val init = solution.parseInput(input)
        val part1 = solution.findMinFuelUsed(init)
        println(part1)
        val part2 = solution.findMinFuelUsed(init) { it * (it + 1) / 2 }
        println(part2)
    }
}
