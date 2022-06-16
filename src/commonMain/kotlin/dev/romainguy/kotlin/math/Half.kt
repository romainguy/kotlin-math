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

import dev.romainguy.kotlin.math.Half.Companion.POSITIVE_INFINITY
import dev.romainguy.kotlin.math.Half.Companion.POSITIVE_ZERO
import kotlin.jvm.JvmInline

/**
 * Converts the specified double-precision float value into a
 * half-precision float value. The following special cases are handled:
 *
 *  - If the input is NaN (see [Double.isNaN]), the returned value is [Half.NaN]
 *  - If the input is [Double.POSITIVE_INFINITY] or [Double.NEGATIVE_INFINITY],
 *  the returned value is respectively [Half.POSITIVE_INFINITY] or [Half.NEGATIVE_INFINITY]
 *  - If the input is 0 (positive or negative), the returned value is [Half.POSITIVE_ZERO]
 *  or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_VALUE], the returned value is flushed to
 *  [Half.POSITIVE_ZERO] or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_NORMAL], the returned value is a denormal
 *  half-precision float
 *  - Otherwise, the returned value is rounded to the nearest representable
 *  half-precision float value
 *
 * @param value The double-precision float value to convert to half-precision
 * @return A half-precision float value
 */
fun Half(value: Double) = Half(floatToHalf(value.toFloat()))

/**
 * Converts this double-precision float value into a half-precision float value.
 * The following special cases are handled:
 *
 *  - If the input is NaN (see [Double.isNaN]), the returned value is [Half.NaN]
 *  - If the input is [Double.POSITIVE_INFINITY] or [Double.NEGATIVE_INFINITY],
 *  the returned value is respectively [Half.POSITIVE_INFINITY] or [Half.NEGATIVE_INFINITY]
 *  - If the input is 0 (positive or negative), the returned value is [Half.POSITIVE_ZERO]
 *  or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_VALUE], the returned value is flushed to
 *  [Half.POSITIVE_ZERO] or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_NORMAL], the returned value is a denormal
 *  half-precision float
 *  - Otherwise, the returned value is rounded to the nearest representable
 *  half-precision float value
 *
 * @return A half-precision float value
 */
fun Double.toHalf() = Half(floatToHalf(toFloat()))

/**
 * Converts the specified single-precision float value into a
 * half-precision float value. The following special cases are handled:
 *
 *  - If the input is NaN (see [Float.isNaN]), the returned value is [Half.NaN]
 *  - If the input is [Float.POSITIVE_INFINITY] or [Float.NEGATIVE_INFINITY],
 *  the returned value is respectively [Half.POSITIVE_INFINITY] or [Half.NEGATIVE_INFINITY]
 *  - If the input is 0 (positive or negative), the returned value is [Half.POSITIVE_ZERO]
 *  or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_VALUE], the returned value is flushed to
 *  [Half.POSITIVE_ZERO] or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_NORMAL], the returned value is a denormal
 *  half-precision float
 *  - Otherwise, the returned value is rounded to the nearest representable
 *  half-precision float value
 *
 * @param value The single-precision float value to convert to half-precision
 * @return A half-precision float value
 */
fun Half(value: Float) = Half(floatToHalf(value))

/**
 * Converts this single-precision float value into a half-precision float value.
 * The following special cases are handled:
 *
 *  - If the input is NaN (see [Float.isNaN]), the returned value is [Half.NaN]
 *  - If the input is [Float.POSITIVE_INFINITY] or [Float.NEGATIVE_INFINITY],
 *  the returned value is respectively [Half.POSITIVE_INFINITY] or [Half.NEGATIVE_INFINITY]
 *  - If the input is 0 (positive or negative), the returned value is [Half.POSITIVE_ZERO]
 *  or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_VALUE], the returned value is flushed to
 *  [Half.POSITIVE_ZERO] or [Half.NEGATIVE_ZERO]
 *  - If the input is less than [Half.MIN_NORMAL], the returned value is a denormal
 *  half-precision float
 *  - Otherwise, the returned value is rounded to the nearest representable
 *  half-precision float value
 *
 * @return A half-precision float value
 */
fun Float.toHalf() = Half(floatToHalf(this))

