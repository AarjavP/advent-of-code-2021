package io.github.aarjavp.aoc.day06

import io.github.aarjavp.aoc.readFromClasspath
import java.io.BufferedReader

class Day06 {
    val newFishEveryDays = 7
    val daysUntilMature = newFishEveryDays + 2

    fun parseInput(input: BufferedReader): Sequence<Int> {
        return input.readLine().splitToSequence(',').map { it.toInt() }
    }

    fun countFishAfterDays(daysRemainingToBirth: Sequence<Int>, days: Int): Long {
        val counts = LongArray(daysUntilMature)
        for (fish in daysRemainingToBirth) {
            counts[fish]++
        }
        repeat(days) {
            val birthdays = counts[0]
            for (i in 1 until daysUntilMature) {
                counts[i - 1] = counts[i] // age
            }
            counts[newFishEveryDays - 1] += birthdays // parents
            counts[daysUntilMature - 1] = birthdays // kids
        }
        return counts.sum()
    }

}

fun main() {
    val solution = Day06()
    readFromClasspath("Day06.txt").use { input ->
        val init = solution.parseInput(input)
        val part1 = solution.countFishAfterDays(init, 80)
        println(part1)
        val part2 = solution.countFishAfterDays(init, 256)
        println(part2)
    }
}
