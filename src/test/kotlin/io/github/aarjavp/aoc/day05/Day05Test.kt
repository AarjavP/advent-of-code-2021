package io.github.aarjavp.aoc.day05

import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Test

class Day05Test {

    val solution = Day05()

    val testInput = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent()

    @Test
    fun part1() {
        val actual = solution.part1(testInput.lineSequence())
        actual shouldBeExactly 5
    }
    @Test
    fun part2() {
        val actual = solution.part2(testInput.lineSequence())
        actual shouldBeExactly 12
    }

}