/**
 * Returns the half-precision float value represented by the specified string.
 * The string is converted to a half-precision float value as if by the
 * [String.toFloat()] method.</p>
 *
 * Calling this function is equivalent to calling:
 * ```
 * Half(value.toFloat())
 * ```
 *
 * @param value A string to be converted to a {@code Half}
 * @throws NumberFormatException if the string does not contain a parsable number
 *
 * @see String.toFloat
 */
fun Half(value: String) = Half(floatToHalf(value.toFloat()))

/**
 * Returns the half-precision float value represented by the specified string.
 * The string is converted to a half-precision float value as if by the
 * [String.toFloat()] method.</p>
 *
 * Calling this function is equivalent to calling:
 * ```
 * Half(value.toFloat())
 * ```
 *
 * @throws NumberFormatException if the string does not contain a parsable number
 *
 * @see String.toFloat
 */
fun String.toHalf() = Half(floatToHalf(toFloat()))

private const val FP16_SIGN_SHIFT       = 15
private const val FP16_SIGN_MASK        = 0x8000
private const val FP16_EXPONENT_SHIFT   = 10
private const val FP16_EXPONENT_MASK    = 0x1f
private const val FP16_SIGNIFICAND_MASK = 0x3ff
private const val FP16_EXPONENT_BIAS    = 15
private const val FP16_COMBINED         = 0x7fff
private const val FP16_EXPONENT_MAX     = 0x7c00
private const val FP32_SIGN_SHIFT       = 31
private const val FP32_EXPONENT_SHIFT   = 23
private const val FP32_EXPONENT_MASK    = 0xff
private const val FP32_SIGNIFICAND_MASK = 0x7fffff
private const val FP32_EXPONENT_BIAS    = 127
private const val FP32_QNAN_MASK        = 0x400000
private const val FP32_DENORMAL_MAGIC   = 126 shl 23
private       val FP32_DENORMAL_FLOAT   = Float.fromBits(FP32_DENORMAL_MAGIC)

/**
 * The [Half] class is a wrapper and a utility class to manipulate half-precision 16-bit
 * [IEEE 754](https://en.wikipedia.org/wiki/Half-precision_floating-point_format)
 * floating point data types (also called fp16 or binary16). A half-precision float can be
 * created from or converted to single-precision floats, and is stored in a short data type.
 *
 * The IEEE 754 standard specifies an fp16 as having the following format:
 *   - Sign bit: 1 bit
 *   - Exponent width: 5 bits
 *   - Significand: 10 bits
 *
 * The format is laid out as follows:
 * ```
 * 1   11111   1111111111
 * ^   --^--   -----^----
 * sign  |          |_______ significand
 *       |
 *       -- exponent
 * ```
 *
 * Half-precision floating points can be useful to save memory and/or
 * bandwidth at the expense of range and precision when compared to single-precision
 * floating points (fp32).
 *
 * To help you decide whether fp16 is the right storage type for you need, please
 * refer to the table below that shows the available precision throughout the range of
 * possible values. The _precision_ column indicates the step size between two
 * consecutive numbers in a specific part of the range.
 *
 * | Range start      | Precision            |
 * |------------------|----------------------|
 * | 0                | 1 &frasl; 16,777,216 |
 * | 1 &frasl; 16,384 | 1 &frasl; 16,777,216 |
 * | 1 &frasl; 8,192  | 1 &frasl; 8,388,608  |
 * | 1 &frasl; 4,096  | 1 &frasl; 4,194,304  |
 * | 1 &frasl; 2,048  | 1 &frasl; 2,097,152  |
 * | 1 &frasl; 1,024  | 1 &frasl; 1,048,576  |
 * | 1 &frasl; 512    | 1 &frasl; 524,288    |
 * | 1 &frasl; 256    | 1 &frasl; 262,144    |
 * | 1 &frasl; 128    | 1 &frasl; 131,072    |
 * | 1 &frasl; 64     | 1 &frasl; 65,536     |
 * | 1 &frasl; 32     | 1 &frasl; 32,768     |
 * | 1 &frasl; 16     | 1 &frasl; 16,384     |
 * | 1 &frasl; 8      | 1 &frasl; 8,192      |
 * | 1 &frasl; 4      | 1 &frasl; 4,096      |
 * | 1 &frasl; 2      | 1 &frasl; 2,048      |
 * | 1                | 1 &frasl; 1,024      |
 * | 2                | 1 &frasl; 512        |
 * | 4                | 1 &frasl; 256        |
 * | 8                | 1 &frasl; 128        |
 * | 16               | 1 &frasl; 64         |
 * | 32               | 1 &frasl; 32         |
 * | 64               | 1 &frasl; 16         |
 * | 128              | 1 &frasl; 8          |
 * | 256              | 1 &frasl; 4          |
 * | 512              | 1 &frasl; 2          |
 * | 1,024            | 1                    |
 * | 2,048            | 2                    |
 * | 4,096            | 4                    |
 * | 8,192            | 8                    |
 * | 16,384           | 16                   |
 * | 32,768           | 32                   |
 *
 * This table shows that numbers higher than 1024 lose all fractional precision.
 */
