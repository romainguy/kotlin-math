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

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.math.acos
import kotlin.math.absoluteValue

/**
 * Enumeration of vector components for various coordinate systems (XYZW, RGBA, STPQ).
 */
enum class VectorComponent {
    X, Y, Z, W,
    R, G, B, A,
    S, T, P, Q
}

/**
 * A 2-component vector of single-precision floats.
 *
 * @constructor Creates a new vector with the specified components.
 * @property x The X component of the vector.
 * @property y The Y component of the vector.
 */
data class Float2(var x: Float = 0.0f, var y: Float = 0.0f) {
    /**
     * Creates a new vector with the specified [v] for all components.
     */
    constructor(v: Float) : this(v, v)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Float2) : this(v.x, v.y)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Float
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Float
        get() = y
        set(value) {
            y = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Float
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Float
        get() = y
        set(value) {
            y = value
        }

    /**
     * The XY components of the vector as a [Float2].
     */
    inline var xy: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Float2].
     */
    inline var rg: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Float2].
     */
    inline var st: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        else -> throw IllegalArgumentException("index must be X, Y, R, G, S or T")
    }

    /**
     * Returns two components at the specified indices as a [Float2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Float2 {
        return Float2(get(index1), get(index2))
    }

    /**
     * Returns the component at the specified [index] (0 or 1).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("index must be in 0..1")
    }

    /**
     * Returns two components at the specified indices as a [Float2].
     */
    operator fun get(index1: Int, index2: Int) = Float2(get(index1), get(index2))

    /**
     * Returns the component at the specified [index] (1 or 2).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0 or 1).
     */
    operator fun set(index: Int, v: Float) = when (index) {
        0 -> x = v
        1 -> y = v
        else -> throw IllegalArgumentException("index must be in 0..1")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Float) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Float) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        else -> throw IllegalArgumentException("index must be X, Y, R, G, S or T")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Float) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Negates all components of this vector.
     */
    operator fun unaryMinus() = Float2(-x, -y)

    /**
     * Increments all components of this vector.
     */
    operator fun inc() = Float2(x++, y++)

    /**
     * Decrements all components of this vector.
     */
    operator fun dec() = Float2(x--, y--)

    /**
     * Adds a scalar to all components of this vector.
     */
    inline operator fun plus(v: Float) = Float2(x + v, y + v)

    /**
     * Subtracts a scalar from all components of this vector.
     */
    inline operator fun minus(v: Float) = Float2(x - v, y - v)

    /**
     * Multiplies all components of this vector by a scalar.
     */
    inline operator fun times(v: Float) = Float2(x * v, y * v)

    /**
     * Divides all components of this vector by a scalar.
     */
    inline operator fun div(v: Float) = Float2(x / v, y / v)

    /**
     * Compares this vector to a scalar.
     */
    inline fun compareTo(v: Float, delta: Float = 0.0f) = Float2(
        x.compareTo(v, delta),
        y.compareTo(v, delta)
    )

    /**
     * Returns true if all components are equal to the scalar.
     */
    inline fun equals(v: Float, delta: Float = 0.0f) = x.equals(v, delta) && y.equals(v, delta)

    /**
     * Adds two vectors.
     */
    inline operator fun plus(v: Float2) = Float2(x + v.x, y + v.y)

    /**
     * Subtracts two vectors.
     */
    inline operator fun minus(v: Float2) = Float2(x - v.x, y - v.y)

    /**
     * Multiplies two vectors component-wise.
     */
    inline operator fun times(v: Float2) = Float2(x * v.x, y * v.y)

    /**
     * Divides two vectors component-wise.
     */
    inline operator fun div(v: Float2) = Float2(x / v.x, y / v.y)

    /**
     * Compares two vectors.
     */
    inline fun compareTo(v: Float2, delta: Float = 0.0f) = Float2(
        x.compareTo(v.x, delta),
        y.compareTo(v.y, delta)
    )

    /**
     * Returns true if two vectors are equal.
     */
    inline fun equals(v: Float2, delta: Float = 0.0f) = x.equals(v.x, delta) && y.equals(v.y, delta)

    /**
     * Transforms each component of this vector using the provided [block].
     */
    inline fun transform(block: (Float) -> Float): Float2 {
        x = block(x)
        y = block(y)
        return this
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     */
    fun toFloatArray() = floatArrayOf(x, y)
}

/**
 * A 3-component vector of single-precision floats.
 *
 * @constructor Creates a new vector with the specified components.
 * @property x The X component of the vector.
 * @property y The Y component of the vector.
 * @property z The Z component of the vector.
 */
data class Float3(var x: Float = 0.0f, var y: Float = 0.0f, var z: Float = 0.0f) {
    /**
     * Creates a new vector with the specified [v] for all components.
     */
    constructor(v: Float) : this(v, v, v)

    /**
     * Creates a new vector with the specified [v] and [z].
     */
    constructor(v: Float2, z: Float = 0.0f) : this(v.x, v.y, z)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Float3) : this(v.x, v.y, v.z)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Float
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Float
        get() = y
        set(value) {
            y = value
        }

    /**
     * The B (blue) component of the vector (alias for [z]).
     */
    inline var b: Float
        get() = z
        set(value) {
            z = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Float
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Float
        get() = y
        set(value) {
            y = value
        }

    /**
     * The P (texture W) component of the vector (alias for [z]).
     */
    inline var p: Float
        get() = z
        set(value) {
            z = value
        }

    /**
     * The XY components of the vector as a [Float2].
     */
    inline var xy: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Float2].
     */
    inline var rg: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Float2].
     */
    inline var st: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RGB components of the vector as a [Float3].
     */
    inline var rgb: Float3
        get() = Float3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The XYZ components of the vector as a [Float3].
     */
    inline var xyz: Float3
        get() = Float3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The STP components of the vector as a [Float3].
     */
    inline var stp: Float3
        get() = Float3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z
        else -> throw IllegalArgumentException("index must be X, Y, Z, R, G, B, S, T or P")
    }

    /**
     * Returns two components at the specified indices as a [Float2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Float2 {
        return Float2(get(index1), get(index2))
    }

    /**
     * Returns three components at the specified indices as a [Float3].
     */
    operator fun get(
            index1: VectorComponent, index2: VectorComponent, index3: VectorComponent): Float3 {
        return Float3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns the component at the specified [index] (0, 1 or 2).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("index must be in 0..2")
    }

    /**
     * Returns two components at the specified indices as a [Float2].
     */
    operator fun get(index1: Int, index2: Int) = Float2(get(index1), get(index2))

    /**
     * Returns three components at the specified indices as a [Float3].
     */
    operator fun get(index1: Int, index2: Int, index3: Int): Float3 {
        return Float3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns the component at the specified [index] (1 to 3).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0, 1 or 2).
     */
    operator fun set(index: Int, v: Float) = when (index) {
        0 -> x = v
        1 -> y = v
        2 -> z = v
        else -> throw IllegalArgumentException("index must be in 0..2")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Float) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, v: Float) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Float) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z = v
        else -> throw IllegalArgumentException("index must be X, Y, Z, R, G, B, S, T or P")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Float) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
            index1: VectorComponent, index2: VectorComponent, index3: VectorComponent, v: Float) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Negates all components of this vector.
     */
    operator fun unaryMinus() = Float3(-x, -y, -z)

    /**
     * Increments all components of this vector.
     */
    operator fun inc() = Float3(x++, y++, z++)

    /**
     * Decrements all components of this vector.
     */
    operator fun dec() = Float3(x--, y--, z--)

    /**
     * Adds a scalar to all components of this vector.
     */
    inline operator fun plus(v: Float) = Float3(x + v, y + v, z + v)

    /**
     * Subtracts a scalar from all components of this vector.
     */
    inline operator fun minus(v: Float) = Float3(x - v, y - v, z - v)

    /**
     * Multiplies all components of this vector by a scalar.
     */
    inline operator fun times(v: Float) = Float3(x * v, y * v, z * v)

    /**
     * Divides all components of this vector by a scalar.
     */
    inline operator fun div(v: Float) = Float3(x / v, y / v, z / v)

    /**
     * Compares this vector to a scalar.
     */
    inline fun compareTo(v: Float, delta: Float = 0.0f) = Float3(
        x.compareTo(v, delta),
        y.compareTo(v, delta),
        z.compareTo(v, delta)
    )

    /**
     * Returns true if all components are equal to the scalar.
     */
    inline fun equals(v: Float, delta: Float = 0.0f) =
        x.equals(v, delta) && y.equals(v, delta) && z.equals(v, delta)

    /**
     * Adds a [Float2] to this vector (z is unchanged).
     */
    inline operator fun plus(v: Float2) = Float3(x + v.x, y + v.y, z)

    /**
     * Subtracts a [Float2] from this vector (z is unchanged).
     */
    inline operator fun minus(v: Float2) = Float3(x - v.x, y - v.y, z)

    /**
     * Multiplies this vector by a [Float2] (z is unchanged).
     */
    inline operator fun times(v: Float2) = Float3(x * v.x, y * v.y, z)

    /**
     * Divides this vector by a [Float2] (z is unchanged).
     */
    inline operator fun div(v: Float2) = Float3(x / v.x, y / v.y, z)

    /**
     * Adds two vectors.
     */
    inline operator fun plus(v: Float3) = Float3(x + v.x, y + v.y, z + v.z)

    /**
     * Subtracts two vectors.
     */
    inline operator fun minus(v: Float3) = Float3(x - v.x, y - v.y, z - v.z)

    /**
     * Multiplies two vectors component-wise.
     */
    inline operator fun times(v: Float3) = Float3(x * v.x, y * v.y, z * v.z)

    /**
     * Divides two vectors component-wise.
     */
    inline operator fun div(v: Float3) = Float3(x / v.x, y / v.y, z / v.z)

    /**
     * Compares two vectors.
     */
    inline fun compareTo(v: Float3, delta: Float = 0.0f) = Float3(
        x.compareTo(v.x, delta),
        y.compareTo(v.y, delta),
        z.compareTo(v.z, delta)
    )

    /**
     * Returns true if two vectors are equal.
     */
    inline fun equals(v: Float3, delta: Float = 0.0f) =
        x.equals(v.x, delta) && y.equals(v.y, delta) && z.equals(v.z, delta)

    /**
     * Transforms each component of this vector using the provided [block].
     */
    inline fun transform(block: (Float) -> Float): Float3 {
        x = block(x)
        y = block(y)
        z = block(z)
        return this
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     */
    fun toFloatArray() = floatArrayOf(x, y, z)
}

/**
 * A 4-component vector of single-precision floats.
 *
 * @constructor Creates a new vector with the specified components.
 * @property x The X component of the vector.
 * @property y The Y component of the vector.
 * @property z The Z component of the vector.
 * @property w The W component of the vector.
 */
