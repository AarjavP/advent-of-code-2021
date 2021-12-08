package io.github.aarjavp.aoc.day07

import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Test

class Day07Test {

    val solution = Day07()

    @Test
    fun testFuelCalculation() {
        val testInput = sequenceOf(16,1,2,0,4,2,7,1,2,14).toMutableList()
        val actual = solution.calculateFuel(2, testInput, distanceToFuel = { it })
        actual shouldBeExactly 37
    }

    @Test
    fun testFuelCalculationWithVariableDistance() {
        val testInput = sequenceOf(16,1,2,0,4,2,7,1,2,14).toMutableList()
        val actual = solution.calculateFuel(5, testInput, distanceToFuel = { it * (it + 1) / 2 })
        actual shouldBeExactly 168
    }



}