@JvmInline
value class Half(private val v: UShort) : Comparable<Half> {
    companion object {
        /**
         * The number of bits used to represent a half-precision float value.
         */
        const val SIZE = 16

        /**
         * Epsilon is the difference between 1.0 and the next value representable
         * by a half-precision floating-point.
         */
        val EPSILON = Half(0x1400.toUShort())

        /**
         * Maximum exponent a finite half-precision float may have.
         */
        const val MAX_EXPONENT = 15

        /**
         * Minimum exponent a normalized half-precision float may have.
         */
        const val MIN_EXPONENT = -14

        /**
         * Smallest negative value a half-precision float may have.
         */
        val LOWEST_VALUE = Half(0xfbff.toUShort())

        /**
         * Maximum positive finite value a half-precision float may have.
         */
        val MAX_VALUE = Half(0x7bff.toUShort())

        /**
         * Smallest positive normal value a half-precision float may have.
         */
        val MIN_NORMAL = Half(0x0400.toUShort())

        /**
         * Smallest positive non-zero value a half-precision float may have.
         */
        val MIN_VALUE = Half(0x0001.toUShort())

        /**
         * A Not-a-Number representation of a half-precision float.
         */
        val NaN = Half(0x7e00.toUShort())

        /**
         * Negative infinity of type half-precision float.
         */
        val NEGATIVE_INFINITY = Half(0xfc00.toUShort())

        /**
         * Negative 0 of type half-precision float.
         */
        val NEGATIVE_ZERO = Half(0x8000.toUShort())

        /**
         * Positive infinity of type half-precision float.
         */
        val POSITIVE_INFINITY = Half(0x7c00.toUShort())

        /**
         * Positive 0 of type half-precision float.
         */
        val POSITIVE_ZERO = Half(0x0000.toUShort())

        /**
         * Returns the [Half] value corresponding to the given bit representation according to
         * the IEEE 754 floating-point half-precision bit layout.
         */
        fun fromBits(bits: Int) = Half((bits and 0xffff).toUShort())
    }

    /**
     * Returns the sign of this half-precision float value:
     * - `Half(-1.0)` if the value is negative,
     * - [POSITIVE_ZERO] if the value is zero,
     * - `Half(1.0)` if the value is positive
     * - [NaN] is the value is Not-a-Number
     */
    val sign: Half
        get() {
            val bits = v.toInt()
            val abs = bits and FP16_COMBINED
            return when {
                abs > FP16_EXPONENT_MAX -> NaN
                abs == 0 -> POSITIVE_ZERO
                else -> if (bits and FP16_SIGN_MASK != 0) Half(-1.0f) else Half(1.0f)
            }
        }

    /**
     * Returns the unbiased exponent used in the representation of this half-precision float value.
     * if the value is NaN or infinite, this method returns [MAX_EXPONENT] + 1.
     * If the argument is 0 or a subnormal representation, this method returns [MIN_EXPONENT] - 1.
     */
    val exponent: Int
        get() = ((v.toInt() ushr FP16_EXPONENT_SHIFT) and FP16_EXPONENT_MASK) - FP16_EXPONENT_BIAS

    /**
     * Returns the significand, or mantissa, used in the representation of this
     * half-precision float value.
     */
    val significand: Int
        get() = v.toInt() and FP16_SIGNIFICAND_MASK

    /**
     * Returns the absolute value of this half-precision float.
     * Special values are handled in the following ways:
     * - If the specified half-precision float is NaN, the result is NaN
     * - If the specified half-precision float is zero (negative or positive),
     * the result is positive zero (see [POSITIVE_ZERO])
     * - If the specified half-precision float is infinity (negative or positive),
     * the result is positive infinity (see [POSITIVE_INFINITY])
     *
     * @return The absolute value of the specified half-precision float
     */
    val absoluteValue: Half
        get() = Half((v.toInt() and FP16_COMBINED).toUShort())

    /**
     * Returns the ulp of this value.
     * An ulp is a positive distance between this value and the next nearest [Half] value
     * larger in magnitude.
     *
     * Special Cases:
     * - `NaN.ulp` is [NaN]
     * - `x.ulp` is [POSITIVE_INFINITY] when x is [POSITIVE_INFINITY] or [NEGATIVE_INFINITY]
     * - `0.0.ulp` is [MIN_VALUE]
     */
    val ulp: Half
        get() = when {
            isNaN() -> NaN
            isInfinite() -> POSITIVE_INFINITY
            // 0x7bff == MAX_VALUE
            v.toInt() and FP16_COMBINED == 0x7bff -> Half(0x4c00.toUShort())
            else -> {
                val d = absoluteValue
                d.nextUp() - d
            }
        }

    /**
     * Returns a bit representation of this half-precision floating point value as [Int]
     * according to the IEEE 754 floating-point half-precision bit layout.
     */
    fun toBits() = v.toInt()

    /**
     * Returns the value of this [Half] as a `byte` after a narrowing primitive conversion.
     *
     * @return The half-precision float value represented by this object converted to type `byte`
     * @see halfToShort
     */
    fun toByte() = halfToShort(v).toInt().toByte()

    /**
     * Returns the value of this [Half] as a `short` after a narrowing primitive conversion.
     *
     * @return The half-precision float value represented by this object converted to type `short`
     * @see halfToShort
     */
    fun toShort() = halfToShort(v).toInt().toShort()

    /**
     * Returns the value of this [Half] as a `int` after a narrowing primitive conversion.
     *
     * @return The half-precision float value represented by this object converted to type `int`
     * @see halfToShort
     * */
    fun toInt() = halfToShort(v).toInt()

    /**
     * Returns the value of this [Half] as a `long` after a narrowing primitive conversion.
     *
     * @return The half-precision float value represented by this object converted to type `long`
     * @see halfToShort
     */
    fun toLong() = halfToShort(v).toLong()

    /**
     * Returns the value of this [Half] as a `float` after a widening primitive conversion.
     *
     * The following special cases are handled:
     * - If the input is [Half.NaN], the returned value is [Float.NaN]
     * - If the input is [Half.POSITIVE_INFINITY] or
     * [Half.NEGATIVE_INFINITY], the returned value is respectively [Float.POSITIVE_INFINITY]
     * or [Float.NEGATIVE_INFINITY]
     * - If the input is 0 (positive or negative), the returned value is +/-0.0f
     * - Otherwise, the returned value is a normalized single-precision float value
     *
     * @return The half-precision float value represented by this object converted to type `float`
     */
    fun toFloat() = halfToShort(v)

    /**
     * Returns the value of this [Half] as a `double` after a widening primitive conversion.
     *
     * The following special cases are handled:
     * - If the input is [Half.NaN], the returned value is [Double.NaN]
     * - If the input is [Half.POSITIVE_INFINITY] or
     * [Half.NEGATIVE_INFINITY], the returned value is respectively [Double.POSITIVE_INFINITY]
     * or [Double.NEGATIVE_INFINITY]
     * - If the input is 0 (positive or negative), the returned value is +/-0.0f
     * - Otherwise, the returned value is a normalized double-precision float value
     *
     * @return The half-precision float value represented by this object converted to type `double`
     */
    fun toDouble() = halfToShort(v).toDouble()

    /**
     * Returns true if this half-precision float value represents a Not-a-Number, false otherwise.
     */
    fun isNaN() = (v.toInt() and FP16_COMBINED) > FP16_EXPONENT_MAX

    /**
     * Returns true if this half-precision float value represents infinity, false otherwise.
     */
    fun isInfinite() = (v.toInt() and FP16_COMBINED) == FP16_EXPONENT_MAX

    /**
     * Returns true if this half-precision float value does not represent infinity nor NaN,
     * false otherwise.
     */
    fun isFinite() = (v.toInt() and FP16_EXPONENT_MAX) != FP16_EXPONENT_MAX

    /**
     * Returns true if this half-precision float value represents zero, false otherwise.
     */
    fun isZero() = (v.toInt() and FP16_COMBINED) == 0

    /**
     * Returns true if this half-precision float value is normalized (does not have a subnormal
     * representation). If this value is [Half.POSITIVE_INFINITY], [Half.NEGATIVE_INFINITY],
     * [Half.POSITIVE_ZERO], [Half.NEGATIVE_ZERO], NaN or any subnormal number, this method
     * returns false.
     *
     * @return True if the value is normalized, false otherwise
     */
    fun isNormalized() = (v.toInt() and FP16_EXPONENT_MAX) != 0
        && (v.toInt() and FP16_EXPONENT_MAX) != FP16_EXPONENT_MAX

    /**
     * Returns this value with the sign bit same as of the [sign] value.
     * If [sign] is NaN the sign of the result is undefined.
     */
    fun withSign(sign: Half) =
        Half(((sign.v.toInt() and FP16_SIGN_MASK) or (v.toInt() and FP16_COMBINED)).toUShort())

    /**
     * Returns the [Half] value nearest to this value in direction of positive infinity.
     */
    fun nextUp(): Half = when {
        isNaN() || v == POSITIVE_INFINITY.v -> this
        v == POSITIVE_ZERO.v -> MIN_VALUE
        else -> fromBits(toBits() + if (v.toInt() and FP16_SIGN_MASK == 0) 1 else -1)
    }

    /**
     * Returns the [Half] value nearest to this value in direction of negative infinity.
     */
    fun nextDown(): Half = when {
        isNaN() || v == NEGATIVE_INFINITY.v -> this
        v == POSITIVE_ZERO.v -> -MIN_VALUE
        else -> fromBits(toBits() + if (v.toInt() and FP16_SIGN_MASK == 0) -1 else 1)
    }

    /**
     * Returns the [Half] value nearest to this value in direction from this value towards
     * the value [to].
     *
     * Special cases:
     *   - `x.nextTowards(y)` is `NaN` if either `x` or `y` are `NaN`
     *   - `x.nextTowards(x) == x`
     */
    fun nextTowards(to: Half) = when {
        isNaN() || to.isNaN() -> NaN
        to == this -> this
        to > this -> nextUp()
        else -> nextDown()
    }

    /**
     * Rounds this [Half] value to the nearest integer and converts the result to [Int].
     * Ties are rounded towards positive infinity.
     *
     * @throws IllegalArgumentException when this value is `NaN`
     */
    fun roundToInt() = when {
        isNaN() -> throw IllegalArgumentException("Cannot round NaN value.")
        else -> round(this).toInt()
    }

    /**
     * Rounds this [Half] value to the nearest integer and converts the result to [Long].
     * Ties are rounded towards positive infinity.
     *
     * @throws IllegalArgumentException when this value is `NaN`
     */
    fun roundToLong() = when {
        isNaN() -> throw IllegalArgumentException("Cannot round NaN value.")
        else -> round(this).toLong()
    }

    operator fun unaryMinus() = Half((v.toInt() xor FP16_SIGN_MASK).toUShort())

    operator fun unaryPlus() = Half(v)

    operator fun plus(other: Half): Half {
        TODO("Not yet implemented")
    }

    operator fun minus(other: Half) = this + (-other)

    operator fun times(other: Half): Half {
        TODO("Not yet implemented")
    }

    operator fun div(other: Half): Half {
        TODO("Not yet implemented")
    }

    operator fun inc() = this + Half(0x3c00.toUShort())

    operator fun dec() = this + Half(0xbc00.toUShort())

    override fun compareTo(other: Half): Int {
        // Preserve the sign for comparisons later
        var x = v.toShort().toInt()
        var y = other.v.toShort().toInt()

        // Collapse NaNs, but we want to keep (signed) values to preserve the ordering of
        // -0.0 and +0.0
        if (x and FP16_COMBINED > FP16_EXPONENT_MAX) x = 0x7e00 // NaN
        if (y and FP16_COMBINED > FP16_EXPONENT_MAX) y = 0x7e00 // NaN

        if (x == y) return 0

        val a = (x xor (FP16_SIGN_MASK or (FP16_SIGN_MASK - (x shr 15)))) + (x shr 15)
        val b = (y xor (FP16_SIGN_MASK or (FP16_SIGN_MASK - (y shr 15)))) + (y shr 15)

        return if (a < b) -1 else 1
    }

    override fun toString() = toFloat().toString()

    /**
     *
     * Returns a hexadecimal string representation of the specified half-precision
     * float value. If the value is a NaN, the result is `"NaN"`,
     * otherwise the result follows this format:
     *
     * - If the sign is positive, no sign character appears in the result
     * - If the sign is negative, the first character is `'-'`
     * - If the value is inifinity, the string is `"Infinity"`
     * - If the value is 0, the string is `"0x0.0p0"`
     * - If the value has a normalized representation, the exponent and
     * significand are represented in the string in two fields. The significand
     * starts with `"0x1."` followed by its lowercase hexadecimal
     * representation. Trailing zeroes are removed unless all digits are 0, then
     * a single zero is used. The significand representation is followed by the
     * exponent, represented by `"p"`, itself followed by a decimal
     * string of the unbiased exponent
     * - If the value has a subnormal representation, the significand starts
     * with `"0x0."` followed by its lowercase hexadecimal
     * representation. Trailing zeroes are removed unless all digits are 0, then
     * a single zero is used. The significand representation is followed by the
     * exponent, represented by `"p-14"`
     *
     * @return A hexadecimal string representation of the specified value
     */
    fun toHexString(): String {
        val o = StringBuilder()
        val bits = v.toInt()
        val s = bits ushr FP16_SIGN_SHIFT
        val e = bits ushr FP16_EXPONENT_SHIFT and FP16_EXPONENT_MASK
        val m = bits and FP16_SIGNIFICAND_MASK
        if (e == 0x1f) { // Infinite or NaN
            if (m == 0) {
                if (s != 0) o.append('-')
                o.append("Infinity")
            } else {
                o.append("NaN")
            }
        } else {
            if (s == 1) o.append('-')
            if (e == 0) {
                if (m == 0) {
                    o.append("0x0.0p0")
                } else {
                    o.append("0x0.")
                    val significand = m.toString(16)
                    o.append(significand.replaceFirst("0{2,}$".toRegex(), ""))
                    o.append("p-14")
                }
            } else {
                o.append("0x1.")
                val significand = m.toString(16)
                o.append(significand.replaceFirst("0{2,}$".toRegex(), ""))
                o.append('p')
                o.append((e - FP16_EXPONENT_BIAS).toString())
            }
        }
        return o.toString()
    }
}

