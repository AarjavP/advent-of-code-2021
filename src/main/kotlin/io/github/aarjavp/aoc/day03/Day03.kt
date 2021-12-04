package io.github.aarjavp.aoc.day03

import io.github.aarjavp.aoc.readFromClasspath

class Day03 {

    data class PowerConsumption(val gammaRate: Int, val epsilonRate: Int) {
        val value = gammaRate * epsilonRate
    }
    fun calculatePowerConsumption(diagnostic: Sequence<String>, bits: Int): PowerConsumption {
        val counts = IntArray(bits)
        var lineCount = 0
        for (line in diagnostic) {
            lineCount++
            for (i in counts.indices) {
                if (line[i] == '1') counts[i]++
            }
        }
        val gamma = counts.map { if (it > lineCount / 2) 1 else 0 }.joinToString(separator = "").toInt(2)
        val epsilon = gamma.inv() and ("1".repeat(counts.size).toInt(2))
        return PowerConsumption(gamma, epsilon)
    }

    data class LifeSupportRating(val oxygenGeneratorRating: Int, val co2ScrubberRating: Int) {
        val value = oxygenGeneratorRating * co2ScrubberRating
    }
    fun calculateLifeSupportRating(diagnostic: Sequence<String>, bits: Int): LifeSupportRating {
        fun List<String>.partitionMostAndLeastCommon(position: Int): Pair<List<String>, List<String>> {
            val (ones, zeroes) = partition { it[position] == '1' }
            return if (ones.size >= zeroes.size) ones to zeroes else zeroes to ones
        }
        var oxygenCandidates: List<String>
        var co2ScrubberCandidates: List<String>
        diagnostic.toList().partitionMostAndLeastCommon(0).run {
            oxygenCandidates = first
            co2ScrubberCandidates = second
        }
        for (i in 1 until bits) {
            if (oxygenCandidates.size > 1) {
                oxygenCandidates = oxygenCandidates.partitionMostAndLeastCommon(i).first
            }
            if (co2ScrubberCandidates.size > 1) {
                co2ScrubberCandidates = co2ScrubberCandidates.partitionMostAndLeastCommon(i).second
            }
            if (oxygenCandidates.size == 1 && co2ScrubberCandidates.size == 1) {
                break
            }
        }
        if (oxygenCandidates.size != 1 && co2ScrubberCandidates.size != 1) {
            error("expected only one value for oxygen and co2 but got oxygen: $oxygenCandidates, co2: $co2ScrubberCandidates")
        }
        return LifeSupportRating(oxygenCandidates.first().toInt(2), co2ScrubberCandidates.first().toInt(2))
    }

}

fun main() {
    val solution = Day03()

    println(readFromClasspath("Day03.txt").useLines { solution.calculatePowerConsumption(it, 12).value })
    println(readFromClasspath("Day03.txt").useLines { solution.calculateLifeSupportRating(it, 12).value })
}
