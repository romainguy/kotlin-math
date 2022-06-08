/*
 * Copyright (C) 2017 Romain Guy
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

package dev.romainguy.kotlin.math

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RationalTest {
    @Test
    fun fromInt() {
        assertEquals(0, Rational(0).numerator)
        assertEquals(1, Rational(0).denominator)

        assertEquals(2, Rational(2).numerator)
        assertEquals(1, Rational(2).denominator)

        assertEquals(-2, Rational(-2).numerator)
        assertEquals(1, Rational(-2).denominator)

        assertEquals(Int.MAX_VALUE, Rational(Int.MAX_VALUE).numerator)
        assertEquals(1, Rational(Int.MAX_VALUE).denominator)

        assertEquals(Int.MIN_VALUE, Rational(Int.MIN_VALUE).numerator)
        assertEquals(1, Rational(Int.MIN_VALUE).denominator)
    }

    @Test
    fun fromFloat() {
        assertEquals(0, Rational(0.0f).numerator)
        assertEquals(1, Rational(0.0f).denominator)

        assertEquals(0, Rational(-0.0f).numerator)
        assertEquals(1, Rational(-0.0f).denominator)

        assertEquals(2, Rational(2.0f).numerator)
        assertEquals(1, Rational(2.0f).denominator)

        assertEquals(-2, Rational(-2.0f).numerator)
        assertEquals(1, Rational(-2.0f).denominator)

        assertEquals(1, Rational(0.25f).numerator)
        assertEquals(4, Rational(0.25f).denominator)

        assertEquals(14913081, Rational(3.0f / 27.0f).numerator)
        assertEquals(134217728, Rational(3.0f / 27.0f).denominator)

        assertEquals(Rational.NaN, Rational(Float.NaN))
        assertEquals(Rational.POSITIVE_INFINITY, Rational(Float.POSITIVE_INFINITY))
        assertEquals(Rational.NEGATIVE_INFINITY, Rational(Float.NEGATIVE_INFINITY))
    }

    @Test
    fun fromDouble() {
        assertEquals(0, Rational(0.0).numerator)
        assertEquals(1, Rational(0.0).denominator)

        assertEquals(0, Rational(-0.0).numerator)
        assertEquals(1, Rational(-0.0).denominator)

        assertEquals(2, Rational(2.0).numerator)
        assertEquals(1, Rational(2.0).denominator)

        assertEquals(-2, Rational(-2.0).numerator)
        assertEquals(1, Rational(-2.0).denominator)

        assertEquals(1, Rational(0.25).numerator)
        assertEquals(4, Rational(0.25).denominator)

        assertEquals(-1, Rational(3.0 / 27.0).numerator)
        assertEquals(0, Rational(3.0 / 27.0).denominator)

        assertEquals(Rational.NaN, Rational(Double.NaN))
        assertEquals(Rational.POSITIVE_INFINITY, Rational(Double.POSITIVE_INFINITY))
        assertEquals(Rational.NEGATIVE_INFINITY, Rational(Double.NEGATIVE_INFINITY))
    }

    @Test
    fun isNaN() {
        assertTrue(Rational.NaN.isNaN())
        assertFalse(Rational.POSITIVE_INFINITY.isNaN())
        assertFalse(Rational.NEGATIVE_INFINITY.isNaN())
        assertFalse(Rational.ZERO.isNaN())
        assertFalse(Rational(2, 5).isNaN())
    }

    @Test
    fun isFinite() {
        assertFalse(Rational.NaN.isFinite())
        assertFalse(Rational.POSITIVE_INFINITY.isFinite())
        assertFalse(Rational.NEGATIVE_INFINITY.isFinite())
        assertTrue(Rational.ZERO.isFinite())
        assertTrue(Rational(2, 5).isFinite())
    }

    @Test
    fun isInfinite() {
        assertFalse(Rational.NaN.isInfinite())
        assertTrue(Rational.POSITIVE_INFINITY.isInfinite())
        assertTrue(Rational.NEGATIVE_INFINITY.isInfinite())
        assertFalse(Rational.ZERO.isInfinite())
        assertFalse(Rational(2, 5).isInfinite())
    }

    @Test
    fun isZero() {
        assertFalse(Rational.NaN.isZero())
        assertFalse(Rational.POSITIVE_INFINITY.isZero())
        assertFalse(Rational.NEGATIVE_INFINITY.isZero())
        assertTrue(Rational.ZERO.isZero())
        assertFalse(Rational(2, 5).isZero())
    }

    @Test
    fun sign() {
        assertEquals(1, Rational(2, 5).sign)
        assertEquals(-1, Rational(-2, 5).sign)
        assertEquals(-1, Rational(2, -5).sign)
        assertEquals(1, Rational(-2, -5).sign)
        assertEquals(1, Rational.POSITIVE_INFINITY.sign)
        assertEquals(-1, Rational.NEGATIVE_INFINITY.sign)
        assertEquals(1, Rational.ZERO.sign)
        assertEquals(1, Rational.NaN.sign)
    }

    @Test
    fun numerator() {
        assertEquals(2, Rational(2, 5).numerator)
        assertEquals(-2, Rational(2, -5).numerator)
        assertEquals(-279, Rational(-279, 51233).numerator)
        assertEquals(279, Rational(-279, -51233).numerator)
        assertEquals(2, Rational(4, 30).numerator)

        assertEquals(1, Rational.POSITIVE_INFINITY.numerator)
        assertEquals(-1, Rational.NEGATIVE_INFINITY.numerator)
        assertEquals(0, Rational.ZERO.numerator)
        assertEquals(0, Rational.NaN.numerator)
    }

    @Test
    fun denominator() {
        assertEquals(5, Rational(2, 5).denominator)
        assertEquals(51233, Rational(-279, 51233).denominator)
        assertEquals(5, Rational(2, -5).denominator)
        assertEquals(5, Rational(-2, -5).denominator)
        assertEquals(15, Rational(4, 30).denominator)

        assertEquals(0, Rational.POSITIVE_INFINITY.denominator)
        assertEquals(0, Rational.NEGATIVE_INFINITY.denominator)
        assertEquals(1, Rational.ZERO.denominator)
        assertEquals(0, Rational.NaN.denominator)
    }

    @Test
    fun string() {
        assertEquals("NaN", Rational.NaN.toString())
        assertEquals("+Infinity", Rational.POSITIVE_INFINITY.toString())
        assertEquals("-Infinity", Rational.NEGATIVE_INFINITY.toString())
        assertEquals("0", Rational.ZERO.toString())
        assertEquals("2/5", Rational(2, 5).toString())
        assertEquals("2", Rational(2).toString())
    }

    @Test
    fun toDouble() {
        assertEquals(Double.NaN, Rational.NaN.toDouble())
        assertEquals(Double.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY.toDouble())
        assertEquals(Double.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY.toDouble())
        assertEquals(0.0, Rational.ZERO.toDouble())
        assertEquals(2.0 / 5.0, Rational(2, 5).toDouble())
    }

    @Test
    fun toFloat() {
        assertEquals(Float.NaN, Rational.NaN.toFloat())
        assertEquals(Float.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY.toFloat())
        assertEquals(Float.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY.toFloat())
        assertEquals(0.0f, Rational.ZERO.toFloat())
        assertEquals(2.0f / 5.0f, Rational(2, 5).toFloat())
    }

    @Test
    fun toInt() {
        assertEquals(0, Rational.NaN.toInt())
        assertEquals(Int.MAX_VALUE, Rational.POSITIVE_INFINITY.toInt())
        assertEquals(Int.MIN_VALUE, Rational.NEGATIVE_INFINITY.toInt())
        assertEquals(0, Rational.ZERO.toInt())
        assertEquals(2 / 5, Rational(2, 5).toInt())
    }

    @Test
    fun toLong() {
        assertEquals(0L, Rational.NaN.toLong())
        assertEquals(Long.MAX_VALUE, Rational.POSITIVE_INFINITY.toLong())
        assertEquals(Long.MIN_VALUE, Rational.NEGATIVE_INFINITY.toLong())
        assertEquals(0L, Rational.ZERO.toLong())
        assertEquals(2L / 5L, Rational(2, 5).toLong())
    }

    @Test
    fun unaryPlus() {
        assertEquals(Rational.NaN, +Rational.NaN)
        assertEquals(Rational.POSITIVE_INFINITY, +Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, +Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.ZERO, +Rational.ZERO)
        assertEquals(Rational(2, 5), +Rational(2, 5))
        assertEquals(Rational(-2, 5), +Rational(-2, 5))
        assertEquals(Rational(-2, 5), +Rational(2, -5))
        assertEquals(Rational(2, 5), +Rational(-2, -5))
    }

    @Test
    fun unaryMinus() {
        assertEquals(Rational.NaN, -Rational.NaN)
        assertEquals(Rational.NEGATIVE_INFINITY, -Rational.POSITIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, -Rational.NEGATIVE_INFINITY)
        assertTrue((-Rational.ZERO).isZero())
        assertEquals(Rational(-2, 5), -Rational(2, 5))
        assertEquals(Rational(2, 5), -Rational(-2, 5))
        assertEquals(Rational(2, 5), -Rational(2, -5))
        assertEquals(Rational(-2, 5), -Rational(-2, -5))
    }

    @Test
    fun addition() {
        assertEquals(Rational.NaN, Rational.NaN + Rational.NaN)
        assertEquals(Rational.NaN, Rational.NaN + Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN + Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN + Rational.ZERO)
        assertEquals(Rational.NaN, Rational.NaN + Rational(2, 5))

        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY + Rational.POSITIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY + Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY + Rational.ZERO)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY + Rational(2, 5))

        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY + Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY + Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY + Rational.ZERO)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY + Rational(2, 5))

        assertEquals(Rational(2, 5), Rational(2, 5) + Rational.ZERO)
        assertEquals(Rational(3, 5), Rational(2, 5) + Rational(1, 5))
        assertEquals(Rational(1, 5), Rational(-2, 5) + Rational(3, 5))
        assertEquals(Rational(-4, 5), Rational(-2, 5) + Rational(-2, 5))
    }

    @Test
    fun subtraction() {
        assertEquals(Rational.NaN, Rational.NaN - Rational.NaN)
        assertEquals(Rational.NaN, Rational.NaN - Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN - Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN - Rational.ZERO)
        assertEquals(Rational.NaN, Rational.NaN - Rational(2, 5))

        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY - Rational.POSITIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY - Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY - Rational.ZERO)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY - Rational(2, 5))

        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY - Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY - Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY - Rational.ZERO)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY - Rational(2, 5))

        assertEquals(Rational(2, 5), Rational(2, 5) - Rational.ZERO)
        assertEquals(Rational(1, 5), Rational(2, 5) - Rational(1, 5))
        assertEquals(Rational(-1, 5), Rational(2, 5) - Rational(3, 5))
        assertEquals(Rational(-4, 5), Rational(-2, 5) - Rational(2, 5))
        assertEquals(Rational(1, 5), Rational(-2, 5) - Rational(-3, 5))
    }

    @Test
    fun multiplication() {
        assertEquals(Rational.NaN, Rational.NaN * Rational.NaN)
        assertEquals(Rational.NaN, Rational.NaN * Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN * Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN * Rational.ZERO)
        assertEquals(Rational.NaN, Rational.NaN * Rational(2, 5))

        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY * Rational.POSITIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY * Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY * Rational.ZERO)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY * Rational(2, 5))

        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY * Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY * Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY * Rational.ZERO)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY * Rational(2, 5))

        assertEquals(Rational.ZERO, Rational(2, 5) * Rational.ZERO)
        assertEquals(Rational(4, 25), Rational(2, 5) * Rational(2, 5))
        assertEquals(Rational(-6, 25), Rational(2, 5) * Rational(-3, 5))
        assertEquals(Rational(6, 25), Rational(-2, 5) * Rational(-3, 5))
    }

    @Test
    fun division() {
        assertEquals(Rational.NaN, Rational.NaN / Rational.NaN)
        assertEquals(Rational.NaN, Rational.NaN / Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN / Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NaN, Rational.NaN / Rational.ZERO)
        assertEquals(Rational.NaN, Rational.NaN / Rational(2, 5))

        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY / Rational.POSITIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY / Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY / Rational.ZERO)
        assertEquals(Rational.POSITIVE_INFINITY, Rational.POSITIVE_INFINITY / Rational(2, 5))

        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY / Rational.POSITIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY / Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY / Rational.ZERO)
        assertEquals(Rational.NEGATIVE_INFINITY, Rational.NEGATIVE_INFINITY / Rational(2, 5))

        assertEquals(Rational.POSITIVE_INFINITY, Rational(2, 5) / Rational.ZERO)
        assertEquals(Rational.ZERO, Rational(2, 5) / Rational.POSITIVE_INFINITY)
        assertEquals(Rational.ZERO, Rational(2, 5) / Rational.NEGATIVE_INFINITY)
        assertEquals(Rational.ZERO, Rational.ZERO / Rational(2, 5))
        assertEquals(Rational(-6, 25), Rational(2, 5) * Rational(-3, 5))
    }

    @Test
    fun compareTo() {
        assertEquals(0, Rational.NaN.compareTo(Rational.NaN))
        assertEquals(0, Rational.POSITIVE_INFINITY.compareTo(Rational.POSITIVE_INFINITY))
        assertEquals(0, Rational.NEGATIVE_INFINITY.compareTo(Rational.NEGATIVE_INFINITY))
        assertEquals(0, Rational.ZERO.compareTo(Rational.ZERO))
        assertEquals(0, Rational(2, 5).compareTo(Rational(2, 5)))
        assertEquals(0, Rational(-2, 5).compareTo(Rational(-2, 5)))

        assertEquals(1, Rational(2, 5).compareTo(Rational(-2, 5)))
        assertEquals(-1, Rational(-2, 5).compareTo(Rational(2, 5)))

        assertEquals(1, Rational(2, 5).compareTo(Rational.NEGATIVE_INFINITY))
        assertEquals(1, Rational(-2, 5).compareTo(Rational.NEGATIVE_INFINITY))
        assertEquals(-1, Rational(2, 5).compareTo(Rational.POSITIVE_INFINITY))
        assertEquals(-1, Rational(-2, 5).compareTo(Rational.POSITIVE_INFINITY))

        assertEquals(-1, Rational(1, 4).compareTo(Rational(3, 5)))
        assertEquals(1, Rational(3, 4).compareTo(Rational(3, 5)))
    }
}