/**
 * Returns the absolute value of the specified half-precision float.
 * Special values are handled in the following ways:
 * - If the specified half-precision float is NaN, the result is NaN
 * - If the specified half-precision float is zero (negative or positive),
 * the result is positive zero (see [POSITIVE_ZERO])
 * - If the specified half-precision float is infinity (negative or positive),
 * the result is positive infinity (see [POSITIVE_INFINITY])
 *
 * @return The absolute value of the specified half-precision float
 */
fun abs(x: Half) = x.absoluteValue

/**
 * Returns the smaller of two half-precision float values (the value closest
 * to negative infinity). Special values are handled in the following ways:
 *
 * - If either value is NaN, the result is NaN
 * - [Half.NEGATIVE_ZERO] is smaller than [Half.POSITIVE_ZERO]
 *
 * @param x The first half-precision value
 * @param y The second half-precision value
 * @return The smaller of the two specified half-precision values
 */
fun min(x: Half, y: Half): Half {
    val a = x.toBits()
    if (a and FP16_COMBINED > FP16_EXPONENT_MAX) return Half.NaN

    val b = y.toBits()
    if (b and FP16_COMBINED > FP16_EXPONENT_MAX) return Half.NaN

    if (a and FP16_COMBINED == 0 && b and FP16_COMBINED == 0) {
        return if (a and FP16_SIGN_MASK != 0) x else y
    }

    return if ((if (a and FP16_SIGN_MASK != 0) 0x8000 - (a and 0xffff) else a and 0xffff) <
               (if (b and FP16_SIGN_MASK != 0) 0x8000 - (b and 0xffff) else b and 0xffff)) x else y
}

