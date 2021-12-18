package io.github.aarjavp.aoc.day13

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {

    val solution = Day13()

    @Test
    fun testParseGrid() {
        val testInput = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
        """.trimIndent()

        val grid = solution.parseGrid(testInput.lines())

        grid.countPoints() shouldBeExactly testInput.lineSequence().count()
    }

    @Test
    fun part1() {
        val testInput = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0

            fold along y=7
            fold along x=5
        """.trimIndent()

        val (grid, foldInstructions) = solution.parseInput(testInput.lineSequence())
        solution.applyFolds(grid, foldInstructions.take(1))

        grid.countPoints() shouldBeExactly 17
        grid.toString() shouldBe """
            #.##..#..#.
            #...#......
            ......#...#
            #...#......
            .#.#..#.###
            ...........
            ...........
        """.trimIndent() + "\n"
    }

    @Test
    fun part2() {
        val testInput = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0

            fold along y=7
            fold along x=5
        """.trimIndent()

        val (grid, foldInstructions) = solution.parseInput(testInput.lineSequence())
        solution.applyFolds(grid, foldInstructions)

        grid.countPoints() shouldBeExactly 16
        grid.toString() shouldBe """
            #####
            #...#
            #...#
            #...#
            #####
            .....
            .....
        """.trimIndent() + "\n"
    }

}
