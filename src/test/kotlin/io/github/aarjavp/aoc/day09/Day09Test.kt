package io.github.aarjavp.aoc.day09

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Test

class Day09Test {

    @Test
    fun `finds the correct low points`() {
        val testInput = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()

        val grid = Day09.Grid(testInput.lines())
        val actual = grid.findLowPoints()

        actual shouldContainExactly listOf(
            Day09.Grid.Location(row = 0, column = 1),
            Day09.Grid.Location(row = 0, column = 9),
            Day09.Grid.Location(row = 2, column = 2),
            Day09.Grid.Location(row = 4, column = 6),
        )
    }

    @Test
    fun part1Sample() {
        val testInput = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()

        val grid = Day09.Grid(testInput.lines())
        val solution = Day09()
        val actual = solution.part1(grid)

        actual shouldBeExactly 15
    }

    private infix fun Int.to(col: Int): Day09.Grid.Location = Day09.Grid.Location(this, col)

    @Test
    fun `finds the correct basins`() {
        //  0123456789
        val testInput = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()

        val grid = Day09.Grid(testInput.lines())
        val actual = grid.findBasins(grid.findLowPoints())

        actual shouldContainExactlyInAnyOrder listOf<Set<Day09.Grid.Location>>(
            setOf(0 to 0, 0 to 1, 1 to 0),
            setOf(0 to 5, 0 to 6, 0 to 7, 0 to 8, 0 to 9,
                  1 to 6, 1 to 8, 1 to 9,
                  2 to 9),
            setOf(1 to 2, 1 to 3, 1 to 4,
                  2 to 1, 2 to 2, 2 to 3, 2 to 4, 2 to 5,
                  3 to 0, 3 to 1, 3 to 2, 3 to 3, 3 to 4,
                  4 to 1),
            setOf(
                2 to 7,
                3 to 6, 3 to 7, 3 to 8,
                4 to 5, 4 to 6, 4 to 7, 4 to 8, 4 to 9
            ),
        )
    }


    @Test
    fun part2Sample() {
        val testInput = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()

        val grid = Day09.Grid(testInput.lines())
        val solution = Day09()
        val actual = solution.part2(grid)

        actual shouldBeExactly 1134
    }

}