/**
 * Returns the larger of two half-precision float values (the value closest
 * to positive infinity). Special values are handled in the following ways:
 *
 * - If either value is NaN, the result is NaN
 * - [Half.POSITIVE_ZERO] is greater than [Half.NEGATIVE_ZERO]
 *
 *
 * @param x The first half-precision value
 * @param y The second half-precision value
 *
 * @return The larger of the two specified half-precision values
 */
fun max(x: Half, y: Half): Half {
    val a = x.toBits()
    if (a and FP16_COMBINED > FP16_EXPONENT_MAX) return Half.NaN

    val b = y.toBits()
    if (b and FP16_COMBINED > FP16_EXPONENT_MAX) return Half.NaN

    if (a and FP16_COMBINED == 0 && b and FP16_COMBINED == 0) {
        return if (a and FP16_SIGN_MASK != 0) y else x
    }

    return if ((if (a and FP16_SIGN_MASK != 0) 0x8000 - (a and 0xffff) else a and 0xffff) >
               (if (b and FP16_SIGN_MASK != 0) 0x8000 - (b and 0xffff) else b and 0xffff)) x else y
}

/**
 * Returns the closest integral half-precision float value to the specified
 * half-precision float value. Special values are handled in the
 * following ways:
 *
 * - If the specified half-precision float is NaN, the result is NaN
 * - If the specified half-precision float is infinity (negative or positive),
 * the result is infinity (with the same sign)
 * - If the specified half-precision float is zero (negative or positive),
 * the result is zero (with the same sign)
 *
 * @param x A half-precision float value
 * @return The value of the specified half-precision float rounded to the nearest
 * half-precision float value
 */