data class Float4(
        var x: Float = 0.0f,
        var y: Float = 0.0f,
        var z: Float = 0.0f,
        var w: Float = 0.0f) {
    /**
     * Creates a new vector with the specified [v] for all components.
     */
    constructor(v: Float) : this(v, v, v, v)

    /**
     * Creates a new vector with the specified [v], [z] and [w].
     */
    constructor(v: Float2, z: Float = 0.0f, w: Float = 0.0f) : this(v.x, v.y, z, w)

    /**
     * Creates a new vector with the specified [v] and [w].
     */
    constructor(v: Float3, w: Float = 0.0f) : this(v.x, v.y, v.z, w)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Float4) : this(v.x, v.y, v.z, v.w)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Float
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Float
        get() = y
        set(value) {
            y = value
        }

    /**
     * The B (blue) component of the vector (alias for [z]).
     */
    inline var b: Float
        get() = z
        set(value) {
            z = value
        }

    /**
     * The A (alpha) component of the vector (alias for [w]).
     */
    inline var a: Float
        get() = w
        set(value) {
            w = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Float
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Float
        get() = y
        set(value) {
            y = value
        }

    /**
     * The P (texture W) component of the vector (alias for [z]).
     */
    inline var p: Float
        get() = z
        set(value) {
            z = value
        }

    /**
     * The Q (texture Q) component of the vector (alias for [w]).
     */
    inline var q: Float
        get() = w
        set(value) {
            w = value
        }

    /**
     * The XY components of the vector as a [Float2].
     */
    inline var xy: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Float2].
     */
    inline var rg: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Float2].
     */
    inline var st: Float2
        get() = Float2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RGB components of the vector as a [Float3].
     */
    inline var rgb: Float3
        get() = Float3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The XYZ components of the vector as a [Float3].
     */
    inline var xyz: Float3
        get() = Float3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The STP components of the vector as a [Float3].
     */
    inline var stp: Float3
        get() = Float3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The RGBA components of the vector as a [Float4].
     */
    inline var rgba: Float4
        get() = Float4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * The XYZW components of the vector as a [Float4].
     */
    inline var xyzw: Float4
        get() = Float4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * The STPQ components of the vector as a [Float4].
     */
    inline var stpq: Float4
        get() = Float4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z
        VectorComponent.W, VectorComponent.A, VectorComponent.Q -> w
    }

    /**
     * Returns two components at the specified indices as a [Float2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Float2 {
        return Float2(get(index1), get(index2))
    }

    /**
     * Returns three components at the specified indices as a [Float3].
     */
    operator fun get(
            index1: VectorComponent,
            index2: VectorComponent,
            index3: VectorComponent): Float3 {
        return Float3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns four components at the specified indices as a [Float4].
     */
    operator fun get(
            index1: VectorComponent,
            index2: VectorComponent,
            index3: VectorComponent,
            index4: VectorComponent): Float4 {
        return Float4(get(index1), get(index2), get(index3), get(index4))
    }

    /**
     * Returns the component at the specified [index] (0 to 3).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    /**
     * Returns two components at the specified indices as a [Float2].
     */
    operator fun get(index1: Int, index2: Int) = Float2(get(index1), get(index2))

    /**
     * Returns three components at the specified indices as a [Float3].
     */
    operator fun get(index1: Int, index2: Int, index3: Int): Float3 {
        return Float3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns four components at the specified indices as a [Float4].
     */
    operator fun get(index1: Int, index2: Int, index3: Int, index4: Int): Float4 {
        return Float4(get(index1), get(index2), get(index3), get(index4))
    }

    /**
     * Returns the component at the specified [index] (1 to 4).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0 to 3).
     */
    operator fun set(index: Int, v: Float) = when (index) {
        0 -> x = v
        1 -> y = v
        2 -> z = v
        3 -> w = v
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Float) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, v: Float) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, index4: Int, v: Float) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
        set(index4, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Float) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z = v
        VectorComponent.W, VectorComponent.A, VectorComponent.Q -> w = v
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Float) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
            index1: VectorComponent, index2: VectorComponent, index3: VectorComponent, v: Float) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
            index1: VectorComponent, index2: VectorComponent,
            index3: VectorComponent, index4: VectorComponent, v: Float) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
        set(index4, v)
    }

    /**
     * Negates all components of this vector.
     */
    operator fun unaryMinus() = Float4(-x, -y, -z, -w)

    /**
     * Increments all components of this vector.
     */
    operator fun inc() = Float4(x++, y++, z++, w++)

    /**
     * Decrements all components of this vector.
     */
    operator fun dec() = Float4(x--, y--, z--, w--)

    /**
     * Adds a scalar to all components of this vector.
     */
    inline operator fun plus(v: Float) = Float4(x + v, y + v, z + v, w + v)

    /**
     * Subtracts a scalar from all components of this vector.
     */
    inline operator fun minus(v: Float) = Float4(x - v, y - v, z - v, w - v)

    /**
     * Multiplies all components of this vector by a scalar.
     */
    inline operator fun times(v: Float) = Float4(x * v, y * v, z * v, w * v)

    /**
     * Divides all components of this vector by a scalar.
     */
    inline operator fun div(v: Float) = Float4(x / v, y / v, z / v, w / v)

    /**
     * Compares this vector to a scalar.
     */
    inline fun compareTo(v: Float, delta: Float = 0.0f) = Float4(
        x.compareTo(v, delta),
        y.compareTo(v, delta),
        z.compareTo(v, delta),
        w.compareTo(v, delta)
    )

    /**
     * Returns true if all components are equal to the scalar.
     */
    inline fun equals(v: Float, delta: Float = 0.0f) =
        x.equals(v, delta) && y.equals(v, delta) && z.equals(v, delta) && w.equals(v, delta)

    /**
     * Adds a [Float2] to this vector (z and w are unchanged).
     */
    inline operator fun plus(v: Float2) = Float4(x + v.x, y + v.y, z, w)

    /**
     * Subtracts a [Float2] from this vector (z and w are unchanged).
     */
    inline operator fun minus(v: Float2) = Float4(x - v.x, y - v.y, z, w)

    /**
     * Multiplies this vector by a [Float2] (z and w are unchanged).
     */
    inline operator fun times(v: Float2) = Float4(x * v.x, y * v.y, z, w)

    /**
     * Divides this vector by a [Float2] (z and w are unchanged).
     */
    inline operator fun div(v: Float2) = Float4(x / v.x, y / v.y, z, w)

    /**
     * Adds a [Float3] to this vector (w is unchanged).
     */
    inline operator fun plus(v: Float3) = Float4(x + v.x, y + v.y, z + v.z, w)

    /**
     * Subtracts a [Float3] from this vector (w is unchanged).
     */
    inline operator fun minus(v: Float3) = Float4(x - v.x, y - v.y, z - v.z, w)

    /**
     * Multiplies this vector by a [Float3] (w is unchanged).
     */
    inline operator fun times(v: Float3) = Float4(x * v.x, y * v.y, z * v.z, w)

    /**
     * Divides this vector by a [Float3] (w is unchanged).
     */
    inline operator fun div(v: Float3) = Float4(x / v.x, y / v.y, z / v.z, w)

    /**
     * Adds two vectors.
     */
    inline operator fun plus(v: Float4) = Float4(x + v.x, y + v.y, z + v.z, w + v.w)

    /**
     * Subtracts two vectors.
     */
    inline operator fun minus(v: Float4) = Float4(x - v.x, y - v.y, z - v.z, w - v.w)

    /**
     * Multiplies two vectors component-wise.
     */
    inline operator fun times(v: Float4) = Float4(x * v.x, y * v.y, z * v.z, w * v.w)

    /**
     * Divides two vectors component-wise.
     */
    inline operator fun div(v: Float4) = Float4(x / v.x, y / v.y, z / v.z, w / v.w)

    /**
     * Compares two vectors.
     */
    inline fun compareTo(v: Float4, delta: Float = 0.0f) = Float4(
        x.compareTo(v.x, delta),
        y.compareTo(v.y, delta),
        z.compareTo(v.z, delta),
        w.compareTo(v.w, delta)
    )

    /**
     * Returns true if two vectors are equal.
     */
    inline fun equals(v: Float4, delta: Float = 0.0f) =
        x.equals(v.x, delta) && y.equals(v.y, delta) && z.equals(v.z, delta) && w.equals(v.w, delta)

    /**
     * Transforms each component of this vector using the provided [block].
     */
    inline fun transform(block: (Float) -> Float): Float4 {
        x = block(x)
        y = block(y)
        z = block(z)
        w = block(w)
        return this
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     */
    fun toFloatArray() = floatArrayOf(x, y, z, w)
}

/**
 * Adds a [Float2] to a scalar.
 */
inline operator fun Float.plus(v: Float2) = Float2(this + v.x, this + v.y)

/**
 * Subtracts a [Float2] from a scalar.
 */
inline operator fun Float.minus(v: Float2) = Float2(this - v.x, this - v.y)

/**
 * Multiplies a scalar by a [Float2].
 */
inline operator fun Float.times(v: Float2) = Float2(this * v.x, this * v.y)

/**
 * Divides a scalar by a [Float2].
 */
inline operator fun Float.div(v: Float2) = Float2(this / v.x, this / v.y)

/**
 * Compares this [Float] to [v] within [delta].
 */
inline fun Float.compareTo(v: Float, delta: Float): Float = when {
    equals(v, delta) -> 0.0f
    else -> compareTo(v).toFloat()
}

/**
 * Returns true if this [Float] is equal to [v] within [delta].
 */
inline fun Float.equals(v: Float, delta: Float) = (this - v).absoluteValue < delta
/**
 * Returns the absolute value of the given vector [v].
 */
inline fun abs(v: Float2) = Float2(abs(v.x), abs(v.y))

/**
 * Returns the length of the given vector [v].
 */
inline fun length(v: Float2) = sqrt(v.x * v.x + v.y * v.y)

/**
 * Returns the squared length of the given vector [v].
 */
inline fun length2(v: Float2) = v.x * v.x + v.y * v.y

/**
 * Returns the distance between two points [a] and [b].
 */
inline fun distance(a: Float2, b: Float2) = length(a - b)

/**
 * Returns the dot product of two vectors.
 */
inline fun dot(a: Float2, b: Float2) = a.x * b.x + a.y * b.y

/**
 * Returns the normalized vector [v] (length is 1.0).
 */
fun normalize(v: Float2): Float2 {
    val l = 1.0f / length(v)
    return Float2(v.x * l, v.y * l)
}

/**
 * Returns the reflection vector of [i] against the normal [n].
 */
inline fun reflect(i: Float2, n: Float2) = i - 2.0f * dot(n, i) * n

/**
 * Returns the refraction vector of [i] against the normal [n] with the given refractive index [eta].
 */
fun refract(i: Float2, n: Float2, eta: Float): Float2 {
    val d = dot(n, i)
    val k = 1.0f - eta * eta * (1.0f - sqr(d))
    return if (k < 0.0f) Float2(0.0f) else eta * i - (eta * d + sqrt(k)) * n
}

/**
 * Returns the angle in radians between two vectors [a] and [b].
 */
inline fun angle(a: Float2, b: Float2): Float {
    val l = length(a) * length(b)
    return if (l == 0.0f) 0.0f else acos(clamp(dot(a, b) / l, -1.0f, 1.0f))
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Float2, min: Float, max: Float): Float2 {
    return Float2(
            clamp(v.x, min, max),
            clamp(v.y, min, max)
    )
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Float2, min: Float2, max: Float2): Float2 {
    return Float2(
            clamp(v.x, min.x, max.x),
            clamp(v.y, min.y, max.y)
    )
}

/**
 * Linearly interpolates between [a] and [b] by [x].
 */
inline fun mix(a: Float2, b: Float2, x: Float): Float2 {
    return Float2(
            mix(a.x, b.x, x),
            mix(a.y, b.y, x)
    )
}

