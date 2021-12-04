package io.github.aarjavp.aoc.day03

import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Test

internal class Day03Test {

    val solution = Day03()
    val testInput = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent()


    @Test
    fun part1() {
        val actual = solution.calculatePowerConsumption(testInput.lineSequence(), 5)
        actual.gammaRate shouldBeExactly 22
        actual.epsilonRate shouldBeExactly 9
    }

    @Test
    fun part2() {
        val actual = solution.calculateLifeSupportRating(testInput.lineSequence(), 5)
        actual.oxygenGeneratorRating shouldBeExactly 23
        actual.co2ScrubberRating shouldBeExactly 10
    }


}