fun round(x: Half): Half {
    val bits = x.toBits()
    var e = bits and 0x7fff
    var result = bits

    if (e < 0x3c00) {
        result = (result and FP16_SIGN_MASK) or (0x3c00 and (if (e >= 0x3800) 0xffff else 0x0))
    } else if (e < 0x6400) {
        e = 25 - (e shr 10)
        val mask = (1 shl e) - 1
        result += 1 shl (e - 1)
        result = result and mask.inv()
    } else {
        if (e > 0x7C00) result = result or 0x200
    }

    return Half(result.toUShort())
}

/**
 * Returns the largest half-precision float value toward positive infinity
 * less than or equal to the specified half-precision float value.
 * Special values are handled in the following ways:
 *
 * - If the specified half-precision float is NaN, the result is NaN
 * - If the specified half-precision float is infinity (negative or positive),
 * the result is infinity (with the same sign)
 * - If the specified half-precision float is zero (negative or positive),
 * the result is zero (with the same sign)
 *
 * @param x A half-precision float value
 * @return The largest half-precision float value toward positive infinity
 * less than or equal to the specified half-precision float value
 */
fun floor(x: Half): Half {
    val bits = x.toBits()
    var e = bits and 0x7fff
    var result = bits

    if (e < 0x3c00) {
        result = (result and FP16_SIGN_MASK) or (0x3c00 and if (bits > 0x8000) 0xffff else 0x0)
    } else if (e < 0x6400) {
        e = 25 - (e shr 10)
        val mask = (1 shl e) - 1
        result += mask and -(bits shr 15)
        result = result and mask.inv()
    } else {
        if (e > 0x7C00) result = result or 0x200
    }

    return Half(result.toUShort())
}

