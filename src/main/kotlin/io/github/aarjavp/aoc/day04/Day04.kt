package io.github.aarjavp.aoc.day04

import io.github.aarjavp.aoc.readFromClasspath
import io.github.aarjavp.aoc.split

class Day04 {

    data class Location(val x: Int, val y: Int)
    class BingoBoard(val mapping: Map<Int, Location>, val gridSize: Int) {
        val markedLocations = mutableSetOf<Location>()
        companion object {
            fun parseBoard(lines: List<String>): BingoBoard {
                val mapping = buildMap<Int, Location> {
                    for ((rowIndex, line) in lines.withIndex()) {
                        for ((colIndex, rawInt) in line.split(' ').filter { it.isNotBlank() }.withIndex()) {
                            val location = Location(colIndex, rowIndex)
                            val number = rawInt.toInt()
                            put(number, location)
                        }
                    }
                }
                return BingoBoard(mapping, lines.size)
            }
        }
        fun mark(n: Int): Location? = mapping[n]?.also { markedLocations.add(it) }

        fun hasWon(): Boolean {
            if (markedLocations.size < gridSize) return false
            if (markedLocations.groupBy { it.x }.any { it.value.size == gridSize}) return true
            return markedLocations.groupBy { it.y }.any { it.value.size == gridSize }
        }

        fun score(multiplier: Int): Int {
            return mapping.entries.filter { it.value !in markedLocations }.sumOf { it.key } * multiplier
        }

    }

    fun parseSetup(lines: Sequence<String>): Pair<Sequence<Int>, List<BingoBoard>> {
        val split = lines.split { it.isBlank() }.toList()
        val numbers = split.first().first().splitToSequence(',').map { it.toInt() }
        val boards = split.subList(1, split.size).map { BingoBoard.parseBoard(it) }
        return numbers to boards
    }


    fun part1(numbers: Sequence<Int>, boards: List<BingoBoard>): Int {
        for (number in numbers) {
            for (board in boards) {
                board.mark(number)?.let {
                    if (board.hasWon())  {
                        return board.score(number)
                    }
                }
            }
        }
        println("winner not found")
        println(numbers)
        println(boards.count())
        return 0
    }

    fun part2(numbers: Sequence<Int>, boards: List<BingoBoard>): Int {
        val remainingBoards = boards.toMutableList()
        for (number in numbers) {
            val iterator = remainingBoards.iterator()
            while (iterator.hasNext()) {
                val board = iterator.next()
                board.mark(number)?.let {
                    if (board.hasWon())  {
                        if (remainingBoards.size == 1) {
                            // this was the last remaining board, and it just won!
                            return board.score(number)
                        } else {
                            iterator.remove()
                        }
                    }
                }
            }
        }
        println("last winner score not found / undefined")
        println(numbers)
        println(boards.count())
        return 0
    }

}

fun main() {
    val solution = Day04()
    readFromClasspath("Day04.txt").useLines { lines ->
        val (numbers, boards) = solution.parseSetup(lines)
        val part1score = solution.part1(numbers, boards)
        val part2score = solution.part2(numbers, boards)
        println(part1score)
        println(part2score)
    }
}

