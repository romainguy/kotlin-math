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

import kotlin.test.*

class HalfTest {
    @Test
    fun singleToHalf() {
        // Zeroes, NaN and infinities
        assertEquals(Half.POSITIVE_ZERO, 0.0f.toHalf())
        assertEquals(Half.NEGATIVE_ZERO, (-0.0f).toHalf())
        assertEquals(Half.NaN, Float.NaN.toHalf())
        assertEquals(Half.POSITIVE_INFINITY, Float.POSITIVE_INFINITY.toHalf())
        assertEquals(Half.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY.toHalf())
        // Known values
        assertEquals(Half(0x3c01.toUShort()), 1.0009766f.toHalf())
        assertEquals(Half(0xc000.toUShort()), (-2.0f).toHalf())
        assertEquals(Half(0x0400.toUShort()), 6.10352e-5f.toHalf())
        assertEquals(Half(0x7bff.toUShort()), 65504.0f.toHalf())
        assertEquals(Half(0x3555.toUShort()), (1.0f / 3.0f).toHalf())
        // Denormals
        assertEquals(Half.MIN_VALUE, 5.96046e-8f.toHalf())
        assertEquals(Half(0x03ff.toUShort()), 6.09756e-5f.toHalf())
        assertEquals(Half(0x83ff.toUShort()), (-6.09756e-5f).toHalf())
        assertEquals(Half(0x8001.toUShort()), (-5.96046e-8f).toHalf())
        // Denormals (flushed to +/-0)
        assertEquals(Half.POSITIVE_ZERO, 5.96046e-9f.toHalf())
        assertEquals(Half.NEGATIVE_ZERO, (-5.96046e-9f).toHalf())
        // Test for values that overflow the mantissa bits into exp bits
        assertEquals(Half(0x1000.toUShort()), Float.fromBits(0x39fff000).toHalf())
        assertEquals(Half(0x0400.toUShort()), Float.fromBits(0x387fe000).toHalf())
        // Floats with absolute value above +/-65519 are rounded to +/-inf
        // when using round-to-even
        assertEquals(Half.MAX_VALUE, 65519.0f.toHalf())
        assertEquals(Half.MAX_VALUE, 65519.9f.toHalf())
        assertEquals(Half.POSITIVE_INFINITY, 65520.0f.toHalf())
        assertEquals(Half.NEGATIVE_INFINITY, (-65520.0f).toHalf())
        // Check if numbers are rounded to nearest even when they
        // cannot be accurately represented by Half
        assertEquals(Half(0x6801.toUShort()), 2049.0f.toHalf())
        assertEquals(Half(0x6c01.toUShort()), 4098.0f.toHalf())
        assertEquals(Half(0x7001.toUShort()), 8196.0f.toHalf())
        assertEquals(Half(0x7401.toUShort()), 16392.0f.toHalf())
        assertEquals(Half(0x7801.toUShort()), 32784.0f.toHalf())
    }

    @Test
    fun halfToSingle() {
        // Zeroes, NaN and infinities
        assertEquals(0.0f, 0.0f.toHalf().toFloat(), 1e-6f)
        assertEquals(-0.0f, (-0.0f).toHalf().toFloat(), 1e-6f)
        assertEquals(Float.NaN, Float.NaN.toHalf().toFloat(), 1e-6f)
        assertEquals(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY.toHalf().toFloat(), 1e-6f)
        assertEquals(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY.toHalf().toFloat(), 1e-6f)
        // Known values
        assertEquals(1.0009766f, 1.0009766f.toHalf().toFloat(), 1e-6f)
        assertEquals(-2.0f, (-2.0f).toHalf().toFloat(), 1e-6f)
        assertEquals(6.1035156e-5f, 6.10352e-5f.toHalf().toFloat(), 1e-6f) // Inexact
        assertEquals(65504.0f, 65504.0f.toHalf().toFloat(), 1e-6f)
        assertEquals(0.33325195f, (1.0f / 3.0f).toHalf().toFloat(), 1e-6f) // Inexact
        // Denormals (flushed to +/-0)
        assertEquals(6.097555e-5f, 6.09756e-5f.toHalf().toFloat(), 1e-6f)
        assertEquals(5.9604645e-8f, 5.96046e-8f.toHalf().toFloat(), 1e-9f)
        assertEquals(-6.097555e-5f, (-6.09756e-5f).toHalf().toFloat(), 1e-6f)
        assertEquals(-5.9604645e-8f, (-5.96046e-8f).toHalf().toFloat(), 1e-9f)
    }

    @Test
    fun hexString() {
        assertEquals("NaN", Half.NaN.toHexString())
        assertEquals("Infinity", Half.POSITIVE_INFINITY.toHexString())
        assertEquals("-Infinity", Half.NEGATIVE_INFINITY.toHexString())
        assertEquals("0x0.0p0", Half.POSITIVE_ZERO.toHexString())
        assertEquals("-0x0.0p0", Half.NEGATIVE_ZERO.toHexString())
        assertEquals("0x1.0p0", 1.0f.toHalf().toHexString())
        assertEquals("-0x1.0p0", (-1.0f).toHalf().toHexString())
        assertEquals("0x1.0p1", 2.0f.toHalf().toHexString())
        assertEquals("0x1.0p8", 256.0f.toHalf().toHexString())
        assertEquals("0x1.0p-1", 0.5f.toHalf().toHexString())
        assertEquals("0x1.0p-2", 0.25f.toHalf().toHexString())
        assertEquals("0x1.3ffp15", Half.MAX_VALUE.toHexString())
        assertEquals("0x0.1p-14", Half.MIN_VALUE.toHexString())
        assertEquals("0x1.0p-14", Half.MIN_NORMAL.toHexString())
        assertEquals("-0x1.3ffp15", Half.LOWEST_VALUE.toHexString())
    }