/**
 * Returns the smallest half-precision float value toward negative infinity
 * greater than or equal to the specified half-precision float value.
 * Special values are handled in the following ways:
 *
 * - If the specified half-precision float is NaN, the result is NaN
 * - If the specified half-precision float is infinity (negative or positive),
 * the result is infinity (with the same sign)
 * - If the specified half-precision float is zero (negative or positive),
 * the result is zero (with the same sign)
 *
 * @param x A half-precision float value
 * @return The smallest half-precision float value toward negative infinity
 * greater than or equal to the specified half-precision float value
 */
fun ceil(x: Half): Half {
    val bits = x.toBits()
    var e = bits and 0x7fff
    var result = bits

    if (e < 0x3c00) {
        result = result and FP16_SIGN_MASK
        result = result or (0x3c00 and -((bits shr 15).inv() and if (e != 0) 1 else 0))
    } else if (e < 0x6400) {
        e = 25 - (e shr 10)
        val mask = (1 shl e) - 1
        result += mask and (bits shr 15) - 1
        result = result and mask.inv()
    } else {
        if (e > 0x7C00) result = result or 0x200
    }

    return Half(result.toUShort())
}

/**
 * Returns the truncated half-precision float value of the specified
 * half-precision float value. Special values are handled in the following ways:
 *
 * - If the specified half-precision float is NaN, the result is NaN
 * - If the specified half-precision float is infinity (negative or positive),
 * the result is infinity (with the same sign)
 * - If the specified half-precision float is zero (negative or positive),
 * the result is zero (with the same sign)
 *
 * @param x A half-precision float value
 * @return The truncated half-precision float value of the specified
 * half-precision float value
 */
