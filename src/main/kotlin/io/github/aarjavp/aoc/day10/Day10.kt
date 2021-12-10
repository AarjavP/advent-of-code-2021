package io.github.aarjavp.aoc.day10

import io.github.aarjavp.aoc.readFromClasspath

class Day10 {

    enum class Brackets(val openingChar: Char, val closingChar: Char, val syntaxScore: Int, val autocompleteScore: Int) {
        ROUND('(', ')', 3, 1),
        SQUARE('[', ']', 57, 2),
        CURLY('{', '}', 1197, 3),
        ANGLE('<', '>', 25137, 4),
        ;
        companion object {
            val values = Brackets.values().asList()
            fun of(char: Char): Brackets = values.first { it.openingChar == char || it.closingChar == char }
        }
    }

    fun getIllegalType(line: String): Brackets? {
        val stack = ArrayDeque<Brackets>()
        for (char in line) {
            val type = Brackets.of(char)
            if (char == type.openingChar) {
                stack.addLast(type)
            } else {
                val actual = stack.removeLast()
                if (actual != type) return type
            }
        }
        return null
    }
    fun complete(line: String): List<Brackets> {
        val stack = ArrayDeque<Brackets>()
        for (char in line) {
            val type = Brackets.of(char)
            if (char == type.openingChar) {
                stack.addLast(type)
            } else {
                val actual = stack.removeLast()
                if (actual != type) error("unexpected")
            }
        }
        return stack.reversed()
    }

    fun score(completion: List<Brackets>): Long {
        return completion.fold(0L) { score, current ->
            score * 5 + current.autocompleteScore
        }
    }

    fun part1(lines: Sequence<String>): Int {
        return lines.mapNotNull { getIllegalType(it) }.sumOf { it.syntaxScore }
    }

    fun part2(lines: Sequence<String>): Long {
        val scores = lines.filter { getIllegalType(it) == null }
            .map { score(complete(it)) }.toMutableList().apply { sort() }
        check(scores.size % 2 == 1)
        return scores[scores.size/2]
    }

}

fun main() {

    val solution = Day10()
    readFromClasspath("Day10.txt").useLines { lines ->
        val part1 = solution.part1(lines)
        println(part1)
    }
    readFromClasspath("Day10.txt").useLines { lines ->
        val part2 = solution.part2(lines)
        println(part2)
    }
}