    @Test
    fun string() {
        assertEquals("NaN", Half.NaN.toString())
        assertEquals("Infinity", Half.POSITIVE_INFINITY.toString())
        assertEquals("-Infinity", Half.NEGATIVE_INFINITY.toString())
        assertEquals("0.0", Half.POSITIVE_ZERO.toString())
        assertEquals("-0.0", Half.NEGATIVE_ZERO.toString())
        assertEquals("1.0", 1.0f.toHalf().toString())
        assertEquals("-1.0", (-1.0f).toHalf().toString())
        assertEquals("2.0", 2.0f.toHalf().toString())
        assertEquals("256.0", 256.0f.toHalf().toString())
        assertEquals("0.5", 0.5f.toHalf().toString())
        assertEquals("0.25", 0.25f.toHalf().toString())
        assertEquals("65504.0", Half.MAX_VALUE.toString())
        assertContains(arrayOf("5.9604645E-8", "5.9604645e-8", "5.960464477539063e-8"), Half.MIN_VALUE.toString())
        assertContains(arrayOf("6.1035156E-5", "0.00006103515625"), Half.MIN_NORMAL.toString())
        assertEquals("-65504.0", Half.LOWEST_VALUE.toString())
    }

    @Test
    fun exponent() {
        assertEquals(16, Half.POSITIVE_INFINITY.exponent)
        assertEquals(16, Half.NEGATIVE_INFINITY.exponent)
        assertEquals(16, Half.NaN.exponent)
        assertEquals(-15, Half.POSITIVE_ZERO.exponent)
        assertEquals(-15, Half.NEGATIVE_ZERO.exponent)
        assertEquals(0, 1.0f.toHalf().exponent)
        assertEquals(-4, 0.1f.toHalf().exponent)
        assertEquals(-10, 0.001f.toHalf().exponent)
        assertEquals(7, 128.8f.toHalf().exponent)
    }

    @Test
    fun significand() {
        assertEquals(0, Half.POSITIVE_INFINITY.significand)
        assertEquals(0, Half.NEGATIVE_INFINITY.significand)
        assertEquals(512, Half.NaN.significand)
        assertEquals(0, Half.POSITIVE_ZERO.significand)
        assertEquals(0, Half.NEGATIVE_ZERO.significand)
        assertEquals(614, 0.1f.toHalf().significand)
        assertEquals(25, 0.001f.toHalf().significand)
        assertEquals(6, 128.8f.toHalf().significand)
    }

    @Test
    fun sign() {
        assertEquals(1.0f.toHalf(), Half.POSITIVE_INFINITY.sign)
        assertEquals((-1.0f).toHalf(), Half.NEGATIVE_INFINITY.sign)
        assertEquals(0.0f.toHalf(), Half.POSITIVE_ZERO.sign)
        assertEquals(0.0f.toHalf(), Half.NEGATIVE_ZERO.sign)
        assertEquals(Half.NaN, Half.NaN.sign)
        assertEquals(1.0f.toHalf(), 12.4f.toHalf().sign)
        assertEquals((-1.0f).toHalf(), (-12.4f).toHalf().sign)
    }

    @Test
    fun isInfinite() {
        assertTrue(Half.POSITIVE_INFINITY.isInfinite())
        assertTrue(Half.NEGATIVE_INFINITY.isInfinite())
        assertFalse(Half.POSITIVE_ZERO.isInfinite())
        assertFalse(Half.NEGATIVE_ZERO.isInfinite())
        assertFalse(Half.NaN.isInfinite())
        assertFalse(Half.MAX_VALUE.isInfinite())
        assertFalse(Half.LOWEST_VALUE.isInfinite())
        assertFalse((-128.3f).toHalf().isInfinite())
        assertFalse(128.3f.toHalf().isInfinite())
    }

    @Test
    fun isFinite() {
        assertFalse(Half.POSITIVE_INFINITY.isFinite())
        assertFalse(Half.NEGATIVE_INFINITY.isFinite())
        assertTrue(Half.POSITIVE_ZERO.isFinite())
        assertTrue(Half.NEGATIVE_ZERO.isFinite())
        assertFalse(Half.NaN.isFinite())
        assertTrue(Half.MAX_VALUE.isFinite())
        assertTrue(Half.LOWEST_VALUE.isFinite())
        assertTrue((-128.3f).toHalf().isFinite())
        assertTrue(128.3f.toHalf().isFinite())
    }

    @Test
    fun isNaN() {
        assertFalse(Half.POSITIVE_INFINITY.isNaN())
        assertFalse(Half.NEGATIVE_INFINITY.isNaN())
        assertFalse(Half.POSITIVE_ZERO.isNaN())
        assertFalse(Half.NEGATIVE_ZERO.isNaN())
        assertTrue(Half.NaN.isNaN())
        assertTrue(Half(0x7c01.toUShort()).isNaN())
        assertTrue(Half(0x7c18.toUShort()).isNaN())
        assertTrue(Half(0xfc01.toUShort()).isNaN())
        assertTrue(Half(0xfc98.toUShort()).isNaN())
        assertFalse(Half.MAX_VALUE.isNaN())
        assertFalse(Half.LOWEST_VALUE.isNaN())
        assertFalse((-128.3f).toHalf().isNaN())
        assertFalse(128.3f.toHalf().isNaN())
    }