/**
 * Linearly interpolates component-wise between [a] and [b] by [x].
 */
inline fun mix(a: Float2, b: Float2, x: Float2): Float2 {
    return Float2(
            mix(a.x, b.x, x.x),
            mix(a.y, b.y, x.y)
    )
}

/**
 * Returns the minimum component of the vector [v].
 */
inline fun min(v: Float2) = min(v.x, v.y)

/**
 * Returns a vector containing the minimum components of [a] and [b].
 */
inline fun min(a: Float2, b: Float2) = Float2(min(a.x, b.x), min(a.y, b.y))

/**
 * Returns the maximum component of the vector [v].
 */
inline fun max(v: Float2) = max(v.x, v.y)

/**
 * Returns a vector containing the maximum components of [a] and [b].
 */
inline fun max(a: Float2, b: Float2) = Float2(max(a.x, b.x), max(a.y, b.y))

/**
 * Transforms each component of the vector [v] using the provided [block].
 */
inline fun transform(v: Float2, block: (Float) -> Float) = v.copy().transform(block)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than [b].
 */
inline fun lessThan(a: Float2, b: Float) = Bool2(a.x < b, a.y < b)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than the corresponding component of [b].
 */
inline fun lessThan(a: Float2, b: Float2) = Bool2(a.x < b.x, a.y < b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than or equal to [b].
 */
inline fun lessThanEqual(a: Float2, b: Float) = Bool2(a.x <= b, a.y <= b)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than or equal to the corresponding component of [b].
 */
inline fun lessThanEqual(a: Float2, b: Float2) = Bool2(a.x <= b.x, a.y <= b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than [b].
 */
inline fun greaterThan(a: Float2, b: Float) = Bool2(a.x > b, a.y > b)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than the corresponding component of [b].
 */
inline fun greaterThan(a: Float2, b: Float2) = Bool2(a.x > b.y, a.y > b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than or equal to [b].
 */
inline fun greaterThanEqual(a: Float2, b: Float) = Bool2(a.x >= b, a.y >= b)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than or equal to the corresponding component of [b].
 */
inline fun greaterThanEqual(a: Float2, b: Float2) = Bool2(a.x >= b.x, a.y >= b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is equal to [b] within [delta].
 */
inline fun equal(a: Float2, b: Float, delta: Float = 0.0f) = Bool2(
    a.x.equals(b, delta),
    a.y.equals(b, delta)
)

/**
 * Returns a [Bool2] indicating if each component of [a] is equal to the corresponding component of [b] within [delta].
 */
inline fun equal(a: Float2, b: Float2, delta: Float = 0.0f) = Bool2(
    a.x.equals(b.x, delta),
    a.y.equals(b.y, delta)
)

/**
 * Returns a [Bool2] indicating if each component of [a] is not equal to [b] within [delta].
 */
inline fun notEqual(a: Float2, b: Float, delta: Float = 0.0f) = Bool2(
    !a.x.equals(b, delta),
    !a.y.equals(b, delta)
)

/**
 * Returns a [Bool2] indicating if each component of [a] is not equal to the corresponding component of [b] within [delta].
 */
inline fun notEqual(a: Float2, b: Float2, delta: Float = 0.0f) = Bool2(
    !a.x.equals(b.x, delta),
    !a.y.equals(b.y, delta)
)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than [b].
 */
inline infix fun Float2.lt(b: Float) = Bool2(x < b, y < b)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than the corresponding component of [b].
 */
inline infix fun Float2.lt(b: Float2) = Bool2(x < b.x, y < b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than or equal to [b].
 */
inline infix fun Float2.lte(b: Float) = Bool2(x <= b, y <= b)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than or equal to the corresponding component of [b].
 */
inline infix fun Float2.lte(b: Float2) = Bool2(x <= b.x, y <= b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than [b].
 */
inline infix fun Float2.gt(b: Float) = Bool2(x > b, y > b)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than the corresponding component of [b].
 */
inline infix fun Float2.gt(b: Float2) = Bool2(x > b.x, y > b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than or equal to [b].
 */
inline infix fun Float2.gte(b: Float) = Bool2(x >= b, y >= b)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than or equal to the corresponding component of [b].
 */
inline infix fun Float2.gte(b: Float2) = Bool2(x >= b.x, y >= b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is equal to [b].
 */
inline infix fun Float2.eq(b: Float) = Bool2(x == b, y == b)

/**
 * Returns a [Bool2] indicating if each component of this vector is equal to the corresponding component of [b].
 */
inline infix fun Float2.eq(b: Float2) = Bool2(x == b.x, y == b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is not equal to [b].
 */
inline infix fun Float2.neq(b: Float) = Bool2(x != b, y != b)

/**
 * Returns a [Bool2] indicating if each component of this vector is not equal to the corresponding component of [b].
 */
inline infix fun Float2.neq(b: Float2) = Bool2(x != b.x, y != b.y)

/**
 * Returns true if any component of the vector [v] is true.
 */
inline fun any(v: Bool2) = v.x || v.y

/**
 * Returns true if all components of the vector [v] are true.
 */
inline fun all(v: Bool2) = v.x && v.y

/**
 * Returns true if any component of the vector [v] is true.
 */
inline fun any(v: Bool3) = v.x || v.y || v.z

/**
 * Returns true if all components of the vector [v] are true.
 */
inline fun all(v: Bool3) = v.x && v.y && v.z

/**
 * Adds a [Float3] to a scalar.
 */
inline operator fun Float.plus(v: Float3) = Float3(this + v.x, this + v.y, this + v.z)

/**
 * Subtracts a [Float3] from a scalar.
 */
inline operator fun Float.minus(v: Float3) = Float3(this - v.x, this - v.y, this - v.z)

/**
 * Multiplies a scalar by a [Float3].
 */
inline operator fun Float.times(v: Float3) = Float3(this * v.x, this * v.y, this * v.z)

/**
 * Divides a scalar by a [Float3].
 */
inline operator fun Float.div(v: Float3) = Float3(this / v.x, this / v.y, this / v.z)

/**
 * Adds a [Float4] to a scalar.
 */
inline operator fun Float.plus(v: Float4) = Float4(this + v.x, this + v.y, this + v.z, this + v.w)

/**
 * Subtracts a [Float4] from a scalar.
 */
inline operator fun Float.minus(v: Float4) = Float4(this - v.x, this - v.y, this - v.z, this - v.w)

/**
 * Multiplies a scalar by a [Float4].
 */
inline operator fun Float.times(v: Float4) = Float4(this * v.x, this * v.y, this * v.z, this * v.w)

/**
 * Divides a scalar by a [Float4].
 */
inline operator fun Float.div(v: Float4) = Float4(this / v.x, this / v.y, this / v.z, this / v.w)

/**
 * Returns the absolute value of the given vector [v].
 */
inline fun abs(v: Float3) = Float3(abs(v.x), abs(v.y), abs(v.z))

/**
 * Returns the length of the given vector [v].
 */
inline fun length(v: Float3) = sqrt(v.x * v.x + v.y * v.y + v.z * v.z)

/**
 * Returns the squared length of the given vector [v].
 */
inline fun length2(v: Float3) = v.x * v.x + v.y * v.y + v.z * v.z

/**
 * Returns the distance between two points [a] and [b].
 */
inline fun distance(a: Float3, b: Float3) = length(a - b)

/**
 * Returns the dot product of two vectors.
 */
inline fun dot(a: Float3, b: Float3) = a.x * b.x + a.y * b.y + a.z * b.z

/**
 * Returns the cross product of two vectors.
 */
inline fun cross(a: Float3, b: Float3): Float3 {
    return Float3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x)
}

/**
 * Returns the cross product of two vectors.
 */
inline infix fun Float3.x(v: Float3): Float3  = cross(this, v)

/**
 * Returns the normalized vector [v] (length is 1.0).
 */
fun normalize(v: Float3): Float3 {
    val l = 1.0f / length(v)
    return Float3(v.x * l, v.y * l, v.z * l)
}

/**
 * Returns the reflection vector of [i] against the normal [n].
 */
inline fun reflect(i: Float3, n: Float3) = i - 2.0f * dot(n, i) * n

/**
 * Returns the refraction vector of [i] against the normal [n] with the given refractive index [eta].
 */
fun refract(i: Float3, n: Float3, eta: Float): Float3 {
    val d = dot(n, i)
    val k = 1.0f - eta * eta * (1.0f - sqr(d))
    return if (k < 0.0f) Float3(0.0f) else eta * i - (eta * d + sqrt(k)) * n
}

/**
 * Returns the angle in radians between two vectors [a] and [b].
 */
inline fun angle(a: Float3, b: Float3): Float {
    val l = length(a) * length(b)
    return if (l == 0.0f) 0.0f else acos(clamp(dot(a, b) / l, -1.0f, 1.0f))
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Float3, min: Float, max: Float): Float3 {
    return Float3(
            clamp(v.x, min, max),
            clamp(v.y, min, max),
            clamp(v.z, min, max)
    )
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Float3, min: Float3, max: Float3): Float3 {
    return Float3(
            clamp(v.x, min.x, max.x),
            clamp(v.y, min.y, max.y),
            clamp(v.z, min.z, max.z)
    )
}

/**
 * Linearly interpolates between [a] and [b] by [x].
 */
inline fun mix(a: Float3, b: Float3, x: Float): Float3 {
    return Float3(
            mix(a.x, b.x, x),
            mix(a.y, b.y, x),
            mix(a.z, b.z, x)
    )
}

/**
 * Linearly interpolates component-wise between [a] and [b] by [x].
 */
inline fun mix(a: Float3, b: Float3, x: Float3): Float3 {
    return Float3(
            mix(a.x, b.x, x.x),
            mix(a.y, b.y, x.y),
            mix(a.z, b.z, x.z)
    )
}

/**
 * Returns the minimum component of the vector [v].
 */
inline fun min(v: Float3) = min(v.x, min(v.y, v.z))

/**
 * Returns a vector containing the minimum components of [a] and [b].
 */
inline fun min(a: Float3, b: Float3) = Float3(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z))

/**
 * Returns the maximum component of the vector [v].
 */
inline fun max(v: Float3) = max(v.x, max(v.y, v.z))

/**
 * Returns a vector containing the maximum components of [a] and [b].
 */
inline fun max(a: Float3, b: Float3) = Float3(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z))

/**
 * Transforms each component of the vector [v] using the provided [block].
 */
inline fun transform(v: Float3, block: (Float) -> Float) = v.copy().transform(block)

// Boolean functions omitted for brevity

/**
 * Returns the absolute value of the given vector [v].
 */
inline fun abs(v: Float4) = Float4(abs(v.x), abs(v.y), abs(v.z), abs(v.w))

/**
 * Returns the length of the given vector [v].
 */
inline fun length(v: Float4) = sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w)

/**
 * Returns the squared length of the given vector [v].
 */
inline fun length2(v: Float4) = v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w

/**
 * Returns the distance between two points [a] and [b].
 */
inline fun distance(a: Float4, b: Float4) = length(a - b)

/**
 * Returns the dot product of two vectors.
 */
inline fun dot(a: Float4, b: Float4) = a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w

/**
 * Returns the normalized vector [v] (length is 1.0).
 */
fun normalize(v: Float4): Float4 {
    val l = 1.0f / length(v)
    return Float4(v.x * l, v.y * l, v.z * l, v.w * l)
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Float4, min: Float, max: Float): Float4 {
    return Float4(
            clamp(v.x, min, max),
            clamp(v.y, min, max),
            clamp(v.z, min, max),
            clamp(v.w, min, max)
    )
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Float4, min: Float4, max: Float4): Float4 {
    return Float4(
            clamp(v.x, min.x, max.x),
            clamp(v.y, min.y, max.y),
            clamp(v.z, min.z, max.z),
            clamp(v.w, min.z, max.w)
    )
}

/**
 * Linearly interpolates between [a] and [b] by [x].
 */
inline fun mix(a: Float4, b: Float4, x: Float): Float4 {
    return Float4(
            mix(a.x, b.x, x),
            mix(a.y, b.y, x),
            mix(a.z, b.z, x),
            mix(a.w, b.w, x)
    )
}

/**
 * Linearly interpolates component-wise between [a] and [b] by [x].
 */
inline fun mix(a: Float4, b: Float4, x: Float4): Float4 {
    return Float4(
            mix(a.x, b.x, x.x),
            mix(a.y, b.y, x.y),
            mix(a.z, b.z, x.z),
            mix(a.w, b.w, x.w))
}

/**
 * Returns the minimum component of the vector [v].
 */
inline fun min(v: Float4) = min(v.x, min(v.y, min(v.z, v.w)))

/**
 * Returns a vector containing the minimum components of [a] and [b].
 */
inline fun min(a: Float4, b: Float4): Float4 {
    return Float4(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z), min(a.w, b.w))
}

/**
 * Returns the maximum component of the vector [v].
 */
inline fun max(v: Float4) = max(v.x, max(v.y, max(v.z, v.w)))

/**
 * Returns a vector containing the maximum components of [a] and [b].
 */
inline fun max(a: Float4, b: Float4): Float4 {
    return Float4(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z), max(a.w, b.w))
}

/**
 * Transforms each component of the vector [v] using the provided [block].
 */
inline fun transform(v: Float4, block: (Float) -> Float) = v.copy().transform(block)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than [b].
 */
inline fun lessThan(a: Float4, b: Float) = Bool4(a.x < b, a.y < b, a.z < b, a.w < b)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than the corresponding component of [b].
 */
inline fun lessThan(a: Float4, b: Float4) = Bool4(a.x < b.x, a.y < b.y, a.z < b.z, a.w < b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than or equal to [b].
 */
inline fun lessThanEqual(a: Float4, b: Float) = Bool4(a.x <= b, a.y <= b, a.z <= b, a.w <= b)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than or equal to the corresponding component of [b].
 */
inline fun lessThanEqual(a: Float4, b: Float4) =
    Bool4(a.x <= b.x, a.y <= b.y, a.z <= b.z, a.w <= b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than [b].
 */
inline fun greaterThan(a: Float4, b: Float) = Bool4(a.x > b, a.y > b, a.z > b, a.w > b)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than the corresponding component of [b].
 */
inline fun greaterThan(a: Float4, b: Float4) = Bool4(a.x > b.y, a.y > b.y, a.z > b.z, a.w > b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than or equal to [b].
 */
inline fun greaterThanEqual(a: Float4, b: Float) = Bool4(a.x >= b, a.y >= b, a.z >= b, a.w >= b)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than or equal to the corresponding component of [b].
 */
inline fun greaterThanEqual(a: Float4, b: Float4) =
    Bool4(a.x >= b.x, a.y >= b.y, a.z >= b.z, a.w >= b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is equal to [b] within [delta].
 */
inline fun equal(a: Float4, b: Float, delta: Float = 0.0f) = Bool4(
    a.x.equals(b, delta),
    a.y.equals(b, delta),
    a.z.equals(b, delta),
    a.w.equals(b, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is equal to the corresponding component of [b] within [delta].
 */
inline fun equal(a: Float4, b: Float4, delta: Float = 0.0f) = Bool4(
    a.x.equals(b.x, delta),
    a.y.equals(b.y, delta),
    a.z.equals(b.z, delta),
    a.w.equals(b.w, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is not equal to [b] within [delta].
 */
inline fun notEqual(a: Float4, b: Float, delta: Float = 0.0f) = Bool4(
    !a.x.equals(b, delta),
    !a.y.equals(b, delta),
    !a.z.equals(b, delta),
    !a.w.equals(b, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is not equal to the corresponding component of [b] within [delta].
 */
inline fun notEqual(a: Float4, b: Float4, delta: Float = 0.0f) = Bool4(
    !a.x.equals(b.x, delta),
    !a.y.equals(b.y, delta),
    !a.z.equals(b.z, delta),
    !a.w.equals(b.w, delta)
)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than [b].
 */
inline infix fun Float4.lt(b: Float) = Bool4(x < b, y < b, z < b, w < b)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than the corresponding component of [b].
 */
inline infix fun Float4.lt(b: Float4) = Bool4(x < b.x, y < b.y, z < b.z, w < b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than or equal to [b].
 */
inline infix fun Float4.lte(b: Float) = Bool4(x <= b, y <= b, z <= b, w <= b)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than or equal to the corresponding component of [b].
 */
inline infix fun Float4.lte(b: Float4) = Bool4(x <= b.x, y <= b.y, z <= b.z, w <= b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than [b].
 */
inline infix fun Float4.gt(b: Float) = Bool4(x > b, y > b, z > b, w > b)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than the corresponding component of [b].
 */
inline infix fun Float4.gt(b: Float4) = Bool4(x > b.x, y > b.y, z > b.z, w > b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than or equal to [b].
 */
inline infix fun Float4.gte(b: Float) = Bool4(x >= b, y >= b, z >= b, w >= b)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than or equal to the corresponding component of [b].
 */
inline infix fun Float4.gte(b: Float4) = Bool4(x >= b.x, y >= b.y, z >= b.z, w >= b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is equal to [b].
 */
inline infix fun Float4.eq(b: Float) = Bool4(x == b, y == b, z == b, w == b)

/**
 * Returns a [Bool4] indicating if each component of this vector is equal to the corresponding component of [b].
 */
inline infix fun Float4.eq(b: Float4) = Bool4(x == b.x, y == b.y, z == b.z, w == b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is not equal to [b].
 */
inline infix fun Float4.neq(b: Float) = Bool4(x != b, y != b, z != b, w != b)

/**
 * Returns a [Bool4] indicating if each component of this vector is not equal to the corresponding component of [b].
 */
inline infix fun Float4.neq(b: Float4) = Bool4(x != b.x, y != b.y, z != b.z, w != b.w)

/**
 * Returns true if any component of the vector [v] is true.
 */
inline fun any(v: Bool4) = v.x || v.y || v.z || v.w

/**
 * Returns true if all components of the vector [v] are true.
 */
inline fun all(v: Bool4) = v.x && v.y && v.z && v.w

/**
 * A 2-component vector of booleans.
 *
 * @constructor Creates a new vector with the specified components.
 */
data class Bool2(var x: Boolean = false, var y: Boolean = false) {
    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Bool2) : this(v.x, v.y)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Boolean
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Boolean
        get() = y
        set(value) {
            y = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Boolean
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Boolean
        get() = y
        set(value) {
            y = value
        }

    /**
     * The XY components of the vector as a [Bool2].
     */
    inline var xy: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Bool2].
     */
    inline var rg: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Bool2].
     */
    inline var st: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        else -> throw IllegalArgumentException("index must be X, Y, R, G, S or T")
    }

    /**
     * Returns two components at the specified indices as a [Bool2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Bool2 {
        return Bool2(get(index1), get(index2))
    }

    /**
     * Returns the component at the specified [index] (0 or 1).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("index must be in 0..1")
    }

    /**
     * Returns two components at the specified indices as a [Bool2].
     */
    operator fun get(index1: Int, index2: Int) = Bool2(get(index1), get(index2))

    /**
     * Returns the component at the specified [index] (1 or 2).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0 or 1).
     */
    operator fun set(index: Int, v: Boolean) = when (index) {
        0 -> x = v
        1 -> y = v
        else -> throw IllegalArgumentException("index must be in 0..1")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Boolean) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Boolean) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        else -> throw IllegalArgumentException("index must be X, Y, R, G, S or T")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Boolean) {
        set(index1, v)
        set(index2, v)
    }
}

/**
 * A 3-component vector of booleans.
 *
 * @constructor Creates a new vector with the specified components.
 */
data class Bool3(var x: Boolean = false, var y: Boolean = false, var z: Boolean = false) {
    /**
     * Creates a new vector with the specified [v] and [z].
     */
    constructor(v: Bool2, z: Boolean = false) : this(v.x, v.y, z)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Bool3) : this(v.x, v.y, v.z)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Boolean
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Boolean
        get() = y
        set(value) {
            y = value
        }

    /**
     * The B (blue) component of the vector (alias for [z]).
     */
    inline var b: Boolean
        get() = z
        set(value) {
            z = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Boolean
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Boolean
        get() = y
        set(value) {
            y = value
        }

    /**
     * The P (texture W) component of the vector (alias for [z]).
     */
    inline var p: Boolean
        get() = z
        set(value) {
            z = value
        }

    /**
     * The XY components of the vector as a [Bool2].
     */
    inline var xy: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Bool2].
     */
    inline var rg: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Bool2].
     */
    inline var st: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RGB components of the vector as a [Bool3].
     */
    inline var rgb: Bool3
        get() = Bool3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The XYZ components of the vector as a [Bool3].
     */
    inline var xyz: Bool3
        get() = Bool3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The STP components of the vector as a [Bool3].
     */
    inline var stp: Bool3
        get() = Bool3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z
        else -> throw IllegalArgumentException("index must be X, Y, Z, R, G, B, S, T or P")
    }

    /**
     * Returns two components at the specified indices as a [Bool2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Bool2 {
        return Bool2(get(index1), get(index2))
    }

    /**
     * Returns three components at the specified indices as a [Bool3].
     */
    operator fun get(
            index1: VectorComponent, index2: VectorComponent, index3: VectorComponent): Bool3 {
        return Bool3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns the component at the specified [index] (0, 1 or 2).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("index must be in 0..2")
    }

    /**
     * Returns two components at the specified indices as a [Bool2].
     */
    operator fun get(index1: Int, index2: Int) = Bool2(get(index1), get(index2))

    /**
     * Returns three components at the specified indices as a [Bool3].
     */
    operator fun get(index1: Int, index2: Int, index3: Int): Bool3 {
        return Bool3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns the component at the specified [index] (1 to 3).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0, 1 or 2).
     */
    operator fun set(index: Int, v: Boolean) = when (index) {
        0 -> x = v
        1 -> y = v
        2 -> z = v
        else -> throw IllegalArgumentException("index must be in 0..2")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Boolean) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, v: Boolean) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Boolean) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z = v
        else -> throw IllegalArgumentException("index must be X, Y, Z, R, G, B, S, T or P")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Boolean) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
            index1: VectorComponent, index2: VectorComponent, index3: VectorComponent, v: Boolean) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }
}

/**
 * A 4-component vector of booleans.
 *
 * @constructor Creates a new vector with the specified components.
 */
data class Bool4(
        var x: Boolean = false,
        var y: Boolean = false,
        var z: Boolean = false,
        var w: Boolean = false) {
    /**
     * Creates a new vector with the specified [v], [z] and [w].
     */
    constructor(v: Bool2, z: Boolean = false, w: Boolean = false) : this(v.x, v.y, z, w)

    /**
     * Creates a new vector with the specified [v] and [w].
     */
    constructor(v: Bool3, w: Boolean = false) : this(v.x, v.y, v.z, w)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Bool4) : this(v.x, v.y, v.z, v.w)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Boolean
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Boolean
        get() = y
        set(value) {
            y = value
        }

    /**
     * The B (blue) component of the vector (alias for [z]).
     */
    inline var b: Boolean
        get() = z
        set(value) {
            z = value
        }

    /**
     * The A (alpha) component of the vector (alias for [w]).
     */
    inline var a: Boolean
        get() = w
        set(value) {
            w = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Boolean
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Boolean
        get() = y
        set(value) {
            y = value
        }

    /**
     * The P (texture W) component of the vector (alias for [z]).
     */
    inline var p: Boolean
        get() = z
        set(value) {
            z = value
        }

    /**
     * The Q (texture Q) component of the vector (alias for [w]).
     */
    inline var q: Boolean
        get() = w
        set(value) {
            w = value
        }

    /**
     * The XY components of the vector as a [Bool2].
     */
    inline var xy: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Bool2].
     */
    inline var rg: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Bool2].
     */
    inline var st: Bool2
        get() = Bool2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RGB components of the vector as a [Bool3].
     */
    inline var rgb: Bool3
        get() = Bool3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The XYZ components of the vector as a [Bool3].
     */
    inline var xyz: Bool3
        get() = Bool3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The STP components of the vector as a [Bool3].
     */
    inline var stp: Bool3
        get() = Bool3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The RGBA components of the vector as a [Bool4].
     */
    inline var rgba: Bool4
        get() = Bool4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * The XYZW components of the vector as a [Bool4].
     */
    inline var xyzw: Bool4
        get() = Bool4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * The STPQ components of the vector as a [Bool4].
     */
    inline var stpq: Bool4
        get() = Bool4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z
        VectorComponent.W, VectorComponent.A, VectorComponent.Q -> w
    }

    /**
     * Returns two components at the specified indices as a [Bool2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Bool2 {
        return Bool2(get(index1), get(index2))
    }

    /**
     * Returns three components at the specified indices as a [Bool3].
     */
    operator fun get(
            index1: VectorComponent,
            index2: VectorComponent,
            index3: VectorComponent): Bool3 {
        return Bool3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns four components at the specified indices as a [Bool4].
     */
    operator fun get(
            index1: VectorComponent,
            index2: VectorComponent,
            index3: VectorComponent,
            index4: VectorComponent): Bool4 {
        return Bool4(get(index1), get(index2), get(index3), get(index4))
    }

    /**
     * Returns the component at the specified [index] (0 to 3).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    /**
     * Returns two components at the specified indices as a [Bool2].
     */
    operator fun get(index1: Int, index2: Int) = Bool2(get(index1), get(index2))

    /**
     * Returns three components at the specified indices as a [Bool3].
     */
    operator fun get(index1: Int, index2: Int, index3: Int): Bool3 {
        return Bool3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns four components at the specified indices as a [Bool4].
     */
    operator fun get(index1: Int, index2: Int, index3: Int, index4: Int): Bool4 {
        return Bool4(get(index1), get(index2), get(index3), get(index4))
    }

    /**
     * Returns the component at the specified [index] (1 to 4).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0 to 3).
     */
    operator fun set(index: Int, v: Boolean) = when (index) {
        0 -> x = v
        1 -> y = v
        2 -> z = v
        3 -> w = v
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Boolean) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, v: Boolean) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, index4: Int, v: Boolean) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
        set(index4, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Boolean) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z = v
        VectorComponent.W, VectorComponent.A, VectorComponent.Q -> w = v
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Boolean) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
            index1: VectorComponent, index2: VectorComponent, index3: VectorComponent, v: Boolean) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
            index1: VectorComponent, index2: VectorComponent,
            index3: VectorComponent, index4: VectorComponent, v: Boolean) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
        set(index4, v)
    }
}

