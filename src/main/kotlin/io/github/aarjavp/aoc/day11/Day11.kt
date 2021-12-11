package io.github.aarjavp.aoc.day11

import io.github.aarjavp.aoc.readFromClasspath

class Day11 {

    enum class Direction(val rowOffset: Int, val colOffset: Int) {
        UP(rowOffset = -1, colOffset = 0),
        UP_RIGHT(rowOffset = -1, colOffset = 1),
        RIGHT(rowOffset = 0, colOffset = 1),
        DOWN_RIGHT(rowOffset = 1, colOffset = 1),
        DOWN(rowOffset = 1, colOffset = 0),
        DOWN_LEFT(rowOffset = 1, colOffset = -1),
        LEFT(rowOffset = 0, colOffset = -1),
        UP_LEFT(rowOffset = -1, colOffset = -1),
        ;
        companion object {
            val values = values().toList()
        }
    }
    data class Location(val row: Int, val col: Int) {
        operator fun plus(direction: Direction): Location {
            return Location(row + direction.rowOffset, col + direction.colOffset)
        }
    }

    class Grid(val state: List<CharArray>) {
        val rows = state.size
        val columns = state.first().size

        init {
            for (row in state) {
                require(row.size == columns) { "Expected all rows to have same number of columns" }
                for (char in row) {
                    check(char.isDigit()) { "Expected all cells to be a digit" }
                }
            }
        }
        fun Location.exists(): Boolean = row >= 0 && row < rows && col >= 0 && col < columns

        var flashesSoFar = 0
        var stepsTaken = 0

        fun step() {
            val flashes = ArrayDeque<Location>()
            for (row in 0 until rows) {
                for (col in 0 until columns) {
                    val actual = state[row][col]
                    if (actual == '9') {
                        state[row][col] = '0'
                        flashesSoFar++
                        flashes += Location(row, col)
                    } else {
                        state[row][col]++
                    }
                }
            }
            fun flash(location: Location) {
                for (direction in Direction.values) {
                    val neighbor = location + direction
                    if (!neighbor.exists()) continue
                    when (state[neighbor.row][neighbor.col]) {
                        '0' -> {
                            // no-op
                        }
                        '9' -> {
                            flashesSoFar++
                            flashes += neighbor
                            state[neighbor.row][neighbor.col] = '0'
                        }
                        else -> state[neighbor.row][neighbor.col]++
                    }
                }
            }
            while (flashes.isNotEmpty()) {
                val flasher = flashes.removeFirst()
                flash(flasher)
            }
            stepsTaken++
        }

        fun writeTo(dest: Appendable) {
            for (row in state) {
                dest.appendLine(String(row))
            }
        }

        override fun toString(): String {
            return StringBuilder().also { writeTo(it) }.toString()
        }

    }

    fun parse(lines: Sequence<String>): Grid {
        return Grid(lines.map { it.toCharArray() }.toList())
    }

    fun findSyncStep(grid: Grid): Int {
        //finds the next sync flashing step. Hopefully it's the first :)
        val targetFlashes = grid.rows * grid.columns
        while (true) {
            val startingFlashes = grid.flashesSoFar
            grid.step()
            val flashesThisStep = grid.flashesSoFar - startingFlashes
            if (flashesThisStep == targetFlashes) {
                return grid.stepsTaken
            }
        }
    }

}

fun main() {
    val solution = Day11()
    val grid = readFromClasspath("Day11.txt").useLines { lines ->
        solution.parse(lines)
    }
    repeat(100) { grid.step() }
    println("flashes after 100: ${grid.flashesSoFar}")
    val step = solution.findSyncStep(grid)
    println("sync step: $step")
}