    @Test
    fun isZero() {
        assertFalse(Half.POSITIVE_INFINITY.isZero())
        assertFalse(Half.NEGATIVE_INFINITY.isZero())
        assertTrue(Half.POSITIVE_ZERO.isZero())
        assertTrue(Half.NEGATIVE_ZERO.isZero())
        assertFalse(Half.NaN.isZero())
        assertFalse(Half(0x7c01.toUShort()).isZero())
        assertFalse(Half(0x7c18.toUShort()).isZero())
        assertFalse(Half(0xfc01.toUShort()).isZero())
        assertFalse(Half(0xfc98.toUShort()).isZero())
        assertFalse(Half.MAX_VALUE.isZero())
        assertFalse(Half.LOWEST_VALUE.isZero())
        assertFalse((-128.3f).toHalf().isZero())
        assertFalse(128.3f.toHalf().isZero())
    }

    @Test
    fun isNormalized() {
        assertFalse(Half.POSITIVE_INFINITY.isNormalized())
        assertFalse(Half.NEGATIVE_INFINITY.isNormalized())
        assertFalse(Half.POSITIVE_ZERO.isNormalized())
        assertFalse(Half.NEGATIVE_ZERO.isNormalized())
        assertFalse(Half.NaN.isNormalized())
        assertTrue(Half.MAX_VALUE.isNormalized())
        assertTrue(Half.MIN_NORMAL.isNormalized())
        assertTrue(Half.LOWEST_VALUE.isNormalized())
        assertTrue((-128.3f).toHalf().isNormalized())
        assertTrue(128.3f.toHalf().isNormalized())
        assertTrue(0.3456f.toHalf().isNormalized())
        assertFalse(Half.MIN_VALUE.isNormalized())
        assertFalse(Half(0x3ff.toUShort()).isNormalized())
        assertFalse(Half(0x200.toUShort()).isNormalized())
        assertFalse(Half(0x100.toUShort()).isNormalized())
    }

    @Test
    fun copySign() {
        assertEquals(7.5f.toHalf(), (-7.5f).toHalf().withSign(Half.POSITIVE_INFINITY))
        assertEquals(7.5f.toHalf(), (-7.5f).toHalf().withSign(Half.POSITIVE_ZERO))
        assertEquals((-7.5f).toHalf(), 7.5f.toHalf().withSign(Half.NEGATIVE_INFINITY))
        assertEquals((-7.5f).toHalf(), 7.5f.toHalf().withSign(Half.NEGATIVE_ZERO))
        assertEquals(7.5f.toHalf(), 7.5f.toHalf().withSign(Half.NaN))
        assertEquals(7.5f.toHalf(), 7.5f.toHalf().withSign(12.4f.toHalf()))
        assertEquals((-7.5f).toHalf(), 7.5f.toHalf().withSign((-12.4f).toHalf()))
    }

