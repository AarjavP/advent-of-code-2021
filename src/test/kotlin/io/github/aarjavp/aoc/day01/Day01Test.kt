package io.github.aarjavp.aoc.day01

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.sequences.shouldContainExactly
import org.junit.jupiter.api.Test

internal class Day01Test {
    val solution = Day01()

    @Test
    fun `test consolidate with window size 2`() {
        val source = sequenceOf(1, 2, 5, 6, 8, 12)
        val actual = solution.consolidate(2, depths = source)

        actual shouldContainExactly sequenceOf(3, 7, 11, 14, 20)
    }

    @Test
    fun countIncreases() {
        val source = sequenceOf(1, 2, 5, 2, 8, 4)
        val actual = solution.countIncreases(source)

        actual shouldBeExactly 3
    }

}
