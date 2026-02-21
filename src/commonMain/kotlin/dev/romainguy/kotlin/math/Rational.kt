/*
 * Copyright (C) 2022 Romain Guy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("NOTHING_TO_INLINE")

package dev.romainguy.kotlin.math

import kotlin.jvm.JvmInline
import kotlin.math.abs
import kotlin.math.sign

/**
 * Creates a [Rational] from a double-precision float value.
 */
fun Rational(value: Double) = pack(value)

/**
 * Creates a [Rational] from a single-precision float value.
 */
fun Rational(value: Float) = pack(value.toDouble())

/**
 * Creates a [Rational] from an integer value.
 */
fun Rational(value: Int) = Rational(pack(value, 1))

/**
 * Creates a [Rational] from a [numerator] and a [denominator].
 */
fun Rational(numerator: Int, denominator: Int) = Rational(pack(numerator, denominator))

/**
 * [Rational] is an inline value class representing a rational number (a fraction).
 * It is backed by a [Long] that packs both the numerator and denominator.
 *
 * It correctly handles special values such as NaN and Infinity.
 *
 * @constructor Creates a new rational with the specified packed value.
 */
@JvmInline
value class Rational(private val r: Long) : Comparable<Rational> {
    companion object {
        /**
         * A constant for the Not-a-Number (NaN) rational value.
         */
        val NaN = Rational(0, 0)

        /**
         * A constant for positive infinity.
         */
        val POSITIVE_INFINITY = Rational(1, 0)

        /**
         * A constant for negative infinity.
         */
        val NEGATIVE_INFINITY = Rational(-1, 0)

        /**
         * A constant for the value zero.
         */
        val ZERO = Rational(0, 1)
    }

    /**
     * Returns the sign of this rational: -1 if negative, 1 otherwise.
     */
    val sign: Int
        get() = if ((r ushr 32).toInt() < 0) -1 else 1

    /**
     * Returns the numerator of this rational.
     */
    val numerator: Int
        get() = (r ushr 32).toInt()

    /**
     * Returns the denominator of this rational.
     */
    val denominator: Int
        get() = (r and 0xFFFFFFFFL).toInt()

    /**
     * Component for destructuring to the numerator.
     */
    fun component1() = numerator

    /**
     * Component for destructuring to the denominator.
     */
    fun component2() = denominator

    /**
     * Returns true if this value is Not-a-Number (NaN).
     */
    fun isNaN() = r == 0L

    /**
     * Returns true if this value is a finite number (the denominator is non-zero).
     */
    fun isFinite() = denominator != 0

    /**
     * Returns true if this value is positive or negative infinity.
     */
    fun isInfinite() = numerator != 0 && denominator == 0

    /**
     * Returns true if this value is zero.
     */
    fun isZero() = numerator == 0 && denominator != 0

    /**
     * Converts this rational to a [Double].
     */
    fun toDouble() = numerator.toDouble() / denominator.toDouble()

    /**
     * Converts this rational to a [Float].
     */
    fun toFloat() = numerator.toFloat() / denominator.toFloat()

    /**
     * Converts this rational to an [Int] by dividing the numerator by the denominator.
     */
    fun toInt() = when {
        isNaN() -> 0
        isInfinite() -> if (sign > 0) Int.MAX_VALUE else Int.MIN_VALUE
        else -> numerator / denominator
    }

    /**
     * Converts this rational to a [Long] by dividing the numerator by the denominator.
     */
    fun toLong() = when {
        isNaN() -> 0L
        isInfinite() -> if (sign > 0) Long.MAX_VALUE else Long.MIN_VALUE
        else -> numerator.toLong() / denominator.toLong()
    }

    /**
     * Negates this rational.
     */
    operator fun unaryMinus(): Rational {
      return Rational(-numerator, denominator)
    }

    /**
     * Returns this rational.
     */
    operator fun unaryPlus() = Rational(r)

    /**
     * Adds another rational to this one.
     */
    operator fun plus(other: Rational): Rational {
        if (r == 0L || other.r == 0L) return NaN

        var n = numerator.toLong()
        var d = denominator.toLong()

        // Infinite
        if (n != 0L && d == 0L) return this

        val on = other.numerator.toLong()
        val od = other.denominator.toLong()

        // Infinite
        if (on != 0L && od == 0L) return this

        val a = n * od
        val b = d * on

        n = a + b
        d *= od

        val gcd = gcd(n, d)
        n /= gcd
        d /= gcd

        return Rational((n shl 32) or d)
    }

    /**
     * Subtracts another rational from this one.
     */
    operator fun minus(other: Rational) = this + (-other)

    /**
     * Multiplies this rational by another.
     */
    operator fun times(other: Rational): Rational {
        if (r == 0L || other.r == 0L) return NaN

        var n = numerator.toLong()
        var d = denominator.toLong()

        // Infinite
        if (n != 0L && d == 0L) return this

        val on = other.numerator.toLong()
        val od = other.denominator.toLong()

        // Infinite
        if (on != 0L && od == 0L) return this

        n *= on
        d *= od

        val gcd = gcd(n, d)
        n /= gcd
        d /= gcd

        return Rational((n shl 32) or d)
    }

    /**
     * Divides this rational by another.
     */
    operator fun div(other: Rational): Rational {
        if (r == 0L || other.r == 0L) return NaN

        var n = numerator.toLong()
        var d = denominator.toLong()

        // Infinite
        if (n != 0L && d == 0L) return this

        // Swap for division
        val on = other.denominator.toLong()
        val od = other.numerator.toLong()

        // Division by infinity
        if (on == 0L && od != 0L) return ZERO

        n *= on
        d *= od

        val gcd = gcd(n, d)
        n /= gcd
        d /= gcd

        return Rational((n shl 32) or d)
    }

    /**
     * Compares this rational to another.
     */
    override fun compareTo(other: Rational): Int {
        if (r == other.r) return 0
        return when {
            isNaN() -> 1
            other.isNaN() -> -1
            isInfinite() && other.isInfinite() -> if (sign > other.sign) 1 else -1
            else -> {
                val a = numerator.toLong() * other.denominator.toLong()
                val b = other.numerator.toLong() * denominator.toLong()
                if (a > b) 1 else -1
            }
        }
    }

    override fun toString() = when {
        isNaN() -> "NaN"
        isInfinite() -> "${if (sign > 0) "+" else "-"}Infinity"
        isZero() -> "${if (sign < 0) "-" else ""}0"
        else -> if (denominator == 1) "$numerator" else "$numerator/$denominator"
    }
}

