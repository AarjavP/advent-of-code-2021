package io.github.aarjavp.aoc.day02

import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Test

internal class Day02Test {
    val solution = Day02()

    val testInput = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent().lineSequence()

    @Test
    fun part1() {
        val endLocation = solution.move(testInput)
        endLocation.x shouldBeExactly 15
        endLocation.y shouldBeExactly 10
        endLocation.aim shouldBeExactly 0
    }

    @Test
    fun part2() {
        val endLocation = solution.move(testInput, Day02.Location::advance2)
        endLocation.x shouldBeExactly 15
        endLocation.y shouldBeExactly 60
        endLocation.aim shouldBeExactly 10
    }

}
