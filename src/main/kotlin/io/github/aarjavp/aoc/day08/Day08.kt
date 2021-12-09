package io.github.aarjavp.aoc.day08

import io.github.aarjavp.aoc.readFromClasspath

class Day08 {

    class Encoding(
        val mapping: Map<Set<Char>, Int>,
    ) {
        companion object {
            //    0:      1:      2:      3:      4:
            //   aaaa    ....    aaaa    aaaa    ....
            //  b    c  .    c  .    c  .    c  b    c
            //  b    c  .    c  .    c  .    c  b    c
            //   ....    ....    dddd    dddd    dddd
            //  e    f  .    f  e    .  .    f  .    f
            //  e    f  .    f  e    .  .    f  .    f
            //   gggg    ....    gggg    gggg    ....
            //
            //    5:      6:      7:      8:      9:
            //   aaaa    aaaa    aaaa    aaaa    aaaa
            //  b    .  b    .  .    c  b    c  b    c
            //  b    .  b    .  .    c  b    c  b    c
            //   dddd    dddd    ....    dddd    dddd
            //  .    f  e    f  .    f  e    f  .    f
            //  .    f  e    f  .    f  e    f  .    f
            //   gggg    gggg    ....    gggg    gggg

            // size based
            // 2 -> 1
            // 3 -> 7
            // 4 -> 4
            // 5 -> 2, 3, 5
            // 6 -> 0, 6, 9
            // 7 -> 8

            // a -> in 0,  , 2, 3,  , 5, 6, 7, 8, 9
            // b -> in 0,  ,  ,  , 4, 5, 6,  , 8, 9
            // c -> in 0, 1, 2, 3, 4,  ,  , 7, 8, 9
            // d -> in  ,  , 2, 3, 4, 5, 6,  , 8, 9
            // e -> in 0,  , 2,  ,  ,  , 6,  , 8,
            // f -> in 0, 1,  , 3, 4, 5, 6, 7, 8, 9
            // g -> in 0,  , 2, 3,  , 5, 6,  , 8, 9

            // we can get numbers 1, 7, 4, 8 just based on size
            // for rest we can disambiguate using those sets:
            // size of 6:
            //   -> take out letters of 4. for 0, 6 -> remaining size is 3. 9 -> 2
            //   -> take out letters of 7, for 0 remaining size is 3, while for 6 remaining size is 4
            // size of 5:
            //   -> only 3 contains all of 1
            //   -> 5 - 6 = 0

            fun findEncoding(codes: Set<String>): Encoding {
                val sets = codes.mapTo(mutableSetOf()) { it.toSet() }
                fun <T> MutableIterable<T>.popFirst(predicate: (T) -> Boolean): T {
                    val iterator = iterator()
                    while (iterator.hasNext()) {
                        val next = iterator.next()
                        if (predicate(next)) {
                            iterator.remove()
                            return next
                        }
                    }
                    throw NoSuchElementException("No element matching predicate found")
                }

                // get sets based on size
                val onesSet = sets.popFirst { it.size == 2 }
                val sevensSet = sets.popFirst { it.size == 3 }
                val foursSet = sets.popFirst { it.size == 4 }
                val eightsSet = sets.popFirst { it.size == 7 }

                val ninesSet = sets.popFirst { it.size == 6 && it.minus(foursSet).size == 2 }
                val zeroesSet = sets.popFirst { it.size == 6 && it.minus(sevensSet).size == 3 }
                val sixesSet = sets.popFirst { it.size == 6 }

                val threesSet = sets.popFirst { it.containsAll(onesSet) }
                val fivesSet = sets.popFirst { it.minus(sixesSet).isEmpty() }
                val twosSet = sets.popFirst { true }
                check(sets.isEmpty())
                val mapping = mapOf(
                    zeroesSet to 0, onesSet to 1, twosSet to 2, threesSet to 3, foursSet to 4, fivesSet to 5,
                    sixesSet to 6, sevensSet to 7, eightsSet to 8, ninesSet to 9
                )
                return Encoding(mapping)
            }
        }

        fun decode(code: String): Int = mapping.getValue(code.toSet())
    }



}

fun main() {
    readFromClasspath("Day08.txt").use { input ->
        val count = input.useLines { lines ->
            lines.sumOf {
                it.substringAfter(" | ").split(' ').count { code ->
                    when(code.length) {
                        2, 3, 4, 7 -> true
                        else -> false
                    }
                }
            }
        }
        println(count)
    }
    readFromClasspath("Day08.txt").use { input ->
        val count = input.useLines { lines ->
            lines.sumOf { line ->
                val (hints, codes) = line.split(" | ")
                val encoding = Day08.Encoding.findEncoding(hints.split(' ').toSet())
                codes.splitToSequence(' ').map { encoding.decode(it) }.joinToString("").toInt()
            }
        }
        println(count)
    }
}