// Note: we should use a binary implementation of GCD instead of relying on modulo
private tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)
private tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) abs(a) else gcd(b, a % b)

private fun pack(value: Double) = when {
    value.isNaN() -> Rational.NaN
    value.isInfinite() -> if (value > 0.0) Rational.POSITIVE_INFINITY else Rational.NEGATIVE_INFINITY
    value == 0.0 -> if (sign(value) > 0.0) Rational.ZERO else -Rational.ZERO
    else -> {
        val bits = value.toRawBits()
        val sign = bits ushr 63
        val exponent = ((bits ushr 52) xor (sign shl 11)) - 1023L
        val fraction = bits shl 12

        var n = 1L
        var d = 1L

        var i = 63
        while (i >= 12) {
            n = n * 2L + ((fraction ushr i) and 1L)
            d *= 2L
            i--
        }

        if (exponent > 0) {
            n *= 1L shl exponent.toInt()
        } else {
            d *= 1L shl -exponent.toInt()
        }

        if (sign == 1L) n *= -1

        // Simplify before clamping to Int
        val gcd = gcd(n, d)
        n /= gcd
        d /= gcd

        // TODO: We should skip this and finish the packing ourselves to avoid another GCD
        Rational(n.toInt(), d.toInt())
    }
}

private fun pack(numerator: Int, denominator: Int): Long {
    var n = numerator
    var d = denominator

    // Normalize sign
    if (d < 0) {
        n = -n
        d = -d
    }

    if (d == 0) {
        n = if (n > 0) {
            1  // +Inf
        } else if (n < 0) {
            -1 // -Inf
        } else {
            0 // NaN
        }
    } else if (n == 0) {
        d = 1                   // 0
    } else {
        val gcd = gcd(n, d)     // Common case
        n /= gcd
        d /= gcd
    }

    return (n.toLong() shl 32) or d.toLong()
}