/**
 * A 2-component vector of half-precision floats.
 *
 * @constructor Creates a new vector with the specified components.
 */
data class Half2(var x: Half = Half.POSITIVE_ZERO, var y: Half = Half.POSITIVE_ZERO) {
    /**
     * Creates a new vector with the specified [v] for all components.
     */
    constructor(v: Half) : this(v, v)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Half2) : this(v.x, v.y)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Half
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Half
        get() = y
        set(value) {
            y = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Half
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Half
        get() = y
        set(value) {
            y = value
        }

    /**
     * The XY components of the vector as a [Half2].
     */
    inline var xy: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Half2].
     */
    inline var rg: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Half2].
     */
    inline var st: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }
    

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        else -> throw IllegalArgumentException("index must be X, Y, R, G, S or T")
    }

    /**
     * Returns two components at the specified indices as a [Half2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Half2 {
        return Half2(get(index1), get(index2))
    }

    /**
     * Returns the component at the specified [index] (0 or 1).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("index must be in 0..1")
    }

    /**
     * Returns two components at the specified indices as a [Half2].
     */
    operator fun get(index1: Int, index2: Int) = Half2(get(index1), get(index2))

    /**
     * Returns the component at the specified [index] (1 or 2).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0 or 1).
     */
    operator fun set(index: Int, v: Half) = when (index) {
        0 -> x = v
        1 -> y = v
        else -> throw IllegalArgumentException("index must be in 0..1")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Half) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Half) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        else -> throw IllegalArgumentException("index must be X, Y, R, G, S or T")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Half) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Negates all components of this vector.
     */
    operator fun unaryMinus() = Half2(-x, -y)

    /**
     * Increments all components of this vector.
     */
    operator fun inc() = Half2(x++, y++)

    /**
     * Decrements all components of this vector.
     */
    operator fun dec() = Half2(x--, y--)

    /**
     * Adds a scalar to all components of this vector.
     */
    inline operator fun plus(v: Half) = Half2(x + v, y + v)

    /**
     * Subtracts a scalar from all components of this vector.
     */
    inline operator fun minus(v: Half) = Half2(x - v, y - v)

    /**
     * Multiplies all components of this vector by a scalar.
     */
    inline operator fun times(v: Half) = Half2(x * v, y * v)

    /**
     * Divides all components of this vector by a scalar.
     */
    inline operator fun div(v: Half) = Half2(x / v, y / v)

    /**
     * Adds two vectors.
     */
    inline operator fun plus(v: Half2) = Half2(x + v.x, y + v.y)

    /**
     * Subtracts two vectors.
     */
    inline operator fun minus(v: Half2) = Half2(x - v.x, y - v.y)

    /**
     * Multiplies two vectors component-wise.
     */
    inline operator fun times(v: Half2) = Half2(x * v.x, y * v.y)

    /**
     * Divides two vectors component-wise.
     */
    inline operator fun div(v: Half2) = Half2(x / v.x, y / v.y)

    /**
     * Transforms each component of this vector using the provided [block].
     */
    inline fun transform(block: (Half) -> Half): Half2 {
        x = block(x)
        y = block(y)
        return this
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     */
    fun toFloatArray() = floatArrayOf(x.toFloat(), y.toFloat())
}

