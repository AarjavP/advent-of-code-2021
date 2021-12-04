package io.github.aarjavp.aoc

import io.kotest.matchers.sequences.shouldContainExactly
import org.junit.jupiter.api.Test


internal class UtilsKtTest {

    @Test
    fun testSimpleSequenceSplit() {
        val inputLines = """
            first line
            
            second
            third

            third
        """.trimIndent()

        val actual = inputLines.lineSequence().split { it.isBlank() }
        actual shouldContainExactly sequenceOf(
            listOf("first line"),
            listOf("second", "third"),
            listOf("third")
        )
    }

    @Test
    fun testSequenceSplitWithDoubleBlanks() {
        val inputLines = """
            first line
            
            second
            third


            third
        """.trimIndent()

        val actual = inputLines.lineSequence().split { it.isBlank() }
        actual shouldContainExactly sequenceOf(
            listOf("first line"),
            listOf("second", "third"),
            listOf("third")
        )
    }

}
