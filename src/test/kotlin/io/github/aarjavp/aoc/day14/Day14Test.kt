package io.github.aarjavp.aoc.day14

import io.github.aarjavp.aoc.day14.Day14.Rule
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.maps.shouldContainExactly
import org.junit.jupiter.api.Test

class Day14Test {

    val solution = Day14()
    val testInput = """
            NNCB

            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent()

    @Test
    fun testParse() {
        val state = solution.parseState(testInput.lineSequence())

        state.rules shouldContainExactlyInAnyOrder listOf(
            Rule("CH", 'B'),
            Rule("HH", 'N'),
            Rule("CB", 'H'),
            Rule("NH", 'C'),
            Rule("HB", 'C'),
            Rule("HC", 'B'),
            Rule("HN", 'C'),
            Rule("NN", 'C'),
            Rule("BH", 'H'),
            Rule("NC", 'B'),
            Rule("NB", 'B'),
            Rule("BN", 'B'),
            Rule("BB", 'N'),
            Rule("BC", 'B'),
            Rule("CC", 'N'),
            Rule("CN", 'C'),
        )
        state.pairCounts shouldContainExactly mapOf(
            "NN" to 1, "NC" to 1, "CB" to 1
        )
        state.characterCounts shouldContainExactly mapOf(
            'N' to 2, 'C' to 1, 'B' to 1
        )
    }

    @Test
    fun testApplySteps() {
        val state = solution.parseState(testInput.lineSequence())

        state.applyRules() //1
        state.characterCounts shouldContainExactly mapOf(
            'N' to 2, 'C' to 2, 'B' to 2, 'H' to 1
        )

        repeat(9) { state.applyRules() } // including above, result will be after 10 steps
        state.characterCounts shouldContainExactly mapOf(
            'B' to 1749, 'C' to 298, 'H' to 161, 'N' to 865
        )

    }

    @Test
    fun testPart1() {
        val actual = solution.part1(testInput.lineSequence())

        actual shouldBeExactly 1588
    }

    @Test
    fun testPart2() {
        val actual = solution.part2(testInput.lineSequence())

        actual shouldBeExactly 2188189693529
    }

}
