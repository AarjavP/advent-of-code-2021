package io.github.aarjavp.aoc.day08

import io.kotest.matchers.maps.shouldContainExactly
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class Day08Test {

    @TestFactory
    fun testDecoder(): List<DynamicTest> {
        data class TestCase(val examples: Set<String>, val expectedMapping: Map<Set<Char>, Int>) {
            fun getTest(name: String): DynamicTest {
                return DynamicTest.dynamicTest(name) {
                    val actual = Day08.Encoding.findEncoding(examples)
                    actual.mapping shouldContainExactly expectedMapping
                }
            }
        }

        val testCases = listOf<TestCase>(
            TestCase(
                examples = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab".split(' ').toSet(),
                expectedMapping = mapOf(
                    setOf('c', 'a', 'g', 'e', 'd', 'b') to 0,
                    setOf('a', 'b') to 1,
                    setOf('g', 'c', 'd', 'f', 'a') to 2,
                    setOf('f','b','c','a','d') to 3,
                    setOf('e', 'a', 'f', 'b') to 4,
                    setOf('c', 'd', 'f', 'b', 'e') to 5,
                    setOf('c', 'd', 'f', 'g', 'e', 'b') to 6,
                    setOf('d', 'a', 'b') to 7,
                    setOf('a', 'c', 'e', 'd', 'g', 'f', 'b') to 8,
                    setOf('c', 'e', 'f', 'a', 'b', 'd') to 9,
                )
            )
        )

        return testCases.mapIndexed { index, testCase ->  testCase.getTest("$index") }
    }

}
