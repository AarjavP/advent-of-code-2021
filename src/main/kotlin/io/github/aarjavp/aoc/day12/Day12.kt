package io.github.aarjavp.aoc.day12

import io.github.aarjavp.aoc.readFromClasspath

class Day12 {

    enum class CaveType { BIG, SMALL }
    data class Node(val id: String, val neighbors: MutableSet<String>, val caveType: CaveType) {
        constructor(id: String): this(
            id = id, neighbors = mutableSetOf(),
            caveType = if (id[0].isUpperCase()) { CaveType.BIG } else { CaveType.SMALL }
        )
    }

    class Graph(
        val nodes: Map<String, Node>,
        val startingNodes: Set<String>,
        val endingNodes: Set<String>,
    )

    fun parse(paths: Sequence<String>): Graph {
        val nodes = mutableMapOf<String, Node>()
        val startingNodes = mutableSetOf<String>()
        val endingNodes = mutableSetOf<String>()
        for (path in paths) {
            val (left, right) = path.split('-')
            when {
                left == "start" -> startingNodes += right
                left == "end" -> endingNodes += right
                right == "start" -> startingNodes += left
                right == "end" -> endingNodes += left
                else -> {
                    nodes.computeIfAbsent(left, ::Node).neighbors.add(right)
                    nodes.computeIfAbsent(right, ::Node).neighbors.add(left)
                }
            }
        }
        return Graph(nodes, startingNodes, endingNodes)
    }

    data class Path(val traveledNodes: List<String>) {
        operator fun plus(nodeId: String): Path = Path(traveledNodes = traveledNodes + nodeId)
        override fun toString(): String {
            return "start,${traveledNodes.joinToString(",")},end"
        }
    }

    fun shouldSkipVisitPt1(path: Path, node: Node): Boolean {
        return node.caveType == CaveType.SMALL && node.id in path.traveledNodes
    }
    fun shouldSkipVisitPt2(path: Path, node: Node): Boolean {
        return node.caveType == CaveType.SMALL && node.id in path.traveledNodes
                && path.traveledNodes.asSequence().filter { it[0].isLowerCase() } //FIXME
                                                  .groupBy { it }.any { it.value.size > 1 }
    }

    fun findAllPaths(graph: Graph, visitStrategy: (Path, Node) -> Boolean = ::shouldSkipVisitPt1 ): List<Path> {
        val pathsFound = mutableListOf<Path>()
        val pathsToExplore = ArrayDeque<Pair<Path, String>>()
        for (startingNode in graph.startingNodes) {
            pathsToExplore.add(Path(traveledNodes = listOf()) to startingNode)
        }
        while (pathsToExplore.isNotEmpty()) {
            val (path, nodeToVisit) = pathsToExplore.removeFirst()
            val node = graph.nodes[nodeToVisit] ?: error("unexpectedly did not find $nodeToVisit")
            // check if this node is something we can visit
            if (node.caveType == CaveType.SMALL && visitStrategy(path, node)) {
                continue
            }
            val newPath = path + nodeToVisit
            if (nodeToVisit in graph.endingNodes) {
                pathsFound += newPath
            }
            for (neighbor in node.neighbors) {
                pathsToExplore.addLast(newPath to neighbor)
            }
        }

        return pathsFound
    }
}

fun main() {
    val solution = Day12()
    val graph = readFromClasspath("Day12.txt").useLines { lines -> solution.parse(lines) }
    val allPaths = solution.findAllPaths(graph)
    println(allPaths.size)
    println(solution.findAllPaths(graph, solution::shouldSkipVisitPt2).size)
}
