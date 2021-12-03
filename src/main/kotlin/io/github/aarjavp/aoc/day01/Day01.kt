package io.github.aarjavp.aoc.day01

import io.github.aarjavp.aoc.readFromClasspath

class Day01 {

    fun consolidate(slidingWindowSize: Int, depths: Sequence<Int>): Sequence<Int> {
        return depths.windowed(slidingWindowSize, partialWindows = false) { it.sum() }
    }

    fun countIncreases(depths: Sequence<Int>): Int {
        return depths.zipWithNext().count { it.second > it.first }
    }

}


fun main() {
    val solution = Day01()
    readFromClasspath("Day01.txt").useLines { lines ->
        val scannedDepths = lines.map { it.toInt() }
        val increases = solution.countIncreases(scannedDepths)
        println("increases for raw: $increases")
    }
    readFromClasspath("Day01.txt").use { input ->
        val scannedDepths = input.lineSequence().map { it.toInt() }
        val windowSize = 3
        val windowedDepths = solution.consolidate(slidingWindowSize = windowSize, depths = scannedDepths)
        val increases = solution.countIncreases(windowedDepths)
        println("increases for window size $windowSize: $increases")
    }
}
