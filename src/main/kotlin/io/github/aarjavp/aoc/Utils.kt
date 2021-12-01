package io.github.aarjavp.aoc

import java.io.BufferedReader
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Opens a BufferedReader for resource on classpath at the given path.
 * The caller is responsible for closing the reader.
 */
fun readFromClasspath(path: String): BufferedReader {
    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(path)
    if (stream == null) error("Requested resource not found on classpath: $path")
    return stream.bufferedReader()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
