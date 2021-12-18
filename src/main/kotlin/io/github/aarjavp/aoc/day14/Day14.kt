package io.github.aarjavp.aoc.day14

import io.github.aarjavp.aoc.readFromClasspath

class Day14 {

    data class Rule(val matchId: String, val addChar: Char) {
        init {
            check(matchId.length == 2)
        }
        val leftPair = "${matchId[0]}$addChar"
        val rightPair = "$addChar${matchId[1]}"
    }

    class State(startingPolymer: String, val rules: List<Rule>) {
        val characterCounts = mutableMapOf<Char, Long>()
        val pairCounts = mutableMapOf<String, Long>()
        init {
            for (c in startingPolymer) {
                characterCounts.merge(c, 1, Long::plus)
            }
            for (pair in startingPolymer.zipWithNext { a, b -> "$a$b" }) {
                pairCounts.merge(pair, 1, Long::plus)
            }
        }

        fun applyRules() {
            val newPairs = mutableMapOf<String, Long>()
            val toRemove = mutableSetOf<String>()
            for (rule in rules) {
                val count = pairCounts[rule.matchId] ?: continue
                toRemove += rule.matchId
                characterCounts.merge(rule.addChar, count, Long::plus)
                newPairs.merge(rule.leftPair, count, Long::plus)
                newPairs.merge(rule.rightPair, count, Long::plus)
            }
            for (pair in toRemove) {
                pairCounts.remove(pair)
            }
            for (newPair in newPairs) {
                pairCounts.merge(newPair.key, newPair.value, Long::plus)
            }
        }
    }

    fun parseState(input: Sequence<String>): State {
        val iterator = input.iterator()
        val startingPolymer = iterator.next()

        check(iterator.next().isBlank())
        val rules = mutableListOf<Rule>()
        while (iterator.hasNext()) {
            val rawRule = iterator.next()
            val (matchId, addCharStr) = rawRule.split(" -> ")
            rules += Rule(matchId, addCharStr[0])
        }
        return State(startingPolymer, rules)
    }

    fun part1(input: Sequence<String>): Long {
        val state = parseState(input)
        repeat(10) { state.applyRules() }
        val entries = state.characterCounts.entries
        return entries.maxOf { it.value } - entries.minOf { it.value }
    }

    fun part2(input: Sequence<String>): Long {
        val state = parseState(input)
        repeat(40) { state.applyRules() }
        val entries = state.characterCounts.entries
        return entries.maxOf { it.value } - entries.minOf { it.value }
    }
}

fun main() {
    val solution = Day14()

    val part1: Long = readFromClasspath("Day14.txt").useLines { input ->
        solution.part1(input)
    }
    println(part1)

    val part2: Long = readFromClasspath("Day14.txt").useLines { input ->
        solution.part2(input)
    }
    println(part2)

}
