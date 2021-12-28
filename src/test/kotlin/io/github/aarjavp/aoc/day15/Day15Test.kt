package io.github.aarjavp.aoc.day15

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Test

class Day15Test {

    val solution = Day15()

    val testInput = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()

    @Test
    fun testParse() {
        val grid = solution.parseGrid(testInput.lineSequence())

        grid.riskLevels shouldContainExactly arrayListOf(
            "1163751742",
            "1381373672",
            "2136511328",
            "3694931569",
            "7463417111",
            "1319128137",
            "1359912421",
            "3125421639",
            "1293138521",
            "2311944581",
        )
        grid.rows shouldBeExactly 10
        grid.cols shouldBeExactly 10
    }

    @Test
    fun testPart1() {
        val grid = solution.parseGrid(testInput.lineSequence())
        val path = solution.findPath(grid)

        path.locations.size shouldBeExactly 19
        path.risk shouldBeExactly 40
    }

    @Test
    fun testGridConversion() {
        val grid = solution.parseGrid(testInput.lineSequence()).toPart2()

        grid.rows shouldBeExactly 50
        grid.cols shouldBeExactly 50
        val bottomRightCorner = Day15.Location(grid.rows - 1, grid.cols - 1)
        grid[bottomRightCorner] shouldBeExactly 9
        grid[bottomRightCorner + Day15.Direction.LEFT] shouldBeExactly 7
        grid[bottomRightCorner + Day15.Direction.UP] shouldBeExactly 9
        grid[bottomRightCorner + Day15.Direction.UP + Day15.Direction.UP] shouldBeExactly 8
        grid[bottomRightCorner + Day15.Direction.UP + Day15.Direction.LEFT] shouldBeExactly 1
    }

    @Test
    fun testPart2() {
        val grid = solution.parseGrid(testInput.lineSequence())
        val path = solution.findPath(grid.toPart2())

        path.risk shouldBeExactly 315
    }

}
