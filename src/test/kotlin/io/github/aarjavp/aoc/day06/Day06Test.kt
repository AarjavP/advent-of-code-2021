package io.github.aarjavp.aoc.day06

import io.kotest.matchers.longs.shouldBeExactly
import org.junit.jupiter.api.Test

class Day06Test {
    val solution = Day06()

    @Test
    fun part1() {
        val testInput = sequenceOf(3,4,3,1,2)
        solution.countFishAfterDays(testInput, 18) shouldBeExactly 26
        solution.countFishAfterDays(testInput, 80) shouldBeExactly 5934
        solution.countFishAfterDays(testInput, 256) shouldBeExactly 26984457539
    }

}