/**
 * A 3-component vector of half-precision floats.
 *
 * @constructor Creates a new vector with the specified components.
 */
data class Half3(
    var x: Half = Half.POSITIVE_ZERO,
    var y: Half = Half.POSITIVE_ZERO,
    var z: Half = Half.POSITIVE_ZERO
) {
    /**
     * Creates a new vector with the specified [v] for all components.
     */
    constructor(v: Half) : this(v, v, v)

    /**
     * Creates a new vector with the specified [v] and [z].
     */
    constructor(v: Half2, z: Half = Half.POSITIVE_ZERO) : this(v.x, v.y, z)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Half3) : this(v.x, v.y, v.z)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Half
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Half
        get() = y
        set(value) {
            y = value
        }

    /**
     * The B (blue) component of the vector (alias for [z]).
     */
    inline var b: Half
        get() = z
        set(value) {
            z = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Half
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Half
        get() = y
        set(value) {
            y = value
        }

    /**
     * The P (texture W) component of the vector (alias for [z]).
     */
    inline var p: Half
        get() = z
        set(value) {
            z = value
        }

    /**
     * The XY components of the vector as a [Half2].
     */
    inline var xy: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Half2].
     */
    inline var rg: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Half2].
     */
    inline var st: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RGB components of the vector as a [Half3].
     */
    inline var rgb: Half3
        get() = Half3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The XYZ components of the vector as a [Half3].
     */
    inline var xyz: Half3
        get() = Half3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The STP components of the vector as a [Half3].
     */
    inline var stp: Half3
        get() = Half3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z
        else -> throw IllegalArgumentException("index must be X, Y, Z, R, G, B, S, T or P")
    }

    /**
     * Returns two components at the specified indices as a [Half2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Half2 {
        return Half2(get(index1), get(index2))
    }

    /**
     * Returns three components at the specified indices as a [Half3].
     */
    operator fun get(
        index1: VectorComponent, index2: VectorComponent, index3: VectorComponent): Half3 {
        return Half3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns the component at the specified [index] (0, 1 or 2).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("index must be in 0..2")
    }

    /**
     * Returns two components at the specified indices as a [Half2].
     */
    operator fun get(index1: Int, index2: Int) = Half2(get(index1), get(index2))

    /**
     * Returns three components at the specified indices as a [Half3].
     */
    operator fun get(index1: Int, index2: Int, index3: Int): Half3 {
        return Half3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns the component at the specified [index] (1 to 3).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0, 1 or 2).
     */
    operator fun set(index: Int, v: Half) = when (index) {
        0 -> x = v
        1 -> y = v
        2 -> z = v
        else -> throw IllegalArgumentException("index must be in 0..2")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Half) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, v: Half) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Half) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z = v
        else -> throw IllegalArgumentException("index must be X, Y, Z, R, G, B, S, T or P")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Half) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
        index1: VectorComponent, index2: VectorComponent, index3: VectorComponent, v: Half) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Negates all components of this vector.
     */
    operator fun unaryMinus() = Half3(-x, -y, -z)

    /**
     * Increments all components of this vector.
     */
    operator fun inc() = Half3(x++, y++, z++)

    /**
     * Decrements all components of this vector.
     */
    operator fun dec() = Half3(x--, y--, z--)

    /**
     * Adds a scalar to all components of this vector.
     */
    inline operator fun plus(v: Half) = Half3(x + v, y + v, z + v)

    /**
     * Subtracts a scalar from all components of this vector.
     */
    inline operator fun minus(v: Half) = Half3(x - v, y - v, z - v)

    /**
     * Multiplies all components of this vector by a scalar.
     */
    inline operator fun times(v: Half) = Half3(x * v, y * v, z * v)

    /**
     * Divides all components of this vector by a scalar.
     */
    inline operator fun div(v: Half) = Half3(x / v, y / v, z / v)

    /**
     * Adds a [Half2] to this vector (z is unchanged).
     */
    inline operator fun plus(v: Half2) = Half3(x + v.x, y + v.y, z)

    /**
     * Subtracts a [Half2] from this vector (z is unchanged).
     */
    inline operator fun minus(v: Half2) = Half3(x - v.x, y - v.y, z)

    /**
     * Multiplies this vector by a [Half2] (z is unchanged).
     */
    inline operator fun times(v: Half2) = Half3(x * v.x, y * v.y, z)

    /**
     * Divides this vector by a [Half2] (z is unchanged).
     */
    inline operator fun div(v: Half2) = Half3(x / v.x, y / v.y, z)

    /**
     * Adds two vectors.
     */
    inline operator fun plus(v: Half3) = Half3(x + v.x, y + v.y, z + v.z)

    /**
     * Subtracts two vectors.
     */
    inline operator fun minus(v: Half3) = Half3(x - v.x, y - v.y, z - v.z)

    /**
     * Multiplies two vectors component-wise.
     */
    inline operator fun times(v: Half3) = Half3(x * v.x, y * v.y, z * v.z)

    /**
     * Divides two vectors component-wise.
     */
    inline operator fun div(v: Half3) = Half3(x / v.x, y / v.y, z / v.z)

    /**
     * Transforms each component of this vector using the provided [block].
     */
    inline fun transform(block: (Half) -> Half): Half3 {
        x = block(x)
        y = block(y)
        z = block(z)
        return this
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     */
    fun toFloatArray() = floatArrayOf(x.toFloat(), y.toFloat(), z.toFloat())
}

