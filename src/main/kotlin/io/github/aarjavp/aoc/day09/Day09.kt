package io.github.aarjavp.aoc.day09

import io.github.aarjavp.aoc.readFromClasspath

class Day09 {

    class Grid(val lines: List<String>) {
        data class Location(val row: Int, val column: Int)

        operator fun get(row: Int, column: Int): Char? = lines.getOrNull(row)?.getOrNull(column)
        operator fun get(row: Int, column: Int, direction: Direction): Char? {
            return get(row + direction.rowOffset, column + direction.columnOffset)
        }
        //Note: assumes the location exists on the grid
        operator fun get(location: Location): Char = lines[location.row][location.column]

        operator fun Location.get(direction: Direction): Location? {
            val neighbor = Location(row + direction.rowOffset, column + direction.columnOffset)
            return neighbor.takeIf { get(neighbor.row, neighbor.column) != null }
        }

        fun findLowPoints(): List<Location> {
            val directionsToCheck = Direction.values()
            val lowPoints = mutableListOf<Location>()
            for ((row, line) in lines.withIndex()) {
                candidateCheck@ for (column in line.indices) {
                    val candidate = line[column]
                    for (direction in directionsToCheck) {
                        val neighbor = get(row, column, direction) ?: continue
                        if (candidate >= neighbor) continue@candidateCheck
                    }
                    lowPoints.add(Location(row = row, column = column))
                }
            }
            return lowPoints
        }

        fun findBasins(seeds: List<Location>): List<Set<Location>> {
            val directionsToCheck = Direction.values()
            val basins = mutableListOf<Set<Location>>()

            for (seed in seeds) {
                if (basins.any { seed in it }) continue
                val visited = mutableSetOf<Location>()
                val toCheck = mutableListOf(seed)
                val currentBasin = mutableSetOf(seed)
                while (toCheck.isNotEmpty()) {
                    val current = toCheck.removeFirst()
                    visited += current
                    for (direction in directionsToCheck) {
                        val neighbor = current[direction] ?: continue
                        if (neighbor in visited || this[neighbor] == '9') continue
                        currentBasin += neighbor
                        toCheck += neighbor
                    }
                }
                basins += currentBasin
            }

            return basins
        }
    }
    enum class Direction(val rowOffset: Int = 0, val columnOffset: Int = 0) {
        UP(rowOffset = -1), DOWN(rowOffset = 1), LEFT(columnOffset = -1), RIGHT(columnOffset = 1)
    }

    fun part1(grid: Grid): Int {
        val lowPoints = grid.findLowPoints()
        val risks = lowPoints.map { grid[it].digitToInt() + 1 }

        return risks.sum()
    }

    fun part2(grid: Grid): Int {
        val basins = grid.findBasins(grid.findLowPoints())
        return basins.sortedByDescending { it.size }.take(3).fold(1) { p, basin -> p * basin.size }
    }

}

fun main() {
    val solution = Day09()
    readFromClasspath("Day09.txt").useLines { lines ->
        val grid = Day09.Grid(lines.toList())
        val sumOfRisks = solution.part1(grid)
        println(sumOfRisks)
        val magicNumber = solution.part2(grid)
        println(magicNumber)
    }
}
