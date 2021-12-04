package io.github.aarjavp.aoc.day04

import io.kotest.assertions.withClue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day04Test {

    @Test
    fun parseBoard() {
        val testBoard = """
           11  2  3
            4 55  6
            7  8 99
        """.trimIndent()

        val actual = Day04.BingoBoard.parseBoard(testBoard.lines())
        actual.mapping shouldContainExactly mapOf(
            11 to Day04.Location(0, 0), 2 to Day04.Location(1, 0), 3 to Day04.Location(2, 0),
            4 to Day04.Location(0, 1), 55 to Day04.Location(1, 1), 6 to Day04.Location(2, 1),
            7 to Day04.Location(0, 2), 8 to Day04.Location(1, 2), 99 to Day04.Location(2, 2),
        )
        actual.gridSize shouldBeExactly 3
    }

    fun sampleBoard(): Day04.BingoBoard {
        val raw = """
        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        """.trimIndent()
        return Day04.BingoBoard.parseBoard(raw.lines())
    }

    @Test
    fun testMarking() {
        val board = sampleBoard()
        data class MarkCase(val n: Int, val expectedLocation: Day04.Location?)
        val cases = listOf<MarkCase>(
            MarkCase(2, Day04.Location(1, 1)), MarkCase(4, Day04.Location(3, 1)),
            MarkCase(36, null), MarkCase(24, Day04.Location(4, 1)),
            MarkCase(10, Day04.Location(1, 3)), MarkCase(19, Day04.Location(4, 4)),
        )
        for (case in cases) {
            val actualLocation = board.mark(case.n)
            withClue("marking ${case.n}") {
                actualLocation shouldBe case.expectedLocation
                if (actualLocation != null) board.markedLocations shouldContain case.expectedLocation
            }
        }
    }

    @Test
    fun checkXWin() {
        val board = sampleBoard()
        val numbersToMark = listOf(22, 2, 14, 21, 12, 9, 7, 24, 18) // marking 16 will win on center row
        for (n in numbersToMark) {
            board.mark(n)
            withClue("marked $n") {
                board.hasWon() shouldBe false
            }
        }
        board.mark(16)
        board.hasWon() shouldBe true
    }

    @Test
    fun checkYWin() {
        val board = sampleBoard()
        val numbersToMark = listOf(22, 2, 14, 21, 12, 19, 8, 16, 1) // marking 6 will win on first col
        for (n in numbersToMark) {
            board.mark(n)
            withClue("marked $n") {
                board.hasWon() shouldBe false
            }
        }
        board.mark(6)
        board.hasWon() shouldBe true
    }

    val solution = Day04()

    val testInput = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7
    """.trimIndent()


    @Test
    fun part1() {
        val (numbers, boards) = solution.parseSetup(testInput.lineSequence())
        val score = solution.part1(numbers, boards)
        score shouldBeExactly 4512
    }

    @Test
    fun part2() {
        val (numbers, boards) = solution.parseSetup(testInput.lineSequence())
        val score = solution.part2(numbers, boards)
        score shouldBeExactly 1924
    }



}
