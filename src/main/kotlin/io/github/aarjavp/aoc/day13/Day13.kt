package io.github.aarjavp.aoc.day13

import io.github.aarjavp.aoc.readFromClasspath
import io.github.aarjavp.aoc.split

class Day13 {

    data class Location(val x: Int, val y: Int)
    class Grid {
        companion object {
            const val MISSING_CHAR = '.'
            const val DOT_CHAR = '#'
        }
        private val state: MutableMap<Int, MutableSet<Int>> = mutableMapOf() // row to col (y, x)
        var rows: Int = 0
            private set
        var columns: Int = 0
            private set

        fun countPoints(): Int = state.values.sumOf { it.size }

        fun put(x: Int, y: Int) {
            state.computeIfAbsent(y) { mutableSetOf() }.add(x)
            rows = maxOf(y + 1, rows)
            columns = maxOf(x + 1, columns)
        }

        fun foldX(along: Int) {
            require(along < columns) { "unexpected fold outside of current grid. Current size: rows=$rows, columns=$columns, requested: $along" }
            for (row in state.values) {
                val toFlip = row.filter { it > along }.map { along - (it - along) }
                row.removeIf { it >= along }
                row.addAll(toFlip)
            }
            columns = along
        }
        fun foldY(along: Int) {
            require(along < rows) { "unexpected fold outside of current grid. Current size: rows=$rows, columns=$columns, requested: $along" }
            for ((row, cols) in state.filter { it.key > along }) {
                val newRow = along - (row - along)
                val newCols = state.computeIfAbsent(newRow) { mutableSetOf() }
                newCols.addAll(cols)
            }
            state.keys.removeIf { it >= along }
            rows = along
        }

        override fun toString(): String {
           return StringBuilder(rows * (columns + 1)).apply {
                for (row in 0 until rows) {
                    val cols = state[row]
                    if (cols == null) {
                        repeat(columns) { append(MISSING_CHAR) }
                    } else {
                        for (col in 0 until columns) {
                            val charToUse = if (col in cols) { DOT_CHAR } else { MISSING_CHAR }
                            append(charToUse)
                        }
                    }
                    appendLine()
                }
            }.toString()
        }
    }

    // returns grid and list of fold instructions
    fun parseInput(input: Sequence<String>): Pair<Grid, List<String>> {
        val (gridInput, foldInput) = input.split { it.isBlank() }.toList()
        val grid = parseGrid(gridInput)
        return grid to foldInput
    }
    fun parseGrid(lines: List<String>): Grid {
        val grid = Grid()
        for (line in lines) {
            val (x,y) = line.splitToSequence(",").map { it.toInt() }.toList()
            grid.put(x, y)
        }
        return grid
    }

    fun applyFolds(grid: Grid, foldInstructions: Iterable<String>) {
        for (foldInstruction in foldInstructions) {
            val line = foldInstruction.substringAfterLast('=').toInt()
            if (foldInstruction.startsWith("fold along y")) {
                grid.foldY(line)
            } else {
                grid.foldX(line)
            }
        }
    }

}

fun main() {
    val solution = Day13()

    val (grid, foldInstructions) = readFromClasspath("Day13.txt").useLines { input ->
        solution.parseInput(input)
    }
    solution.applyFolds(grid, foldInstructions.take(1))
    println(grid.countPoints())
    solution.applyFolds(grid, foldInstructions.drop(1))
    println(grid.toString())
}