    @Test
    fun abs() {
        assertEquals(Half.POSITIVE_INFINITY, abs(Half.POSITIVE_INFINITY))
        assertEquals(Half.POSITIVE_INFINITY, abs(Half.NEGATIVE_INFINITY))
        assertEquals(Half.POSITIVE_ZERO, abs(Half.POSITIVE_ZERO))
        assertEquals(Half.POSITIVE_ZERO, abs(Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, abs(Half.NaN))
        assertEquals(Half.MAX_VALUE, abs(Half.LOWEST_VALUE))
        assertEquals(12.12345f.toHalf(), abs((-12.12345f).toHalf()))
        assertEquals(12.12345f.toHalf(), abs(12.12345f.toHalf()))

        assertEquals(Half.POSITIVE_INFINITY, Half.POSITIVE_INFINITY.absoluteValue)
        assertEquals(Half.POSITIVE_INFINITY, Half.NEGATIVE_INFINITY.absoluteValue)
        assertEquals(Half.POSITIVE_ZERO, Half.POSITIVE_ZERO.absoluteValue)
        assertEquals(Half.POSITIVE_ZERO, Half.NEGATIVE_ZERO.absoluteValue)
        assertEquals(Half.NaN, Half.NaN.absoluteValue)
        assertEquals(Half.MAX_VALUE, Half.LOWEST_VALUE.absoluteValue)
        assertEquals(12.12345f.toHalf(), (-12.12345f).toHalf().absoluteValue)
        assertEquals(12.12345f.toHalf(), 12.12345f.toHalf().absoluteValue)
    }

    @Test
    fun fromString() {
        assertFailsWith(NumberFormatException::class) {
            Half("not a number")
        }
        assertFailsWith(NumberFormatException::class) {
            "not a number".toHalf()
        }

        assertEquals(Half("NaN"), Half.NaN)
        assertEquals(Half("Infinity"), Half.POSITIVE_INFINITY)
        assertEquals(Half("-Infinity"), Half.NEGATIVE_INFINITY)
        assertEquals(Half("0.0"), Half.POSITIVE_ZERO)
        assertEquals(Half("-0.0"), Half.NEGATIVE_ZERO)
        assertEquals(Half("1.0"), 1.0f.toHalf())
        assertEquals(Half("-1.0"), (-1.0f).toHalf())
        assertEquals(Half("2.0"), 2.0f.toHalf())
        assertEquals(Half("256.0"), 256.0f.toHalf())
        assertEquals(Half("0.5"), 0.5f.toHalf())
        assertEquals(Half("0.25"), 0.25f.toHalf())
        assertEquals(Half("65504.0"), Half.MAX_VALUE)
        assertEquals(Half("5.9604645E-8"), Half.MIN_VALUE)
        assertEquals(Half("6.1035156E-5"), Half.MIN_NORMAL)
        assertEquals(Half("-65504.0"), Half.LOWEST_VALUE)
    }

    @Test
    fun conversions() {
        assertEquals(12, 12.57.h.toByte())
        assertEquals(12, 12.57.h.toShort())
        assertEquals(12, 12.57.h.toInt())
        assertEquals(12, 12.57.h.toLong())
        assertEquals(12.57f, 12.57.h.toFloat(), 1e-3f)
        assertEquals(12.57, 12.57.h.toDouble(), 1e-3)

        assertEquals(-12, -12.57.h.toByte())
        assertEquals(-12, -12.57.h.toShort())
        assertEquals(-12, -12.57.h.toInt())
        assertEquals(-12, -12.57.h.toLong())
        assertEquals(-12.57f, -12.57.h.toFloat(), 1e-3f)
        assertEquals(-12.57, -12.57.h.toDouble(), 1e-3)

        assertEquals(0, Half.POSITIVE_ZERO.toByte())
        assertEquals(0, Half.POSITIVE_ZERO.toShort())
        assertEquals(0, Half.POSITIVE_ZERO.toInt())
        assertEquals(0, Half.POSITIVE_ZERO.toLong())
        assertEquals(+0.0f, Half.POSITIVE_ZERO.toFloat())
        assertEquals(+0.0, Half.POSITIVE_ZERO.toDouble())

        assertEquals(0, Half.NEGATIVE_ZERO.toByte())
        assertEquals(0, Half.NEGATIVE_ZERO.toShort())
        assertEquals(0, Half.NEGATIVE_ZERO.toInt())
        assertEquals(0, Half.NEGATIVE_ZERO.toLong())
        assertEquals(-0.0f, Half.NEGATIVE_ZERO.toFloat())
        assertEquals(-0.0, Half.NEGATIVE_ZERO.toDouble())

        assertEquals(-1, Half.POSITIVE_INFINITY.toByte())
        assertEquals(-1, Half.POSITIVE_INFINITY.toShort())
        assertEquals(Int.MAX_VALUE, Half.POSITIVE_INFINITY.toInt())
        assertEquals(Long.MAX_VALUE, Half.POSITIVE_INFINITY.toLong())
        assertEquals(Float.POSITIVE_INFINITY, Half.POSITIVE_INFINITY.toFloat())
        assertEquals(Double.POSITIVE_INFINITY, Half.POSITIVE_INFINITY.toDouble())

        assertEquals(0, Half.NEGATIVE_INFINITY.toByte())
        assertEquals(0, Half.NEGATIVE_INFINITY.toShort())
        assertEquals(Int.MIN_VALUE, Half.NEGATIVE_INFINITY.toInt())
        assertEquals(Long.MIN_VALUE, Half.NEGATIVE_INFINITY.toLong())
        assertEquals(Float.NEGATIVE_INFINITY, Half.NEGATIVE_INFINITY.toFloat())
        assertEquals(Double.NEGATIVE_INFINITY, Half.NEGATIVE_INFINITY.toDouble())

        assertEquals(0, Half.NaN.toByte())
        assertEquals(0, Half.NaN.toShort())
        assertEquals(0, Half.NaN.toInt())
        assertEquals(0, Half.NaN.toLong())
        assertEquals(Float.NaN.toBits(), Half.NaN.toFloat().toBits())
        assertEquals(Double.NaN.toBits(), Half.NaN.toDouble().toBits())
    }

    @Test
    fun min() {
        assertEquals(Half.NEGATIVE_INFINITY, min(Half.POSITIVE_INFINITY, Half.NEGATIVE_INFINITY))
        assertEquals(Half.NEGATIVE_ZERO, min(Half.POSITIVE_ZERO, Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, min(Half.NaN, Half.LOWEST_VALUE))
        assertEquals(Half.NaN, min(Half.LOWEST_VALUE, Half.NaN))
        assertEquals(Half.NEGATIVE_INFINITY, min(Half.NEGATIVE_INFINITY, Half.LOWEST_VALUE))
        assertEquals(Half.MAX_VALUE, min(Half.POSITIVE_INFINITY, Half.MAX_VALUE))
        assertEquals(Half.MIN_VALUE, min(Half.MIN_VALUE, Half.MIN_NORMAL))
        assertEquals(Half.POSITIVE_ZERO, min(Half.MIN_VALUE, Half.POSITIVE_ZERO))
        assertEquals(Half.POSITIVE_ZERO, min(Half.MIN_NORMAL, Half.POSITIVE_ZERO))
        assertEquals((-3.456).h, min((-3.456).h, (-3.453).h))
        assertEquals(3.453.h, min(3.456.h, 3.453.h))
    }

    @Test
    fun max() {
        assertEquals(Half.POSITIVE_INFINITY, max(Half.POSITIVE_INFINITY, Half.NEGATIVE_INFINITY))
        assertEquals(Half.POSITIVE_ZERO, max(Half.POSITIVE_ZERO, Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, max(Half.NaN, Half.MAX_VALUE))
        assertEquals(Half.NaN, max(Half.MAX_VALUE, Half.NaN))
        assertEquals(Half.LOWEST_VALUE, max(Half.NEGATIVE_INFINITY, Half.LOWEST_VALUE))
        assertEquals(Half.POSITIVE_INFINITY, max(Half.POSITIVE_INFINITY, Half.MAX_VALUE))
        assertEquals(Half.MIN_NORMAL, max(Half.MIN_VALUE, Half.MIN_NORMAL))
        assertEquals(Half.MIN_VALUE, max(Half.MIN_VALUE, Half.POSITIVE_ZERO))
        assertEquals(Half.MIN_NORMAL, max(Half.MIN_NORMAL, Half.POSITIVE_ZERO))
        assertEquals((-3.453).h, max((-3.456).h, (-3.453).h))
        assertEquals(3.456.h, max(3.456.h, 3.453.h))
    }

    @Test
    fun truncate() {
        assertEquals(Half.POSITIVE_INFINITY, truncate(Half.POSITIVE_INFINITY))
        assertEquals(Half.NEGATIVE_INFINITY, truncate(Half.NEGATIVE_INFINITY))
        assertEquals(Half.POSITIVE_ZERO, truncate(Half.POSITIVE_ZERO))
        assertEquals(Half.NEGATIVE_ZERO, truncate(Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, truncate(Half.NaN))
        assertEquals(Half.LOWEST_VALUE, truncate(Half.LOWEST_VALUE))
        assertEquals(Half.POSITIVE_ZERO, truncate(0.2.h))
        assertEquals(Half.NEGATIVE_ZERO, truncate((-0.2).h))
        assertEquals(0.0f, truncate(0.7.h).toFloat(), 1e-6f)
        assertEquals(-0.0f, truncate((-0.7).h).toFloat(), 1e-6f)
        assertEquals(124.0f, truncate(124.7.h).toFloat(), 1e-6f)
        assertEquals(-124.0f, truncate((-124.7).h).toFloat(), 1e-6f)
        assertEquals(124.0f, truncate(124.2.h).toFloat(), 1e-6f)
        assertEquals(-124.0f, truncate((-124.2).h).toFloat(), 1e-6f)
    }

    @Test
    fun round() {
        assertEquals(Half.POSITIVE_INFINITY, round(Half.POSITIVE_INFINITY))
        assertEquals(Half.NEGATIVE_INFINITY, round(Half.NEGATIVE_INFINITY))
        assertEquals(Half.POSITIVE_ZERO, round(Half.POSITIVE_ZERO))
        assertEquals(Half.NEGATIVE_ZERO, round(Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, round(Half.NaN))
        assertEquals(Half.LOWEST_VALUE, round(Half.LOWEST_VALUE))
        assertEquals(Half.POSITIVE_ZERO, round(Half.MIN_VALUE))
        assertEquals(Half.POSITIVE_ZERO, round(Half(0x200.toUShort())))
        assertEquals(Half.POSITIVE_ZERO, round(Half(0x3ff.toUShort())))
        assertEquals(Half.POSITIVE_ZERO, round(0.2.h))
        assertEquals(Half.NEGATIVE_ZERO, round((-0.2).h))
        assertEquals(1.0f, round(0.7.h).toFloat(), 1e-6f)
        assertEquals(-1.0f, round((-0.7).h).toFloat(), 1e-6f)
        assertEquals(1.0f, round(0.5.h).toFloat(), 1e-6f)
        assertEquals(-1.0f, round((-0.5).h).toFloat(), 1e-6f)
        assertEquals(2.0f, round(1.5.h).toFloat(), 1e-6f)
        assertEquals(-2.0f, round((-1.5).h).toFloat(), 1e-6f)
        assertEquals(1023.0f, round(1022.5.h).toFloat(), 1e-6f)
        assertEquals(-1023.0f, round((-1022.5).h).toFloat(), 1e-6f)
        assertEquals(125.0f, round(124.7.h).toFloat(), 1e-6f)
        assertEquals(-125.0f, round((-124.7).h).toFloat(), 1e-6f)
        assertEquals(124.0f, round(124.2.h).toFloat(), 1e-6f)
        assertEquals(-124.0f, round((-124.2).h).toFloat(), 1e-6f)
        // round for NaN values
        // These tests check whether the current round implementation achieves
        // bit level compatibility with the hardware implementation (ARM64).
        assertEquals(0x7e01, round(Half(0x7c01.toUShort())).toBits())
        assertEquals(0x7f00, round(Half(0x7d00.toUShort())).toBits())
        assertEquals(0xfe01, round(Half(0xfc01.toUShort())).toBits())
        assertEquals(0xff00, round(Half(0xfd00.toUShort())).toBits())
    }

    @Test
    fun floor() {
        assertEquals(Half.POSITIVE_INFINITY, floor(Half.POSITIVE_INFINITY))
        assertEquals(Half.NEGATIVE_INFINITY, floor(Half.NEGATIVE_INFINITY))
        assertEquals(Half.POSITIVE_ZERO, floor(Half.POSITIVE_ZERO))
        assertEquals(Half.NEGATIVE_ZERO, floor(Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, floor(Half.NaN))
        assertEquals(Half.LOWEST_VALUE, floor(Half.LOWEST_VALUE))
        assertEquals(Half.POSITIVE_ZERO, floor(Half.MIN_NORMAL))
        assertEquals(Half.POSITIVE_ZERO, floor(Half(0x3ff.toUShort())))
        assertEquals(Half.POSITIVE_ZERO, floor(0.2.h))
        assertEquals(-1.0f, floor((-0.2).h).toFloat(), 1e-6f)
        assertEquals(-1.0f, floor((-0.7).h).toFloat(), 1e-6f)
        assertEquals(Half.POSITIVE_ZERO, floor(0.7.h))
        assertEquals(124.0f, floor(124.7.h).toFloat(), 1e-6f)
        assertEquals(-125.0f, floor((-124.7).h).toFloat(), 1e-6f)
        assertEquals(124.0f, floor(124.2.h).toFloat(), 1e-6f)
        assertEquals(-125.0f, floor((-124.2).h).toFloat(), 1e-6f)
        // floor for NaN values
        assertEquals(0x7e01, floor(Half(0x7c01.toUShort())).toBits())
        assertEquals(0x7f00, floor(Half(0x7d00.toUShort())).toBits())
        assertEquals(0xfe01, floor(Half(0xfc01.toUShort())).toBits())
        assertEquals(0xff00, floor(Half(0xfd00.toUShort())).toBits())
    }

    @Test
    fun ceil() {
        assertEquals(Half.POSITIVE_INFINITY, ceil(Half.POSITIVE_INFINITY))
        assertEquals(Half.NEGATIVE_INFINITY, ceil(Half.NEGATIVE_INFINITY))
        assertEquals(Half.POSITIVE_ZERO, ceil(Half.POSITIVE_ZERO))
        assertEquals(Half.NEGATIVE_ZERO, ceil(Half.NEGATIVE_ZERO))
        assertEquals(Half.NaN, ceil(Half.NaN))
        assertEquals(Half.LOWEST_VALUE, ceil(Half.LOWEST_VALUE))
        assertEquals(1.0f, ceil(Half.MIN_NORMAL).toFloat(), 1e-6f)
        assertEquals(1.0f, ceil(Half(0x3ff.toUShort())).toFloat(), 1e-6f)
        assertEquals(1.0f, ceil(0.2.h).toFloat(), 1e-6f)
        assertEquals(Half.NEGATIVE_ZERO, ceil((-0.2).h))
        assertEquals(1.0f, ceil(0.7.h).toFloat(), 1e-6f)
        assertEquals(Half.NEGATIVE_ZERO, ceil((-0.7).h))
        assertEquals(125.0f, ceil(124.7.h).toFloat(), 1e-6f)
        assertEquals(-124.0f, ceil((-124.7).h).toFloat(), 1e-6f)
        assertEquals(125.0f, ceil(124.2.h).toFloat(), 1e-6f)
        assertEquals(-124.0f, ceil((-124.2).h).toFloat(), 1e-6f)
        // ceil for NaN values
        // These tests check whether the current ceil implementation achieves
        // bit level compatibility with the hardware implementation (ARM64).
        assertEquals(0x7f00, ceil(Half(0x7d00.toUShort())).toBits())
        assertEquals(0x7e01, ceil(Half(0x7c01.toUShort())).toBits())
        assertEquals(0xfe01, ceil(Half(0xfc01.toUShort())).toBits())
        assertEquals(0xff00, ceil(Half(0xfd00.toUShort())).toBits())
    }

    @Test
    fun compare() {
        assertEquals(0, Half.NaN.compareTo(Half.NaN))
        assertEquals(0, Half.NaN.compareTo(Half(0xfc98.toUShort()))) // Other NaN
        assertTrue(Half.NaN > Half.POSITIVE_INFINITY)
        assertTrue(Half.NaN > Half.NEGATIVE_INFINITY)
        assertTrue(Half.POSITIVE_INFINITY < Half.NaN)
        assertTrue(Half.NEGATIVE_INFINITY < Half.NaN)

        assertEquals(0, Half.POSITIVE_INFINITY.compareTo(Half.POSITIVE_INFINITY))
        assertEquals(0, Half.NEGATIVE_INFINITY.compareTo(Half.NEGATIVE_INFINITY))
        assertTrue(Half.POSITIVE_INFINITY > Half.NEGATIVE_INFINITY)
        assertTrue(Half.NEGATIVE_INFINITY < Half.POSITIVE_INFINITY)

        assertEquals(0, Half.POSITIVE_ZERO.compareTo(Half.POSITIVE_ZERO))
        assertEquals(0, Half.NEGATIVE_ZERO.compareTo(Half.NEGATIVE_ZERO))
        assertTrue(Half.POSITIVE_ZERO > Half.NEGATIVE_ZERO)
        assertTrue(Half.NEGATIVE_ZERO < Half.POSITIVE_ZERO)

        assertEquals(0, 12.462f.toHalf().compareTo(12.462f.toHalf()))
        assertTrue(12.462f.toHalf() <= 12.462f.toHalf())
        assertTrue(12.462f.toHalf() >= 12.462f.toHalf())
        assertTrue(12.462f.toHalf() > (-12.462f).toHalf())
        assertTrue((-12.462f).toHalf() < 12.462f.toHalf())
    }

    @Test
    fun next() {
        assertEquals(1025.0.h, 1024.0.h.nextUp())
        assertEquals(1023.5.h, 1024.0.h.nextDown())

        assertEquals(0.50048830.h, 0.5.h.nextUp())
        assertEquals(0.49975586.h, 0.5.h.nextDown())

        assertTrue(Half.NaN.nextUp().isNaN())
        assertTrue(Half.NaN.nextDown().isNaN())

        assertTrue(Half.POSITIVE_INFINITY.nextUp().isInfinite())
        assertEquals(Half.MAX_VALUE, Half.POSITIVE_INFINITY.nextDown())

        assertTrue(Half.NEGATIVE_INFINITY.nextDown().isInfinite())
        assertEquals(-Half.MAX_VALUE, Half.NEGATIVE_INFINITY.nextUp())

        assertEquals(Half.MIN_VALUE, Half.POSITIVE_ZERO.nextUp())
        assertEquals(-Half.MIN_VALUE, Half.POSITIVE_ZERO.nextDown())

        assertEquals(Half.MIN_VALUE, Half.NEGATIVE_ZERO.nextUp())
        assertEquals(-Half.MIN_VALUE, Half.NEGATIVE_ZERO.nextDown())

        assertTrue(Half.NaN.nextTowards(HALF_TWO).isNaN())
        assertTrue(HALF_TWO.nextTowards(Half.NaN).isNaN())
        assertEquals(HALF_ONE, HALF_ONE.nextTowards(HALF_ONE))
        assertEquals(-HALF_ONE, (-HALF_ONE).nextTowards(-HALF_ONE))

        assertEquals(1025.0.h, 1024.0.h.nextTowards(32768.0.h))
        assertEquals(1023.5.h, 1024.0.h.nextTowards((-32768.0).h))

        assertEquals(0.50048830.h, 0.5.h.nextTowards(32768.0.h))
        assertEquals(0.49975586.h, 0.5.h.nextTowards((-32768.0).h))
    }

    @Test
    fun unaryOperators() {
        for (i in 0x0..0xffff) {
            val v = Half(i.toUShort())
            assertEquals((+v).toBits(), v.toBits())
        }

        for (i in 0x0..0xffff) {
            val v1 = Half(i.toUShort())
            val v2 = -v1
            when {
                v1.isNaN() -> assertTrue(v2.isNaN())
                v1.isZero() -> assertTrue(v2.isZero())
                else -> assertNotEquals(v2.sign, v1.sign)
            }
            assertEquals(v2.exponent, v1.exponent)
            assertEquals(v2.significand, v1.significand)
        }
    }

    @Test
    fun multiplication() {
        assertTrue((HALF_TWO * Half.NaN).isNaN())
        assertTrue((Half.NaN * HALF_TWO).isNaN())
        assertTrue((Half.POSITIVE_INFINITY * Half.NaN).isNaN())
        assertTrue((Half.NaN * Half.POSITIVE_INFINITY).isNaN())
        assertTrue((Half.NEGATIVE_INFINITY * Half.NaN).isNaN())
        assertTrue((Half.NaN * Half.NEGATIVE_INFINITY).isNaN())
        assertTrue((Half.POSITIVE_ZERO * Half.NaN).isNaN())
        assertTrue((Half.NaN * Half.POSITIVE_ZERO).isNaN())
        assertTrue((Half.NEGATIVE_ZERO * Half.NaN).isNaN())
        assertTrue((Half.NaN * Half.NEGATIVE_ZERO).isNaN())

        assertTrue((HALF_TWO * Half.POSITIVE_INFINITY).isInfinite())
        assertTrue((Half.POSITIVE_INFINITY * HALF_TWO).isInfinite())

        assertTrue((HALF_TWO * Half.NEGATIVE_INFINITY).isInfinite())
        assertTrue((Half.NEGATIVE_INFINITY * HALF_TWO).isInfinite())

        assertTrue((HALF_TWO * Half.POSITIVE_ZERO).isZero())
        assertTrue((Half.POSITIVE_ZERO * HALF_TWO).isZero())

        assertTrue((HALF_TWO * Half.NEGATIVE_ZERO).isZero())
        assertTrue((Half.NEGATIVE_ZERO * HALF_TWO).isZero())

        // Overflow
        assertEquals(Half.POSITIVE_INFINITY, HALF_TWO * Half.MAX_VALUE)
        assertEquals(Half.POSITIVE_INFINITY, Half.MAX_VALUE * HALF_TWO)
        assertEquals(Half.NEGATIVE_INFINITY, (-2.0).h * Half.MAX_VALUE)
        assertEquals(Half.NEGATIVE_INFINITY, Half.MAX_VALUE * (-2.0).h)

        // Underflow
        assertEquals(Half.POSITIVE_ZERO, Half.MIN_VALUE * Half.MIN_NORMAL)
        assertEquals(Half.NEGATIVE_ZERO, Half.MIN_VALUE * -Half.MIN_NORMAL)

        assertEquals(8.0.h, HALF_TWO * 4.0.h)
        assertEquals(2.88.h, 1.2.h * 2.4.h)
        assertEquals((-2.88).h, 1.2.h * (-2.4).h)
        assertEquals((-2.88).h, (-1.2).h * 2.4.h)
        assertEquals(2.88.h, (-1.2).h * (-2.4).h)

        assertEquals(48_000.0.h, 3.0.h * 16_000.0.h)
        assertEquals((-48_000.0).h, 3.0.h * (-16_000.0).h)

        assertEquals(0.000012.h, 0.03.h * 0.0004.h)
        assertEquals((-0.000012).h, 0.03.h * (-0.0004).h)
    }

    @Test
    fun division() {
        assertTrue((HALF_TWO / Half.NaN).isNaN())
        assertTrue((Half.NaN / HALF_TWO).isNaN())
        assertTrue((Half.POSITIVE_INFINITY / Half.NaN).isNaN())
        assertTrue((Half.NaN / Half.POSITIVE_INFINITY).isNaN())
        assertTrue((Half.NEGATIVE_INFINITY / Half.NaN).isNaN())
        assertTrue((Half.NaN / Half.NEGATIVE_INFINITY).isNaN())
        assertTrue((Half.POSITIVE_ZERO / Half.NaN).isNaN())
        assertTrue((Half.NaN / Half.POSITIVE_ZERO).isNaN())
        assertTrue((Half.NEGATIVE_ZERO / Half.NaN).isNaN())
        assertTrue((Half.NaN / Half.NEGATIVE_ZERO).isNaN())

        assertTrue((HALF_TWO / Half.POSITIVE_INFINITY).isZero())
        assertTrue((Half.POSITIVE_INFINITY / HALF_TWO).isInfinite())

        assertTrue((HALF_TWO / Half.NEGATIVE_INFINITY).isZero())
        assertTrue((Half.NEGATIVE_INFINITY / HALF_TWO).isInfinite())

        assertTrue((HALF_TWO / Half.POSITIVE_ZERO).isInfinite())
        assertTrue((Half.POSITIVE_ZERO / HALF_TWO).isZero())

        assertTrue((HALF_TWO / Half.NEGATIVE_ZERO).isInfinite())
        assertTrue((Half.NEGATIVE_ZERO / HALF_TWO).isZero())

        // Underflow
        assertEquals(Half.POSITIVE_ZERO, Half.MIN_VALUE / Half.MAX_VALUE)
        assertEquals(Half.NEGATIVE_ZERO, (-Half.MIN_VALUE) / Half.MAX_VALUE)

        // Overflow
        assertEquals(Half.POSITIVE_INFINITY, Half.MAX_VALUE / Half.MIN_VALUE)
        assertEquals(Half.NEGATIVE_INFINITY, (-Half.MAX_VALUE) / Half.MIN_VALUE)

        assertEquals(0.5.h, HALF_TWO / 4.0.h)
        assertEquals(0.5.h, 1.2.h / 2.4.h)
        assertEquals((-0.5).h, 1.2.h / (-2.4).h)
        assertEquals((-0.5).h, (-1.2).h / 2.4.h)
        assertEquals(0.5.h, (-1.2).h / (-2.4).h)

        assertEquals(16_000.0.h, 48_000.0.h / 3.0.h)
        assertEquals((-16_000.0).h, 48_000.0.h / (-3.0).h)

        assertEquals(Half(2.0861626e-5f), HALF_ONE / 48_000.0.h)
        assertEquals(Half(-2.0861626e-5), HALF_ONE / (-48_000.0).h)

        assertEquals(75.0.h, 0.03.h / 0.0004.h)
        assertEquals((-75.0).h, 0.03.h / (-0.0004).h)
    }

    @Test
    fun addition() {
        assertTrue((Half.NaN + Half.NaN).isNaN())

        assertTrue((Half.NaN + HALF_ONE).isNaN())
        assertTrue((Half.NaN - HALF_ONE).isNaN())
        assertTrue((HALF_ONE + Half.NaN).isNaN())
        assertTrue(((-1.0).h + Half.NaN).isNaN())

        assertTrue((Half.NaN + Half.POSITIVE_INFINITY).isNaN())
        assertTrue((Half.POSITIVE_INFINITY + Half.NaN).isNaN())
        assertTrue((Half.NaN + Half.NEGATIVE_INFINITY).isNaN())
        assertTrue((Half.NEGATIVE_INFINITY + Half.NaN).isNaN())

        assertTrue((Half.NaN + Half.POSITIVE_ZERO).isNaN())
        assertTrue((Half.POSITIVE_ZERO + Half.NaN).isNaN())
        assertTrue((Half.NaN + Half.NEGATIVE_ZERO).isNaN())
        assertTrue((Half.NEGATIVE_ZERO + Half.NaN).isNaN())

        assertTrue((Half.POSITIVE_INFINITY + HALF_ONE).isInfinite())
        assertTrue((Half.POSITIVE_INFINITY - HALF_ONE).isInfinite())
        assertTrue((HALF_ONE + Half.POSITIVE_INFINITY).isInfinite())
        assertTrue((HALF_ONE - Half.POSITIVE_INFINITY).isInfinite())
        assertTrue((Half.POSITIVE_INFINITY + Half.POSITIVE_INFINITY).isInfinite())
        assertTrue((Half.POSITIVE_INFINITY - Half.POSITIVE_INFINITY).isNaN())

        assertTrue((Half.NEGATIVE_INFINITY - HALF_ONE).isInfinite())
        assertTrue((Half.NEGATIVE_INFINITY + HALF_ONE).isInfinite())
        assertTrue((HALF_ONE + Half.NEGATIVE_INFINITY).isInfinite())
        assertTrue((HALF_ONE - Half.NEGATIVE_INFINITY).isInfinite())
        assertTrue((Half.NEGATIVE_INFINITY + Half.NEGATIVE_INFINITY).isInfinite())
        assertTrue((Half.NEGATIVE_INFINITY - Half.NEGATIVE_INFINITY).isNaN())

        assertEquals(3.0.h, HALF_ONE + HALF_TWO)

        // Overflow
        assertEquals(Half.POSITIVE_INFINITY, 32768.0.h + 32768.0.h)
        // Underflow
        assertEquals(Half.NEGATIVE_INFINITY, (-32768.0).h - 32768.0.h)

        for (i in 0x0..0xffff) {
            val v1 = Half(i.toUShort())
            if (v1.isFinite()) {
                assertTrue((v1 - v1).isZero())
                assertEquals(v1 * HALF_TWO, v1 + v1)
            }
        }
    }

    @Test
    fun ulp() {
        assertTrue(Half.NaN.ulp.isNaN())

        assertTrue(Half.POSITIVE_INFINITY.ulp.isInfinite())
        assertTrue(Half.NEGATIVE_INFINITY.ulp.isInfinite())

        assertTrue((Half.MAX_VALUE + Half.MAX_VALUE.ulp).isInfinite())

        assertEquals(Half.MIN_VALUE, Half.POSITIVE_ZERO.ulp)
        assertEquals(Half.MIN_VALUE, Half.NEGATIVE_ZERO.ulp)

        assertEquals(HALF_ONE, 1024.0.h.ulp)
        assertEquals(HALF_ONE, (-1024.0).h.ulp)
    }

    @Test
    fun sqrt() {
        for (i in 0x0..0xffff) {
            val v1 = Half(i.toUShort())
            if (v1.isFinite()) {
                val v2 = sqrt(v1)
                assertTrue(v1 - (v2 * v2) <= HALF_TWO * v1.ulp)
            }
        }
    }
}
