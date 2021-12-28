package io.github.aarjavp.aoc.day15

import io.github.aarjavp.aoc.readFromClasspath
import java.util.*

class Day15 {

    enum class Direction(val rowOffset: Int, val colOffset: Int) {
        UP(-1, 0), DOWN(1, 0),
        LEFT(0, -1), RIGHT(0, 1);
        companion object {
            val values = values().toList()
        }
    }
    data class Location(val row: Int, val col: Int) {
        operator fun plus(direction: Direction): Location = copy(
            row = row + direction.rowOffset,
            col = col + direction.colOffset
        )
    }
    open class Grid(val riskLevels: ArrayList<String>, val rows: Int, val cols: Int) {
        private val rowRange = 0 until rows
        private val colRange = 0 until cols
        open operator fun get(location: Location): Int = riskLevels[location.row][location.col].digitToInt()
        operator fun contains(location: Location): Boolean = location.row in rowRange && location.col in colRange
        fun toPart2(): Grid2 = Grid2(riskLevels = riskLevels, templateRows = rows, templateCols = cols)
    }
    data class Path(val locations: ArrayList<Location>, val risk: Int) {
        val destination: Location
            get() = locations.last()
    }

    fun parseGrid(lines: Sequence<String>): Grid {
        val mapped = lines.toCollection(arrayListOf())
        return Grid(mapped, rows = mapped.size, cols = mapped.first().length)
    }

    fun findPath(grid: Grid): Path {
        val visited = mutableSetOf<Location>()
        val pathsToWalk = PriorityQueue<Path>( Comparator.comparingInt { it.risk } )
        pathsToWalk.add(Path(locations = arrayListOf(Location(0, 0)), risk = 0))

        operator fun Path.plus(direction: Direction): Path? {
            val newDestination = destination + direction
            if (newDestination !in grid) return null
            val newLocations = ArrayList<Location>(locations.size + 1).apply { addAll(locations) }
            newLocations.add(newDestination)
            val newRisk = risk + grid[newDestination]
            return Path(locations = newLocations, risk = newRisk)
        }
        val destination = Location(grid.rows - 1, grid.cols - 1)
        while (pathsToWalk.isNotEmpty()) {
            val pathToExtend = pathsToWalk.poll()
            if (pathToExtend.destination == destination) {
                return pathToExtend
            }
            if (pathToExtend.destination !in visited) {
                visited += pathToExtend.destination
                for (direction in Direction.values) {
                    val newPath = pathToExtend + direction
                    newPath?.let { pathsToWalk.add(it) }
                }
            }
        }
        error("No path to $destination found. Visited $visited")
    }

    class Grid2(riskLevels: ArrayList<String>, val templateRows: Int, val templateCols: Int):
        Grid(riskLevels, templateRows * 5, templateCols * 5) {
        override operator fun get(location: Location): Int {
            val init = riskLevels[location.row % templateRows][location.col % templateCols].digitToInt()
            val additional = location.row / templateRows + location.col / templateCols
            val sum = init + additional
            return if (sum > 9) sum - 9 else sum
        }
    }

}

fun main() {

    val solution = Day15()
    val grid = readFromClasspath("Day15.txt").useLines { lines -> solution.parseGrid(lines) }

    val part1 = solution.findPath(grid).risk
    println(part1)

    val part2 = solution.findPath(grid.toPart2()).risk
    println(part2)
}
