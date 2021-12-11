package io.github.aarjavp.aoc.day11

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Test {

    val solution = Day11()

    @Test
    fun parsing() {
        val testInput = """
            11111
            19991
            19191
            19991
            11111
        """.trimIndent()

        val grid = solution.parse(testInput.lineSequence())

        grid.rows shouldBeExactly 5
        grid.columns shouldBeExactly 5
        grid.toString() shouldBe (testInput + "\n")
    }


    @Test
    fun testTick() {
        val testInput = """
            11111
            19991
            19191
            19991
            11111
        """.trimIndent()
        val grid = solution.parse(testInput.lineSequence())

        grid.step()

        grid.flashesSoFar = 9
        grid.toString() shouldBe """
            34543
            40004
            50005
            40004
            34543
        """.trimIndent() + "\n"
    }

    @Test
    fun testHundredFlashes() {
        val testInput = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
        val grid = solution.parse(testInput.lineSequence())

        repeat(100) { grid.step() }

        grid.flashesSoFar = 1656
        grid.toString() shouldBe """
            0397666866
            0749766918
            0053976933
            0004297822
            0004229892
            0053222877
            0532222966
            9322228966
            7922286866
            6789998766
        """.trimIndent() + "\n"
    }

    @Test
    fun testSyncStepFind() {
        val testInput = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
        val grid = solution.parse(testInput.lineSequence())

        val actual = solution.findSyncStep(grid)

        actual shouldBeExactly 195
    }

}