fun truncate(x: Half): Half {
    val bits = x.toBits()
    var e = bits and 0x7fff
    var result = bits

    if (e < 0x3c00) {
        result = result and FP16_SIGN_MASK
    } else if (e < 0x6400) {
        e = 25 - (e shr 10)
        val mask = (1 shl e) - 1
        result = result and mask.inv()
    }

    return Half(result.toUShort())
}

private fun floatToHalf(f: Float): UShort {
    val bits: Int = f.toBits()
    val s = bits ushr FP32_SIGN_SHIFT
    var e = (bits ushr FP32_EXPONENT_SHIFT) and FP32_EXPONENT_MASK
    var m = bits and FP32_SIGNIFICAND_MASK
    var outE = 0
    var outM = 0
    if (e == 0xff) { // Infinite or NaN
        outE = 0x1f
        outM = if (m != 0) 0x200 else 0
    } else {
        e = e - FP32_EXPONENT_BIAS + FP16_EXPONENT_BIAS
        if (e >= 0x1f) { // Overflow
            outE = 0x31
        } else if (e <= 0) { // Underflow
            if (e < -10) {
                // The absolute fp32 value is less than MIN_VALUE, flush to +/-0
            } else {
                // The fp32 value is a normalized float less than MIN_NORMAL,
                // we convert to a denorm fp16
                m = m or 0x800000 shr 1 - e
                if (m and 0x1000 != 0) m += 0x2000
                outM = m shr 13
            }
        } else {
            outE = e
            outM = m shr 13
            if (m and 0x1000 != 0) {
                // Round to nearest "0.5" up
                var out = outE shl FP16_EXPONENT_SHIFT or outM
                out++
                return (out or (s shl FP16_SIGN_SHIFT)).toUShort()
            }
        }
    }
    return (s shl FP16_SIGN_SHIFT or (outE shl FP16_EXPONENT_SHIFT) or outM).toUShort()
}

private fun halfToShort(h: UShort): Float {
    val bits = h.toInt()
    val s = bits and FP16_SIGN_MASK
    val e = bits ushr FP16_EXPONENT_SHIFT and FP16_EXPONENT_MASK
    val m = bits and FP16_SIGNIFICAND_MASK
    var outE = 0
    var outM = 0
    if (e == 0) { // Denormal or 0
        if (m != 0) {
            // Convert denorm fp16 into normalized fp32
            var o: Float = Float.fromBits(FP32_DENORMAL_MAGIC + m)
            o -= FP32_DENORMAL_FLOAT
            return if (s == 0) o else -o
        }
    } else {
        outM = m shl 13
        if (e == 0x1f) { // Infinite or NaN
            outE = 0xff
            if (outM != 0) { // SNaNs are quieted
                outM = outM or FP32_QNAN_MASK
            }
        } else {
            outE = e - FP16_EXPONENT_BIAS + FP32_EXPONENT_BIAS
        }
    }
    val out = s shl 16 or (outE shl FP32_EXPONENT_SHIFT) or outM
    return Float.fromBits(out)
}
