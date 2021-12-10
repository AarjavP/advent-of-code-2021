package io.github.aarjavp.aoc.day10

import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day10Test {

    val solution = Day10()

    @Test
    fun part1() {
        val testInput = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent()

        val actual = solution.part1(testInput.lineSequence())

        actual shouldBeExactly 26397
    }


    @TestFactory
    fun complete(): List<DynamicTest> {
        data class TestCase(val input: String, val expectedClosing: String) {
            fun toTest(): DynamicTest = DynamicTest.dynamicTest(input) {
                val actual = solution.complete(input).joinToString("") { it.closingChar.toString() }
                actual shouldBe expectedClosing
            }
        }
        val testCases = listOf(
            TestCase("[({(<(())[]>[[{[]{<()<>>", "}}]])})]"),
            TestCase("[(()[<>])]({[<{<<[]>>(", ")}>]})"),
            TestCase("(((({<>}<{<{<>}{[]{[]{}", "}}>}>))))"),
            TestCase("{<[[]]>}<{[{[{[]{()[[[]", "]]}}]}]}>"),
            TestCase("<{([{{}}[<[[[<>{}]]]>[]]", "])}>"),
            TestCase("<{([{{}}[<[[[<>{}]]]>[]]])}>", ""),
        )

        return testCases.map { it.toTest() }
    }

    @Test
    fun part2() {
        val testInput = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent()

        val actual = solution.part2(testInput.lineSequence())

        actual shouldBeExactly 288957
    }



}
