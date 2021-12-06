package io.github.aarjavp.aoc.day05

import io.github.aarjavp.aoc.readFromClasspath

infix fun Int.towards(end: Int): IntProgression {
    val step = if (this > end) -1 else 1
    return IntProgression.fromClosedRange(this, end, step)
}

class Day05 {
    data class Point(val x: Int, val y: Int)
    data class Line(val start: Point, val end: Point) {
        val isHorizontal
            get() = start.y == end.y
        val isVertical
            get() = start.x == end.x
        val isDiagonal //45 deg assumed
            get() = start.x != end.x && start.y != end.x

        fun getPoints(): Sequence<Point> {
            return when {
                isVertical -> {
                    (start.y towards end.y).asSequence().map { Point(x = start.x, y = it) }
                }
                isHorizontal -> {
                    (start.x towards end.x).asSequence().map { Point(x = it, y = start.y) }
                }
                //assume 45 deg diagonal
                else -> {
                    val xRange = (start.x towards end.x).asSequence()
                    val yRange = (start.y towards end.y).asSequence()
                    return xRange.zip(yRange).map { (x, y) -> Point(x, y) }
                }
            }
        }

    }
    class SparseIntPlane {
        val coveredPoints = mutableMapOf<Point, Int>()
        fun add(line: Line) {
            val points = line.getPoints()
            for (point in points) {
                coveredPoints.compute(point) { _, prev -> if (prev == null) 1 else { prev + 1 } }
            }
        }
    }

    fun parseLines(input: Sequence<String>): Sequence<Line> {
        fun parsePoint(raw: String): Point {
            val (x, y) = raw.split(',').map { it.toInt() }
            return Point(x, y)
        }
        return input.map { line ->
            val (start, end) = line.split(" -> ").map { parsePoint(it) }
            Line(start, end)
        }
    }

    fun part1(input: Sequence<String>): Int {
        val plane = SparseIntPlane()
        for (line in parseLines(input).filter { it.isVertical || it.isHorizontal }) {
            plane.add(line)
        }
        return plane.coveredPoints.count { it.value > 1 }
    }
    fun part2(input: Sequence<String>): Int {
        val plane = SparseIntPlane()
        for (line in parseLines(input)) {
            plane.add(line)
        }
        return plane.coveredPoints.count { it.value > 1 }
    }

}

fun main() {
    val solution = Day05()
    readFromClasspath("Day05.txt").useLines { lines ->
        val points = solution.part1(lines)
        println(points)
    }
    readFromClasspath("Day05.txt").useLines { lines ->
        val points = solution.part2(lines)
        println(points)
    }
}