/**
 * A 4-component vector of half-precision floats.
 *
 * @constructor Creates a new vector with the specified components.
 */
data class Half4(
    var x: Half = Half.POSITIVE_ZERO,
    var y: Half = Half.POSITIVE_ZERO,
    var z: Half = Half.POSITIVE_ZERO,
    var w: Half = Half.POSITIVE_ZERO
) {
    /**
     * Creates a new vector with the specified [v] for all components.
     */
    constructor(v: Half) : this(v, v, v, v)

    /**
     * Creates a new vector with the specified [v], [z] and [w].
     */
    constructor(v: Half2, z: Half = Half.POSITIVE_ZERO, w: Half = Half.POSITIVE_ZERO) : this(v.x, v.y, z, w)

    /**
     * Creates a new vector with the specified [v] and [w].
     */
    constructor(v: Half3, w: Half = Half.POSITIVE_ZERO) : this(v.x, v.y, v.z, w)

    /**
     * Creates a new vector from the specified vector [v].
     */
    constructor(v: Half4) : this(v.x, v.y, v.z, v.w)

    /**
     * The R (red) component of the vector (alias for [x]).
     */
    inline var r: Half
        get() = x
        set(value) {
            x = value
        }

    /**
     * The G (green) component of the vector (alias for [y]).
     */
    inline var g: Half
        get() = y
        set(value) {
            y = value
        }

    /**
     * The B (blue) component of the vector (alias for [z]).
     */
    inline var b: Half
        get() = z
        set(value) {
            z = value
        }

    /**
     * The A (alpha) component of the vector (alias for [w]).
     */
    inline var a: Half
        get() = w
        set(value) {
            w = value
        }

    /**
     * The S (texture U) component of the vector (alias for [x]).
     */
    inline var s: Half
        get() = x
        set(value) {
            x = value
        }

    /**
     * The T (texture V) component of the vector (alias for [y]).
     */
    inline var t: Half
        get() = y
        set(value) {
            y = value
        }

    /**
     * The P (texture W) component of the vector (alias for [z]).
     */
    inline var p: Half
        get() = z
        set(value) {
            z = value
        }

    /**
     * The Q (texture Q) component of the vector (alias for [w]).
     */
    inline var q: Half
        get() = w
        set(value) {
            w = value
        }

    /**
     * The XY components of the vector as a [Half2].
     */
    inline var xy: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RG components of the vector as a [Half2].
     */
    inline var rg: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The ST components of the vector as a [Half2].
     */
    inline var st: Half2
        get() = Half2(x, y)
        set(value) {
            x = value.x
            y = value.y
        }

    /**
     * The RGB components of the vector as a [Half3].
     */
    inline var rgb: Half3
        get() = Half3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The XYZ components of the vector as a [Half3].
     */
    inline var xyz: Half3
        get() = Half3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The STP components of the vector as a [Half3].
     */
    inline var stp: Half3
        get() = Half3(x, y, z)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
        }

    /**
     * The RGBA components of the vector as a [Half4].
     */
    inline var rgba: Half4
        get() = Half4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * The XYZW components of the vector as a [Half4].
     */
    inline var xyzw: Half4
        get() = Half4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * The STPQ components of the vector as a [Half4].
     */
    inline var stpq: Half4
        get() = Half4(x, y, z, w)
        set(value) {
            x = value.x
            y = value.y
            z = value.z
            w = value.w
        }

    /**
     * Returns the component at the specified [index].
     */
    operator fun get(index: VectorComponent) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z
        VectorComponent.W, VectorComponent.A, VectorComponent.Q -> w
    }

    /**
     * Returns two components at the specified indices as a [Half2].
     */
    operator fun get(index1: VectorComponent, index2: VectorComponent): Half2 {
        return Half2(get(index1), get(index2))
    }

    /**
     * Returns three components at the specified indices as a [Half3].
     */
    operator fun get(
        index1: VectorComponent,
        index2: VectorComponent,
        index3: VectorComponent): Half3 {
        return Half3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns four components at the specified indices as a [Half4].
     */
    operator fun get(
        index1: VectorComponent,
        index2: VectorComponent,
        index3: VectorComponent,
        index4: VectorComponent): Half4 {
        return Half4(get(index1), get(index2), get(index3), get(index4))
    }

    /**
     * Returns the component at the specified [index] (0 to 3).
     */
    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    /**
     * Returns two components at the specified indices as a [Half2].
     */
    operator fun get(index1: Int, index2: Int) = Half2(get(index1), get(index2))

    /**
     * Returns three components at the specified indices as a [Half3].
     */
    operator fun get(index1: Int, index2: Int, index3: Int): Half3 {
        return Half3(get(index1), get(index2), get(index3))
    }

    /**
     * Returns four components at the specified indices as a [Half4].
     */
    operator fun get(index1: Int, index2: Int, index3: Int, index4: Int): Half4 {
        return Half4(get(index1), get(index2), get(index3), get(index4))
    }

    /**
     * Returns the component at the specified [index] (1 to 4).
     */
    inline operator fun invoke(index: Int) = get(index - 1)

    /**
     * Sets the component at the specified [index] (0 to 3).
     */
    operator fun set(index: Int, v: Half) = when (index) {
        0 -> x = v
        1 -> y = v
        2 -> z = v
        3 -> w = v
        else -> throw IllegalArgumentException("index must be in 0..3")
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, v: Half) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, v: Half) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: Int, index2: Int, index3: Int, index4: Int, v: Half) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
        set(index4, v)
    }

    /**
     * Sets the component at the specified [index].
     */
    operator fun set(index: VectorComponent, v: Half) = when (index) {
        VectorComponent.X, VectorComponent.R, VectorComponent.S -> x = v
        VectorComponent.Y, VectorComponent.G, VectorComponent.T -> y = v
        VectorComponent.Z, VectorComponent.B, VectorComponent.P -> z = v
        VectorComponent.W, VectorComponent.A, VectorComponent.Q -> w = v
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(index1: VectorComponent, index2: VectorComponent, v: Half) {
        set(index1, v)
        set(index2, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
        index1: VectorComponent, index2: VectorComponent, index3: VectorComponent, v: Half) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
    }

    /**
     * Sets the components at the specified indices.
     */
    operator fun set(
        index1: VectorComponent, index2: VectorComponent,
        index3: VectorComponent, index4: VectorComponent, v: Half) {
        set(index1, v)
        set(index2, v)
        set(index3, v)
        set(index4, v)
    }

    /**
     * Negates all components of this vector.
     */
    operator fun unaryMinus() = Half4(-x, -y, -z, -w)

    /**
     * Increments all components of this vector.
     */
    operator fun inc() = Half4(x++, y++, z++, w++)

    /**
     * Decrements all components of this vector.
     */
    operator fun dec() = Half4(x--, y--, z--, w--)

    /**
     * Adds a scalar to all components of this vector.
     */
    inline operator fun plus(v: Half) = Half4(x + v, y + v, z + v, w + v)

    /**
     * Subtracts a scalar from all components of this vector.
     */
    inline operator fun minus(v: Half) = Half4(x - v, y - v, z - v, w - v)

    /**
     * Multiplies all components of this vector by a scalar.
     */
    inline operator fun times(v: Half) = Half4(x * v, y * v, z * v, w * v)

    /**
     * Divides all components of this vector by a scalar.
     */
    inline operator fun div(v: Half) = Half4(x / v, y / v, z / v, w / v)

    /**
     * Adds a [Half2] to this vector (z and w are unchanged).
     */
    inline operator fun plus(v: Half2) = Half4(x + v.x, y + v.y, z, w)

    /**
     * Subtracts a [Half2] from this vector (z and w are unchanged).
     */
    inline operator fun minus(v: Half2) = Half4(x - v.x, y - v.y, z, w)

    /**
     * Multiplies this vector by a [Half2] (z and w are unchanged).
     */
    inline operator fun times(v: Half2) = Half4(x * v.x, y * v.y, z, w)

    /**
     * Divides this vector by a [Half2] (z and w are unchanged).
     */
    inline operator fun div(v: Half2) = Half4(x / v.x, y / v.y, z, w)

    /**
     * Adds a [Half3] to this vector (w is unchanged).
     */
    inline operator fun plus(v: Half3) = Half4(x + v.x, y + v.y, z + v.z, w)

    /**
     * Subtracts a [Half3] from this vector (w is unchanged).
     */
    inline operator fun minus(v: Half3) = Half4(x - v.x, y - v.y, z - v.z, w)

    /**
     * Multiplies this vector by a [Half3] (w is unchanged).
     */
    inline operator fun times(v: Half3) = Half4(x * v.x, y * v.y, z * v.z, w)

    /**
     * Divides this vector by a [Half3] (w is unchanged).
     */
    inline operator fun div(v: Half3) = Half4(x / v.x, y / v.y, z / v.z, w)

    /**
     * Adds two vectors.
     */
    inline operator fun plus(v: Half4) = Half4(x + v.x, y + v.y, z + v.z, w + v.w)

    /**
     * Subtracts two vectors.
     */
    inline operator fun minus(v: Half4) = Half4(x - v.x, y - v.y, z - v.z, w - v.w)

    /**
     * Multiplies two vectors component-wise.
     */
    inline operator fun times(v: Half4) = Half4(x * v.x, y * v.y, z * v.z, w * v.w)

    /**
     * Divides two vectors component-wise.
     */
    inline operator fun div(v: Half4) = Half4(x / v.x, y / v.y, z / v.z, w / v.w)

    /**
     * Transforms each component of this vector using the provided [block].
     */
    inline fun transform(block: (Half) -> Half): Half4 {
        x = block(x)
        y = block(y)
        z = block(z)
        w = block(w)
        return this
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     */
    fun toFloatArray() = floatArrayOf(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())
}

/**
 * Adds a [Half2] to a scalar.
 */
inline operator fun Half.plus(v: Half2) = Half2(this + v.x, this + v.y)

/**
 * Subtracts a [Half2] from a scalar.
 */
inline operator fun Half.minus(v: Half2) = Half2(this - v.x, this - v.y)

/**
 * Multiplies a scalar by a [Half2].
 */
inline operator fun Half.times(v: Half2) = Half2(this * v.x, this * v.y)

/**
 * Divides a scalar by a [Half2].
 */
inline operator fun Half.div(v: Half2) = Half2(this / v.x, this / v.y)

/**
 * Returns the minimum component of the vector [v].
 */
inline fun min(v: Half2) = min(v.x, v.y)

/**
 * Returns a vector containing the minimum components of [a] and [b].
 */
inline fun min(a: Half2, b: Half2) = Half2(min(a.x, b.x), min(a.y, b.y))

/**
 * Returns the maximum component of the vector [v].
 */
inline fun max(v: Half2) = max(v.x, v.y)

/**
 * Returns a vector containing the maximum components of [a] and [b].
 */
inline fun max(a: Half2, b: Half2) = Half2(max(a.x, b.x), max(a.y, b.y))

/**
 * Transforms each component of the vector [v] using the provided [block].
 */
inline fun transform(v: Half2, block: (Half) -> Half) = v.copy().transform(block)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than [b].
 */
inline fun lessThan(a: Half2, b: Half) = Bool2(a.x < b, a.y < b)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than the corresponding component of [b].
 */
inline fun lessThan(a: Half2, b: Half2) = Bool2(a.x < b.x, a.y < b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than or equal to [b].
 */
inline fun lessThanEqual(a: Half2, b: Half) = Bool2(a.x <= b, a.y <= b)

/**
 * Returns a [Bool2] indicating if each component of [a] is less than or equal to the corresponding component of [b].
 */
inline fun lessThanEqual(a: Half2, b: Half2) = Bool2(a.x <= b.x, a.y <= b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than [b].
 */
inline fun greaterThan(a: Half2, b: Half) = Bool2(a.x > b, a.y > b)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than the corresponding component of [b].
 */
inline fun greaterThan(a: Half2, b: Half2) = Bool2(a.x > b.y, a.y > b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than or equal to [b].
 */
inline fun greaterThanEqual(a: Half2, b: Half) = Bool2(a.x >= b, a.y >= b)

/**
 * Returns a [Bool2] indicating if each component of [a] is greater than or equal to the corresponding component of [b].
 */
inline fun greaterThanEqual(a: Half2, b: Half2) = Bool2(a.x >= b.x, a.y >= b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is equal to [b].
 */
inline fun equal(a: Half2, b: Half) = Bool2(a.x == b, a.y == b)

/**
 * Returns a [Bool2] indicating if each component of [a] is equal to the corresponding component of [b].
 */
inline fun equal(a: Half2, b: Half2) = Bool2(a.x == b.x, a.y == b.y)

/**
 * Returns a [Bool2] indicating if each component of [a] is not equal to [b].
 */
inline fun notEqual(a: Half2, b: Half) = Bool2(a.x != b, a.y != b)

/**
 * Returns a [Bool2] indicating if each component of [a] is not equal to the corresponding component of [b].
 */
inline fun notEqual(a: Half2, b: Half2) = Bool2(a.x != b.x, a.y != b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than [b].
 */
inline infix fun Half2.lt(b: Half) = Bool2(x < b, y < b)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than the corresponding component of [b].
 */
inline infix fun Half2.lt(b: Half2) = Bool2(x < b.x, y < b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than or equal to [b].
 */
inline infix fun Half2.lte(b: Half) = Bool2(x <= b, y <= b)

/**
 * Returns a [Bool2] indicating if each component of this vector is less than or equal to the corresponding component of [b].
 */
inline infix fun Half2.lte(b: Half2) = Bool2(x <= b.x, y <= b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than [b].
 */
inline infix fun Half2.gt(b: Half) = Bool2(x > b, y > b)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than the corresponding component of [b].
 */
inline infix fun Half2.gt(b: Half2) = Bool2(x > b.x, y > b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than or equal to [b].
 */
inline infix fun Half2.gte(b: Half) = Bool2(x >= b, y >= b)

/**
 * Returns a [Bool2] indicating if each component of this vector is greater than or equal to the corresponding component of [b].
 */
inline infix fun Half2.gte(b: Half2) = Bool2(x >= b.x, y >= b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is equal to [b].
 */
inline infix fun Half2.eq(b: Half) = Bool2(x == b, y == b)

/**
 * Returns a [Bool2] indicating if each component of this vector is equal to the corresponding component of [b].
 */
inline infix fun Half2.eq(b: Half2) = Bool2(x == b.x, y == b.y)

/**
 * Returns a [Bool2] indicating if each component of this vector is not equal to [b].
 */
inline infix fun Half2.neq(b: Half) = Bool2(x != b, y != b)

/**
 * Returns a [Bool2] indicating if each component of this vector is not equal to the corresponding component of [b].
 */
inline infix fun Half2.neq(b: Half2) = Bool2(x != b.x, y != b.y)

/**
 * Adds a [Half3] to a scalar.
 */
inline operator fun Half.plus(v: Half3) = Half3(this + v.x, this + v.y, this + v.z)

/**
 * Subtracts a [Half3] from a scalar.
 */
inline operator fun Half.minus(v: Half3) = Half3(this - v.x, this - v.y, this - v.z)

/**
 * Multiplies a scalar by a [Half3].
 */
inline operator fun Half.times(v: Half3) = Half3(this * v.x, this * v.y, this * v.z)

/**
 * Divides a scalar by a [Half3].
 */
inline operator fun Half.div(v: Half3) = Half3(this / v.x, this / v.y, this / v.z)

/**
 * Returns the absolute value of the given vector [v].
 */
inline fun abs(v: Half3) = Half3(abs(v.x), abs(v.y), abs(v.z))

/**
 * Returns the length of the given vector [v].
 */
inline fun length(v: Half3) = sqrt(v.x * v.x + v.y * v.y + v.z * v.z)

/**
 * Returns the squared length of the given vector [v].
 */
inline fun length2(v: Half3) = v.x * v.x + v.y * v.y + v.z * v.z

/**
 * Returns the distance between two points [a] and [b].
 */
inline fun distance(a: Half3, b: Half3) = length(a - b)

/**
 * Returns the dot product of two vectors.
 */
inline fun dot(a: Half3, b: Half3) = a.x * b.x + a.y * b.y + a.z * b.z

/**
 * Returns the cross product of two vectors.
 */
inline fun cross(a: Half3, b: Half3): Half3 {
    return Half3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x)
}

/**
 * Returns the cross product of two vectors.
 */
inline infix fun Half3.x(v: Half3): Half3  = cross(this, v)

/**
 * Returns the normalized vector [v] (length is 1.0).
 */
fun normalize(v: Half3): Half3 {
    val l = HALF_ONE / length(v)
    return Half3(v.x * l, v.y * l, v.z * l)
}

/**
 * Returns the reflection vector of [i] against the normal [n].
 */
inline fun reflect(i: Half3, n: Half3) = i - HALF_TWO * dot(n, i) * n

/**
 * Returns the refraction vector of [i] against the normal [n] with the given refractive index [eta].
 */
fun refract(i: Half3, n: Half3, eta: Half): Half3 {
    val d = dot(n, i)
    val k = HALF_ONE - eta * eta * (HALF_ONE - sqr(d))
    return if (k < Half.POSITIVE_ZERO) Half3() else eta * i - (eta * d + sqrt(k)) * n
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Half3, min: Half, max: Half): Half3 {
    return Half3(
        clamp(v.x, min, max),
        clamp(v.y, min, max),
        clamp(v.z, min, max)
    )
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Half3, min: Half3, max: Half3): Half3 {
    return Half3(
        clamp(v.x, min.x, max.x),
        clamp(v.y, min.y, max.y),
        clamp(v.z, min.z, max.z)
    )
}

/**
 * Linearly interpolates between [a] and [b] by [x].
 */
inline fun mix(a: Half3, b: Half3, x: Half): Half3 {
    return Half3(
        mix(a.x, b.x, x),
        mix(a.y, b.y, x),
        mix(a.z, b.z, x)
    )
}

/**
 * Linearly interpolates component-wise between [a] and [b] by [x].
 */
inline fun mix(a: Half3, b: Half3, x: Half3): Half3 {
    return Half3(
        mix(a.x, b.x, x.x),
        mix(a.y, b.y, x.y),
        mix(a.z, b.z, x.z)
    )
}

/**
 * Returns the minimum component of the vector [v].
 */
inline fun min(v: Half3) = min(v.x, min(v.y, v.z))

/**
 * Returns a vector containing the minimum components of [a] and [b].
 */
inline fun min(a: Half3, b: Half3) = Half3(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z))

/**
 * Returns the maximum component of the vector [v].
 */
inline fun max(v: Half3) = max(v.x, max(v.y, v.z))

/**
 * Returns a vector containing the maximum components of [a] and [b].
 */
inline fun max(a: Half3, b: Half3) = Half3(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z))

/**
 * Transforms each component of the vector [v] using the provided [block].
 */
inline fun transform(v: Half3, block: (Half) -> Half) = v.copy().transform(block)

/**
 * Returns a [Bool3] indicating if each component of [a] is less than [b].
 */
inline fun lessThan(a: Half3, b: Half) = Bool3(a.x < b, a.y < b, a.z < b)

/**
 * Returns a [Bool3] indicating if each component of [a] is less than the corresponding component of [b].
 */
inline fun lessThan(a: Half3, b: Half3) = Bool3(a.x < b.x, a.y < b.y, a.z < b.z)

/**
 * Returns a [Bool3] indicating if each component of [a] is less than or equal to [b].
 */
inline fun lessThanEqual(a: Half3, b: Half) = Bool3(a.x <= b, a.y <= b, a.z <= b)

/**
 * Returns a [Bool3] indicating if each component of [a] is less than or equal to the corresponding component of [b].
 */
inline fun lessThanEqual(a: Half3, b: Half3) = Bool3(a.x <= b.x, a.y <= b.y, a.z <= b.z)

/**
 * Returns a [Bool3] indicating if each component of [a] is greater than [b].
 */
inline fun greaterThan(a: Half3, b: Half) = Bool3(a.x > b, a.y > b, a.z > b)

/**
 * Returns a [Bool3] indicating if each component of [a] is greater than the corresponding component of [b].
 */
inline fun greaterThan(a: Half3, b: Half3) = Bool3(a.x > b.y, a.y > b.y, a.z > b.z)

/**
 * Returns a [Bool3] indicating if each component of [a] is greater than or equal to [b].
 */
inline fun greaterThanEqual(a: Half3, b: Half) = Bool3(a.x >= b, a.y >= b, a.z >= b)

/**
 * Returns a [Bool3] indicating if each component of [a] is greater than or equal to the corresponding component of [b].
 */
inline fun greaterThanEqual(a: Half3, b: Half3) = Bool3(a.x >= b.x, a.y >= b.y, a.z >= b.z)

/**
 * Returns a [Bool3] indicating if each component of [a] is equal to [b].
 */
inline fun equal(a: Half3, b: Half) = Bool3(a.x == b, a.y == b, a.z == b)

/**
 * Returns a [Bool3] indicating if each component of [a] is equal to the corresponding component of [b].
 */
inline fun equal(a: Half3, b: Half3) = Bool3(a.x == b.x, a.y == b.y, a.z == b.z)

/**
 * Returns a [Bool3] indicating if each component of [a] is not equal to [b].
 */
inline fun notEqual(a: Half3, b: Half) = Bool3(a.x != b, a.y != b, a.z != b)

/**
 * Returns a [Bool3] indicating if each component of [a] is not equal to the corresponding component of [b].
 */
inline fun notEqual(a: Half3, b: Half3) = Bool3(a.x != b.x, a.y != b.y, a.z != b.z)

/**
 * Returns a [Bool3] indicating if each component of this vector is less than [b].
 */
inline infix fun Half3.lt(b: Half) = Bool3(x < b, y < b, z < b)

/**
 * Returns a [Bool3] indicating if each component of this vector is less than the corresponding component of [b].
 */
inline infix fun Half3.lt(b: Half3) = Bool3(x < b.x, y < b.y, z < b.z)

/**
 * Returns a [Bool3] indicating if each component of this vector is less than or equal to [b].
 */
inline infix fun Half3.lte(b: Half) = Bool3(x <= b, y <= b, z <= b)

/**
 * Returns a [Bool3] indicating if each component of this vector is less than or equal to the corresponding component of [b].
 */
inline infix fun Half3.lte(b: Half3) = Bool3(x <= b.x, y <= b.y, z <= b.z)

/**
 * Returns a [Bool3] indicating if each component of this vector is greater than [b].
 */
inline infix fun Half3.gt(b: Half) = Bool3(x > b, y > b, z > b)

/**
 * Returns a [Bool3] indicating if each component of this vector is greater than the corresponding component of [b].
 */
inline infix fun Half3.gt(b: Half3) = Bool3(x > b.x, y > b.y, z > b.z)

/**
 * Returns a [Bool3] indicating if each component of this vector is greater than or equal to [b].
 */
inline infix fun Half3.gte(b: Half) = Bool3(x >= b, y >= b, z >= b)

/**
 * Returns a [Bool3] indicating if each component of this vector is greater than or equal to the corresponding component of [b].
 */
inline infix fun Half3.gte(b: Half3) = Bool3(x >= b.x, y >= b.y, z >= b.z)

/**
 * Returns a [Bool3] indicating if each component of this vector is equal to [b].
 */
inline infix fun Half3.eq(b: Half) = Bool3(x == b, y == b, z == b)

/**
 * Returns a [Bool3] indicating if each component of this vector is equal to the corresponding component of [b].
 */
inline infix fun Half3.eq(b: Half3) = Bool3(x == b.x, y == b.y, z == b.z)

/**
 * Returns a [Bool3] indicating if each component of this vector is not equal to [b].
 */
inline infix fun Half3.neq(b: Half) = Bool3(x != b, y != b, z != b)

/**
 * Returns a [Bool3] indicating if each component of this vector is not equal to the corresponding component of [b].
 */
inline infix fun Half3.neq(b: Half3) = Bool3(x != b.x, y != b.y, z != b.z)

/**
 * Adds a [Half4] to a scalar.
 */
inline operator fun Half.plus(v: Half4) = Half4(this + v.x, this + v.y, this + v.z, this + v.w)

/**
 * Subtracts a [Half4] from a scalar.
 */
inline operator fun Half.minus(v: Half4) = Half4(this - v.x, this - v.y, this - v.z, this - v.w)

/**
 * Multiplies a scalar by a [Half4].
 */
inline operator fun Half.times(v: Half4) = Half4(this * v.x, this * v.y, this * v.z, this * v.w)

/**
 * Divides a scalar by a [Half4].
 */
inline operator fun Half.div(v: Half4) = Half4(this / v.x, this / v.y, this / v.z, this / v.w)

/**
 * Returns the absolute value of the given vector [v].
 */
inline fun abs(v: Half4) = Half4(abs(v.x), abs(v.y), abs(v.z), abs(v.w))

/**
 * Returns the length of the given vector [v].
 */
inline fun length(v: Half4) = sqrt(v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w)

/**
 * Returns the squared length of the given vector [v].
 */
inline fun length2(v: Half4) = v.x * v.x + v.y * v.y + v.z * v.z + v.w * v.w

/**
 * Returns the distance between two points [a] and [b].
 */
inline fun distance(a: Half4, b: Half4) = length(a - b)

/**
 * Returns the dot product of two vectors.
 */
inline fun dot(a: Half4, b: Half4) = a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w

/**
 * Returns the normalized vector [v] (length is 1.0).
 */
fun normalize(v: Half4): Half4 {
    val l = HALF_ONE / length(v)
    return Half4(v.x * l, v.y * l, v.z * l, v.w * l)
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Half4, min: Half, max: Half): Half4 {
    return Half4(
        clamp(v.x, min, max),
        clamp(v.y, min, max),
        clamp(v.z, min, max),
        clamp(v.w, min, max)
    )
}

/**
 * Clamps the vector [v] component-wise between [min] and [max].
 */
inline fun clamp(v: Half4, min: Half4, max: Half4): Half4 {
    return Half4(
        clamp(v.x, min.x, max.x),
        clamp(v.y, min.y, max.y),
        clamp(v.z, min.z, max.z),
        clamp(v.w, min.z, max.w)
    )
}

/**
 * Linearly interpolates between [a] and [b] by [x].
 */
inline fun mix(a: Half4, b: Half4, x: Half): Half4 {
    return Half4(
        mix(a.x, b.x, x),
        mix(a.y, b.y, x),
        mix(a.z, b.z, x),
        mix(a.w, b.w, x)
    )
}

/**
 * Linearly interpolates component-wise between [a] and [b] by [x].
 */
inline fun mix(a: Half4, b: Half4, x: Half4): Half4 {
    return Half4(
        mix(a.x, b.x, x.x),
        mix(a.y, b.y, x.y),
        mix(a.z, b.z, x.z),
        mix(a.w, b.w, x.w))
}

/**
 * Returns the minimum component of the vector [v].
 */
inline fun min(v: Half4) = min(v.x, min(v.y, min(v.z, v.w)))

/**
 * Returns a vector containing the minimum components of [a] and [b].
 */
inline fun min(a: Half4, b: Half4): Half4 {
    return Half4(min(a.x, b.x), min(a.y, b.y), min(a.z, b.z), min(a.w, b.w))
}

/**
 * Returns the maximum component of the vector [v].
 */
inline fun max(v: Half4) = max(v.x, max(v.y, max(v.z, v.w)))

/**
 * Returns a vector containing the maximum components of [a] and [b].
 */
inline fun max(a: Half4, b: Half4): Half4 {
    return Half4(max(a.x, b.x), max(a.y, b.y), max(a.z, b.z), max(a.w, b.w))
}

/**
 * Transforms each component of the vector [v] using the provided [block].
 */
inline fun transform(v: Half4, block: (Half) -> Half) = v.copy().transform(block)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than [b].
 */
inline fun lessThan(a: Half4, b: Half) = Bool4(a.x < b, a.y < b, a.z < b, a.w < b)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than the corresponding component of [b].
 */
inline fun lessThan(a: Half4, b: Half4) = Bool4(a.x < b.x, a.y < b.y, a.z < b.z, a.w < b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than or equal to [b].
 */
inline fun lessThanEqual(a: Half4, b: Half) = Bool4(a.x <= b, a.y <= b, a.z <= b, a.w <= b)

/**
 * Returns a [Bool4] indicating if each component of [a] is less than or equal to the corresponding component of [b].
 */
inline fun lessThanEqual(a: Half4, b: Half4) = Bool4(a.x <= b.x, a.y <= b.y, a.z <= b.z, a.w <= b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than [b].
 */
inline fun greaterThan(a: Half4, b: Half) = Bool4(a.x > b, a.y > b, a.z > b, a.w > b)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than the corresponding component of [b].
 */
inline fun greaterThan(a: Half4, b: Half4) = Bool4(a.x > b.y, a.y > b.y, a.z > b.z, a.w > b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than or equal to [b].
 */
inline fun greaterThanEqual(a: Half4, b: Half) = Bool4(a.x >= b, a.y >= b, a.z >= b, a.w >= b)

/**
 * Returns a [Bool4] indicating if each component of [a] is greater than or equal to the corresponding component of [b].
 */
inline fun greaterThanEqual(a: Half4, b: Half4) = Bool4(a.x >= b.x, a.y >= b.y, a.z >= b.z, a.w >= b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is equal to [b].
 */
inline fun equal(a: Half4, b: Half) = Bool4(a.x == b, a.y == b, a.z == b, a.w == b)

/**
 * Returns a [Bool4] indicating if each component of [a] is equal to the corresponding component of [b].
 */
inline fun equal(a: Half4, b: Half4) = Bool4(a.x == b.x, a.y == b.y, a.z == b.z, a.w == b.w)

/**
 * Returns a [Bool4] indicating if each component of [a] is not equal to [b].
 */
inline fun notEqual(a: Half4, b: Half) = Bool4(a.x != b, a.y != b, a.z != b, a.w != b)

/**
 * Returns a [Bool4] indicating if each component of [a] is not equal to the corresponding component of [b].
 */
inline fun notEqual(a: Half4, b: Half4) = Bool4(a.x != b.x, a.y != b.y, a.z != b.z, a.w != b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than [b].
 */
inline infix fun Half4.lt(b: Half) = Bool4(x < b, y < b, z < b, a < b)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than the corresponding component of [b].
 */
inline infix fun Half4.lt(b: Half4) = Bool4(x < b.x, y < b.y, z < b.z, w < b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than or equal to [b].
 */
inline infix fun Half4.lte(b: Half) = Bool4(x <= b, y <= b, z <= b, w <= b)

/**
 * Returns a [Bool4] indicating if each component of this vector is less than or equal to the corresponding component of [b].
 */
inline infix fun Half4.lte(b: Half4) = Bool4(x <= b.x, y <= b.y, z <= b.z, w <= b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than [b].
 */
inline infix fun Half4.gt(b: Half) = Bool4(x > b, y > b, z > b, w > b)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than the corresponding component of [b].
 */
inline infix fun Half4.gt(b: Half4) = Bool4(x > b.x, y > b.y, z > b.z, w > b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than or equal to [b].
 */
inline infix fun Half4.gte(b: Half) = Bool4(x >= b, y >= b, z >= b, w >= b)

/**
 * Returns a [Bool4] indicating if each component of this vector is greater than or equal to the corresponding component of [b].
 */
inline infix fun Half4.gte(b: Half4) = Bool4(x >= b.x, y >= b.y, z >= b.z, w >= b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is equal to [b].
 */
inline infix fun Half4.eq(b: Half) = Bool4(x == b, y == b, z == b, w == b)

/**
 * Returns a [Bool4] indicating if each component of this vector is equal to the corresponding component of [b].
 */
inline infix fun Half4.eq(b: Half4) = Bool4(x == b.x, y == b.y, z == b.z, w == b.w)

/**
 * Returns a [Bool4] indicating if each component of this vector is not equal to [b].
 */
inline infix fun Half4.neq(b: Half) = Bool4(x != b, y != b, z != b, w != b)

/**
 * Returns a [Bool4] indicating if each component of this vector is not equal to the corresponding component of [b].
 */
inline infix fun Half4.neq(b: Half4) = Bool4(x != b.x, y != b.y, z != b.z, w != b.w)
