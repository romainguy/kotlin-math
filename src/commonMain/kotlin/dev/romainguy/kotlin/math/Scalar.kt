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

@file:Suppress("NOTHING_TO_INLINE", "unused")

package dev.romainguy.kotlin.math

import kotlin.math.pow

/**
 * The value of π as a single-precision float.
 */
const val PI          = 3.1415927f

/**
 * The value of π/2 as a single-precision float.
 */
const val HALF_PI     = PI * 0.5f

/**
 * The value of 2π as a single-precision float.
 */
const val TWO_PI      = PI * 2.0f

/**
 * The value of 4π as a single-precision float.
 */
const val FOUR_PI     = PI * 4.0f

/**
 * The value of 1/π as a single-precision float.
 */
const val INV_PI      = 1.0f / PI

/**
 * The value of 1/(2π) as a single-precision float.
 */
const val INV_TWO_PI  = INV_PI * 0.5f

/**
 * The value of 1/(4π) as a single-precision float.
 */
const val INV_FOUR_PI = INV_PI * 0.25f

/**
 * The half-precision constant 1.0.
 */
val HALF_ONE = Half(0x3c00.toUShort())

/**
 * The half-precision constant 2.0.
 */
val HALF_TWO = Half(0x4000.toUShort())

/**
 * Clamps the value [x] between [min] and [max].
 *
 * @param x The value to clamp.
 * @param min The lower bound.
 * @param max The upper bound.
 * @return [min] if [x] < [min], [max] if [x] > [max], otherwise [x].
 */
inline fun clamp(x: Float, min: Float, max: Float) = if (x < min) min else (if (x > max) max else x)

/**
 * Clamps the half-precision value [x] between [min] and [max].
 *
 * @param x The half-precision value to clamp.
 * @param min The lower bound.
 * @param max The upper bound.
 * @return [min] if [x] < [min], [max] if [x] > [max], otherwise [x].
 */
inline fun clamp(x: Half, min: Half, max: Half) = if (x < min) min else (if (x > max) max else x)

/**
 * Clamps the value [x] between 0.0 and 1.0.
 *
 * @param x The value to saturate.
 * @return [x] clamped to the range [0.0, 1.0].
 */
inline fun saturate(x: Float) = clamp(x, 0.0f, 1.0f)

/**
 * Clamps the half-precision value [x] between 0.0 and 1.0.
 *
 * @param x The half-precision value to saturate.
 * @return [x] clamped to the range [0.0, 1.0].
 */
inline fun saturate(x: Half) = clamp(x, Half.POSITIVE_ZERO, HALF_ONE)

/**
 * Linearly interpolates between [a] and [b] by [x].
 *
 * @param a The starting value.
 * @param b The ending value.
 * @param x The interpolation factor, typically between 0.0 and 1.0.
 * @return The interpolated value.
 */
inline fun mix(a: Float, b: Float, x: Float) = a * (1.0f - x) + b * x

/**
 * Linearly interpolates between half-precision values [a] and [b] by [x].
 *
 * @param a The starting half-precision value.
 * @param b The ending half-precision value.
 * @param x The interpolation factor, typically between 0.0 and 1.0.
 * @return The interpolated half-precision value.
 */
inline fun mix(a: Half, b: Half, x: Half) = a * (HALF_ONE - x) + b * x

/**
 * Converts radians to degrees.
 *
 * @param v The angle in radians.
 * @return The angle in degrees.
 */
inline fun degrees(v: Float) = v * (180.0f * INV_PI)

/**
 * Converts degrees to radians.
 *
 * @param v The angle in degrees.
 * @return The angle in radians.
 */
inline fun radians(v: Float) = v * (PI / 180.0f)

/**
 * Returns the fractional part of [v].
 *
 * @param v The value to process.
 * @return [v] - floor([v]).
 */
inline fun fract(v: Float) = v % 1

/**
 * Returns the square of [v].
 *
 * @param v The value to square.
 * @return [v] * [v].
 */
inline fun sqr(v: Float) = v * v

/**
 * Returns the square of the half-precision value [v].
 *
 * @param v The half-precision value to square.
 * @return [v] * [v].
 */
inline fun sqr(v: Half) = v * v

/**
 * Returns [x] raised to the power of [y].
 *
 * @param x The base.
 * @param y The exponent.
 * @return [x]^[y].
 */
inline fun pow(x: Float, y: Float) = (x.toDouble().pow(y.toDouble())).toFloat()
