package io.github.aarjavp.aoc.day12

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.maps.shouldContainExactly
import org.junit.jupiter.api.Test

class Day12Test {

    val solution = Day12()

    @Test
    fun parsesCorrectly() {
        val testInput = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent()

        val graph = solution.parse(testInput.lineSequence())

        graph.nodes shouldContainExactly listOf<Day12.Node>(
//            Day12.Node(id = "start", neighbors = mutableSetOf("A", "b"), caveType = Day12.CaveType.SMALL),
            Day12.Node(id = "A", neighbors = mutableSetOf("b", "c"), caveType = Day12.CaveType.BIG),
            Day12.Node(id = "b", neighbors = mutableSetOf("A", "d"), caveType = Day12.CaveType.SMALL),
            Day12.Node(id = "c", neighbors = mutableSetOf("A"), caveType = Day12.CaveType.SMALL),
            Day12.Node(id = "d", neighbors = mutableSetOf("b"), caveType = Day12.CaveType.SMALL),
//            Day12.Node(id = "end", neighbors = mutableSetOf("A", "b"), caveType = Day12.CaveType.SMALL),
        ).associateBy { it.id }
        graph.startingNodes shouldContainExactly setOf("A", "b")
        graph.endingNodes shouldContainExactly setOf("A", "b")
    }

    @Test
    fun findPathsSmall() {
        val testInput = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent()
        val graph = solution.parse(testInput.lineSequence())

        val allPaths = solution.findAllPaths(graph)

        allPaths.map { it.toString() } shouldContainExactlyInAnyOrder """
            start,A,b,A,c,A,end
            start,A,b,A,end
            start,A,b,end
            start,A,c,A,b,A,end
            start,A,c,A,b,end
            start,A,c,A,end
            start,A,end
            start,b,A,c,A,end
            start,b,A,end
            start,b,end
        """.trimIndent().split('\n')
    }

    @Test
    fun findPathsMed() {
        val testInput = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
        """.trimIndent()
        val graph = solution.parse(testInput.lineSequence())

        val allPaths = solution.findAllPaths(graph)

        allPaths.map { it.toString() } shouldContainExactlyInAnyOrder """
            start,HN,dc,HN,end
            start,HN,dc,HN,kj,HN,end
            start,HN,dc,end
            start,HN,dc,kj,HN,end
            start,HN,end
            start,HN,kj,HN,dc,HN,end
            start,HN,kj,HN,dc,end
            start,HN,kj,HN,end
            start,HN,kj,dc,HN,end
            start,HN,kj,dc,end
            start,dc,HN,end
            start,dc,HN,kj,HN,end
            start,dc,end
            start,dc,kj,HN,end
            start,kj,HN,dc,HN,end
            start,kj,HN,dc,end
            start,kj,HN,end
            start,kj,dc,HN,end
            start,kj,dc,end
        """.trimIndent().split('\n')
    }

    @Test
    fun findPathsLarge() {
        val testInput = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
        """.trimIndent()
        val graph = solution.parse(testInput.lineSequence())

        val allPaths = solution.findAllPaths(graph)

        allPaths.size shouldBeExactly 226
    }



    @Test
    fun findPathsSmallPt2() {
        val testInput = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent()
        val graph = solution.parse(testInput.lineSequence())

        val allPaths = solution.findAllPaths(graph, solution::shouldSkipVisitPt2)

        allPaths.map { it.toString() } shouldContainExactlyInAnyOrder """
            start,A,b,A,b,A,c,A,end
            start,A,b,A,b,A,end
            start,A,b,A,b,end
            start,A,b,A,c,A,b,A,end
            start,A,b,A,c,A,b,end
            start,A,b,A,c,A,c,A,end
            start,A,b,A,c,A,end
            start,A,b,A,end
            start,A,b,d,b,A,c,A,end
            start,A,b,d,b,A,end
            start,A,b,d,b,end
            start,A,b,end
            start,A,c,A,b,A,b,A,end
            start,A,c,A,b,A,b,end
            start,A,c,A,b,A,c,A,end
            start,A,c,A,b,A,end
            start,A,c,A,b,d,b,A,end
            start,A,c,A,b,d,b,end
            start,A,c,A,b,end
            start,A,c,A,c,A,b,A,end
            start,A,c,A,c,A,b,end
            start,A,c,A,c,A,end
            start,A,c,A,end
            start,A,end
            start,b,A,b,A,c,A,end
            start,b,A,b,A,end
            start,b,A,b,end
            start,b,A,c,A,b,A,end
            start,b,A,c,A,b,end
            start,b,A,c,A,c,A,end
            start,b,A,c,A,end
            start,b,A,end
            start,b,d,b,A,c,A,end
            start,b,d,b,A,end
            start,b,d,b,end
            start,b,end
        """.trimIndent().split('\n')
    }

}
