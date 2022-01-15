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

import kotlin.math.*

const val PI          = 3.1415926536f
const val HALF_PI     = PI * 0.5f
const val TWO_PI      = PI * 2.0f
const val FOUR_PI     = PI * 4.0f
const val INV_PI      = 1.0f / PI
const val INV_TWO_PI  = INV_PI * 0.5f
const val INV_FOUR_PI = INV_PI * 0.25f

inline fun clamp(x: Float, min: Float, max: Float)= if (x < min) min else (if (x > max) max else x)

inline fun saturate(x: Float) = clamp(x, 0.0f, 1.0f)

inline fun mix(a: Float, b: Float, x: Float) = a * (1.0f - x) + b * x

inline fun degrees(v: Float) = v * (180.0f * INV_PI)

inline fun radians(v: Float) = v * (PI / 180.0f)

inline fun fract(v: Float) = v % 1

inline fun sqr(v: Float) = v * v

inline fun pow(x: Float, y: Float) = (x.toDouble().pow(y.toDouble())).toFloat()

fun eulerAngles(q: Float4): Float3 {
    val x = atan2(2.0f * (q.y * q.z + q.w * q.x), q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z)
    val y = asin(-2.0f * (q.x * q.z - q.w * q.y))
    val z = atan2(2.0f * (q.x * q.y + q.w * q.z), q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z)
    return transform(Float3(x, y, z), ::degrees)
}

fun quaternion(d: Float3): Float4 {
    val x = quaternion(Float3(x = 1.0f), d.x)
    val y = quaternion(Float3(y = 1.0f), d.y)
    val z = quaternion(Float3(z = 1.0f), d.z)
    return combine(combine(y, x), z)
}

inline fun quaternion(axis: Float3, angle: Float): Float4 {
    val r = radians(angle)
    return normalize(Float4(v = axis * sin(r / 2.0f), w = cos(r / 2.0f)))
}