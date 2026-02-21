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

@file:Suppress("unused", "NOTHING_TO_INLINE")

package dev.romainguy.kotlin.math

import kotlin.math.*

/**
 * Enumeration of matrix columns.
 */
enum class MatrixColumn {
    X, Y, Z, W
}

/**
 * Enumeration of rotation orders for Euler angles.
 *
 * @property yaw The component used for the first rotation (yaw).
 * @property pitch The component used for the second rotation (pitch).
 * @property roll The component used for the third rotation (roll).
 */
enum class RotationsOrder(
        val yaw: VectorComponent,
        val pitch: VectorComponent,
        val roll: VectorComponent) {
    XYZ(VectorComponent.X, VectorComponent.Y, VectorComponent.Z),
    XZY(VectorComponent.X, VectorComponent.Z, VectorComponent.Y),
    YXZ(VectorComponent.Y, VectorComponent.X, VectorComponent.Z),
    YZX(VectorComponent.Y, VectorComponent.Z, VectorComponent.X),
    ZXY(VectorComponent.Z, VectorComponent.X, VectorComponent.Y),
    ZYX(VectorComponent.Z, VectorComponent.Y, VectorComponent.X);
}

/**
 * A 2x2 matrix of single-precision floats.
 * The matrix is represented in column-major order.
 *
 * @constructor Creates a new matrix with the specified columns.
 * @property x The first column of the matrix.
 * @property y The second column of the matrix.
 */
data class Mat2(
        var x: Float2 = Float2(x = 1.0f),
        var y: Float2 = Float2(y = 1.0f)) {
    /**
     * Creates a new matrix from the specified matrix [m].
     */
    constructor(m: Mat2) : this(m.x.copy(), m.y.copy())

    companion object {
        /**
         * Creates a [Mat2] from a list of floats in row-major order.
         *
         * @param a A variable number of floats (at least 4).
         * @return A [Mat2] initialized with the provided values.
         */
        fun of(vararg a: Float): Mat2 {
            require(a.size >= 4)
            return Mat2(
                    Float2(a[0], a[2]),
                    Float2(a[1], a[3])
            )
        }

        /**
         * Returns an identity [Mat2].
         */
        fun identity() = Mat2()
    }

    /**
     * Returns the column at the specified [column] index (0 or 1).
     */
    operator fun get(column: Int) = when (column) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("column must be in 0..1")
    }

    /**
     * Returns the value at the specified [column] and [row].
     */
    operator fun get(column: Int, row: Int) = get(column)[row]

    /**
     * Returns the column at the specified [column] ([MatrixColumn.X] or [MatrixColumn.Y]).
     */
    operator fun get(column: MatrixColumn) = when (column) {
        MatrixColumn.X -> x
        MatrixColumn.Y -> y
        else -> throw IllegalArgumentException("column must be X or Y")
    }

    /**
     * Returns the value at the specified [column] and [row].
     */
    operator fun get(column: MatrixColumn, row: Int) = get(column)[row]

    /**
     * Returns the value at the specified [row] and [column] (1-based).
     */
    operator fun invoke(row: Int, column: Int) = get(column - 1)[row - 1]

    /**
     * Sets the value at the specified [row] and [column] (1-based).
     */
    operator fun invoke(row: Int, column: Int, v: Float) = set(column - 1, row - 1, v)

    /**
     * Sets the column at the specified [column] index.
     */
    operator fun set(column: Int, v: Float2) {
        this[column].xy = v
    }

    /**
     * Sets the value at the specified [column] and [row].
     */
    operator fun set(column: Int, row: Int, v: Float) {
        this[column][row] = v
    }

    /**
     * Negates all components of this matrix.
     */
    operator fun unaryMinus() = Mat2(-x, -y)

    /**
     * Increments all components of this matrix.
     */
    operator fun inc() = Mat2(x++, y++)

    /**
     * Decrements all components of this matrix.
     */
    operator fun dec() = Mat2(x--, y--)

    /**
     * Adds a scalar to all components of this matrix.
     */
    operator fun plus(v: Float) = Mat2(x + v, y + v)

    /**
     * Subtracts a scalar from all components of this matrix.
     */
    operator fun minus(v: Float) = Mat2(x - v, y - v)

    /**
     * Multiplies all components of this matrix by a scalar.
     */
    operator fun times(v: Float) = Mat2(x * v, y * v)

    /**
     * Divides all components of this matrix by a scalar.
     */
    operator fun div(v: Float) = Mat2(x / v, y / v)

    /**
     * Compares this matrix to a scalar.
     */
    inline fun compareTo(v: Float, delta: Float = 0.0f) = Mat2(
        x.compareTo(v, delta),
        y.compareTo(v, delta)
    )

    /**
     * Returns true if all components are equal to the scalar.
     */
    inline fun equals(v: Float, delta: Float = 0.0f) = x.equals(v, delta) && y.equals(v, delta)

    /**
     * Multiplies this matrix by another matrix.
     */
    operator fun times(m: Mat2) = Mat2(
            Float2(
                    x.x * m.x.x + y.x * m.x.y,
                    x.y * m.x.x + y.y * m.x.y,
            ),
            Float2(
                    x.x * m.y.x + y.x * m.y.y,
                    x.y * m.y.x + y.y * m.y.y,
            )
    )

    /**
     * Compares two matrices.
     */
    inline fun compareTo(m: Mat2, delta: Float = 0.0f) = Mat2(
        x.compareTo(m.x, delta),
        y.compareTo(m.y, delta)
    )

    /**
     * Returns true if two matrices are equal.
     */
    inline fun equals(m: Mat2, delta: Float = 0.0f) = x.equals(m.x, delta) && y.equals(m.y, delta)

    /**
     * Multiplies this matrix by a vector.
     */
    operator fun times(v: Float2) = Float2(
            x.x * v.x + y.x * v.y,
            x.y * v.x + y.y * v.y,
    )

    /**
     * This matrix as a [FloatArray] in row-major order
     */
    fun toFloatArray() = floatArrayOf(
            x.x, y.x,
            x.y, y.y
    )

    /**
     * This matrix as a [FloatArray] in column-major order
     */
    fun toFloatArrayColumn() = floatArrayOf(
        x.x, x.y,
        y.x, y.y
    )

    override fun toString(): String {
        return """
            |${x.x} ${y.x}|
            |${x.y} ${y.y}|
            """.trimIndent()
    }
}

/**
 * A 3x3 matrix of single-precision floats.
 * The matrix is represented in column-major order.
 *
 * @constructor Creates a new matrix with the specified columns.
 * @property x The first column of the matrix.
 * @property y The second column of the matrix.
 * @property z The third column of the matrix.
 */
data class Mat3(
        var x: Float3 = Float3(x = 1.0f),
        var y: Float3 = Float3(y = 1.0f),
        var z: Float3 = Float3(z = 1.0f)) {
    /**
     * Creates a new matrix from the specified matrix [m].
     */
    constructor(m: Mat3) : this(m.x.copy(), m.y.copy(), m.z.copy())

    companion object {
        /**
         * Creates a [Mat3] from a list of floats in row-major order.
         *
         * @param a A variable number of floats (at least 9).
         * @return A [Mat3] initialized with the provided values.
         */
        fun of(vararg a: Float): Mat3 {
            require(a.size >= 9)
            return Mat3(
                    Float3(a[0], a[3], a[6]),
                    Float3(a[1], a[4], a[7]),
                    Float3(a[2], a[5], a[8])
            )
        }

        /**
         * Returns an identity [Mat3].
         */
        fun identity() = Mat3()
    }

    /**
     * Returns the column at the specified [column] index (0, 1 or 2).
     */
    operator fun get(column: Int) = when (column) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("column must be in 0..2")
    }

    /**
     * Returns the value at the specified [column] and [row].
     */
    operator fun get(column: Int, row: Int) = get(column)[row]

    /**
     * Returns the column at the specified [column] ([MatrixColumn.X], [MatrixColumn.Y] or [MatrixColumn.Z]).
     */
    operator fun get(column: MatrixColumn) = when (column) {
        MatrixColumn.X -> x
        MatrixColumn.Y -> y
        MatrixColumn.Z -> z
        else -> throw IllegalArgumentException("column must be X, Y or Z")
    }

    /**
     * Returns the value at the specified [column] and [row].
     */
    operator fun get(column: MatrixColumn, row: Int) = get(column)[row]

    /**
     * Returns the value at the specified [row] and [column] (1-based).
     */
    operator fun invoke(row: Int, column: Int) = get(column - 1)[row - 1]

    /**
     * Sets the value at the specified [row] and [column] (1-based).
     */
    operator fun invoke(row: Int, column: Int, v: Float) = set(column - 1, row - 1, v)

    /**
     * Sets the column at the specified [column] index.
     */
    operator fun set(column: Int, v: Float3) {
        this[column].xyz = v
    }

    /**
     * Sets the value at the specified [column] and [row].
     */
    operator fun set(column: Int, row: Int, v: Float) {
        this[column][row] = v
    }

    /**
     * Negates all components of this matrix.
     */
    operator fun unaryMinus() = Mat3(-x, -y, -z)

    /**
     * Increments all components of this matrix.
     */
    operator fun inc() = Mat3(x++, y++, z++)

    /**
     * Decrements all components of this matrix.
     */
    operator fun dec() = Mat3(x--, y--, z--)

    /**
     * Adds a scalar to all components of this matrix.
     */
    operator fun plus(v: Float) = Mat3(x + v, y + v, z + v)

    /**
     * Subtracts a scalar from all components of this matrix.
     */
    operator fun minus(v: Float) = Mat3(x - v, y - v, z - v)

    /**
     * Multiplies all components of this matrix by a scalar.
     */
    operator fun times(v: Float) = Mat3(x * v, y * v, z * v)

    /**
     * Divides all components of this matrix by a scalar.
     */
    operator fun div(v: Float) = Mat3(x / v, y / v, z / v)

    /**
     * Compares this matrix to a scalar.
     */
    inline fun compareTo(v: Float, delta: Float = 0.0f) = Mat3(
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
     * Multiplies this matrix by another matrix.
     */
    operator fun times(m: Mat3) = Mat3(
            Float3(
                    x.x * m.x.x + y.x * m.x.y + z.x * m.x.z,
                    x.y * m.x.x + y.y * m.x.y + z.y * m.x.z,
                    x.z * m.x.x + y.z * m.x.y + z.z * m.x.z,
            ),
            Float3(
                    x.x * m.y.x + y.x * m.y.y + z.x * m.y.z,
                    x.y * m.y.x + y.y * m.y.y + z.y * m.y.z,
                    x.z * m.y.x + y.z * m.y.y + z.z * m.y.z,
            ),
            Float3(
                    x.x * m.z.x + y.x * m.z.y + z.x * m.z.z,
                    x.y * m.z.x + y.y * m.z.y + z.y * m.z.z,
                    x.z * m.z.x + y.z * m.z.y + z.z * m.z.z,
            )
    )

    /**
     * Compares two matrices.
     */
    inline fun compareTo(m: Mat3, delta: Float = 0.0f) = Mat3(
        x.compareTo(m.x, delta),
        y.compareTo(m.y, delta),
        z.compareTo(m.z, delta)
    )

    /**
     * Returns true if two matrices are equal.
     */
    inline fun equals(m: Mat3, delta: Float = 0.0f) =
        x.equals(m.x, delta) && y.equals(m.y, delta) && z.equals(m.z, delta)

    /**
     * Multiplies this matrix by a vector.
     */
    operator fun times(v: Float3) = Float3(
            x.x * v.x + y.x * v.y + z.x * v.z,
            x.y * v.x + y.y * v.y + z.y * v.z,
            x.z * v.x + y.z * v.y + z.z * v.z,
    )

    /**
     * This matrix as a [FloatArray] in row-major order
     */
    fun toFloatArray() = floatArrayOf(
            x.x, y.x, z.x,
            x.y, y.y, z.y,
            x.z, y.z, z.z
    )

    /**
     * This matrix as a [FloatArray] in column-major order
     */
    fun toFloatArrayColumn() = floatArrayOf(
        x.x, x.y, x.z,
        y.x, y.y, y.z,
        z.x, z.y, z.z
    )

    override fun toString(): String {
        return """
            |${x.x} ${y.x} ${z.x}|
            |${x.y} ${y.y} ${z.y}|
            |${x.z} ${y.z} ${z.z}|
            """.trimIndent()
    }
}

/**
 * A 4x4 matrix of single-precision floats.
 * The matrix is represented in column-major order.
 *
 * @constructor Creates a new matrix with the specified columns.
 * @property x The first column of the matrix.
 * @property y The second column of the matrix.
 * @property z The third column of the matrix.
 * @property w The fourth column of the matrix.
 */
data class Mat4(
        var x: Float4 = Float4(x = 1.0f),
        var y: Float4 = Float4(y = 1.0f),
        var z: Float4 = Float4(z = 1.0f),
        var w: Float4 = Float4(w = 1.0f)) {
    /**
     * Creates a [Mat4] from three basis vectors and a position vector.
     *
     * @param right The right-facing (X) basis vector.
     * @param up The up-facing (Y) basis vector.
     * @param forward The forward-facing (Z) basis vector.
     * @param position The translation component of the matrix.
     */
    constructor(right: Float3, up: Float3, forward: Float3, position: Float3 = Float3()) :
            this(Float4(right), Float4(up), Float4(forward), Float4(position, 1.0f))

    /**
     * Creates a new matrix from the specified matrix [m].
     */
    constructor(m: Mat4) : this(m.x.copy(), m.y.copy(), m.z.copy(), m.w.copy())

    companion object {

        /**
         * Creates a [Mat4] from a list of floats in row-major order.
         *
         * @param a A variable number of floats (at least 16).
         * @return A [Mat4] initialized with the provided values.
         */
        fun of(vararg a: Float): Mat4 {
            require(a.size >= 16)
            return Mat4(
                    Float4(a[0], a[4], a[8],  a[12]),
                    Float4(a[1], a[5], a[9],  a[13]),
                    Float4(a[2], a[6], a[10], a[14]),
                    Float4(a[3], a[7], a[11], a[15])
            )
        }

        /**
         * Returns an identity [Mat4].
         */
        fun identity() = Mat4()
    }

    /**
     * The right-facing basis vector (XYZ part of column X).
     */
    inline var right: Float3
        get() = x.xyz
        set(value) {
            x.xyz = value
        }

    /**
     * The up-facing basis vector (XYZ part of column Y).
     */
    inline var up: Float3
        get() = y.xyz
        set(value) {
            y.xyz = value
        }

    /**
     * The forward-facing basis vector (XYZ part of column Z).
     */
    inline var forward: Float3
        get() = z.xyz
        set(value) {
            z.xyz = value
        }

    /**
     * The translation component of the matrix (XYZ part of column W).
     */
    inline var position: Float3
        get() = w.xyz
        set(value) {
            w.xyz = value
        }

    /**
     * The scaling component of the matrix.
     */
    inline val scale: Float3
        get() = Float3(length(x.xyz), length(y.xyz), length(z.xyz))

    /**
     * The translation component of the matrix.
     */
    inline val translation: Float3
        get() = w.xyz

    /**
     * The rotation of the matrix as Euler angles (in degrees).
     */
    val rotation: Float3
        get() {
            val x = normalize(right)
            val y = normalize(up)
            val z = normalize(forward)

            return when {
                z.y <= -1.0f -> Float3(degrees(-HALF_PI), 0.0f, degrees(atan2( x.z,  y.z)))
                z.y >=  1.0f -> Float3(degrees( HALF_PI), 0.0f, degrees(atan2(-x.z, -y.z)))
                else -> Float3(
                        degrees(-asin(z.y)), degrees(-atan2(z.x, z.z)), degrees(atan2( x.y,  y.y)))
            }
        }

    /**
     * The upper-left 3x3 portion of this matrix.
     */
    inline val upperLeft: Mat3
        get() = Mat3(x.xyz, y.xyz, z.xyz)

    /**
     * Returns the column at the specified [column] index (0, 1, 2 or 3).
     */
    operator fun get(column: Int) = when (column) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("column must be in 0..3")
    }

    /**
     * Returns the value at the specified [column] and [row].
     */
    operator fun get(column: Int, row: Int) = get(column)[row]

    /**
     * Returns the column at the specified [column] ([MatrixColumn.X], [MatrixColumn.Y], [MatrixColumn.Z] or [MatrixColumn.W]).
     */
    operator fun get(column: MatrixColumn) = when (column) {
        MatrixColumn.X -> x
        MatrixColumn.Y -> y
        MatrixColumn.Z -> z
        MatrixColumn.W -> w
    }

    /**
     * Returns the value at the specified [column] and [row].
     */
    operator fun get(column: MatrixColumn, row: Int) = get(column)[row]

    /**
     * Returns the value at the specified [row] and [column] (1-based).
     */
    operator fun invoke(row: Int, column: Int) = get(column - 1)[row - 1]

    /**
     * Sets the value at the specified [row] and [column] (1-based).
     */
    operator fun invoke(row: Int, column: Int, v: Float) = set(column - 1, row - 1, v)

    /**
     * Sets the column at the specified [column] index.
     */
    operator fun set(column: Int, v: Float4) {
        this[column].xyzw = v
    }

    /**
     * Sets the value at the specified [column] and [row].
     */
    operator fun set(column: Int, row: Int, v: Float) {
        this[column][row] = v
    }

    /**
     * Negates all components of this matrix.
     */
    operator fun unaryMinus() = Mat4(-x, -y, -z, -w)

    /**
     * Increments all components of this matrix.
     */
    operator fun inc() = Mat4(x++, y++, z++, w++)

    /**
     * Decrements all components of this matrix.
     */
    operator fun dec() = Mat4(x--, y--, z--, w--)

    /**
     * Adds a scalar to all components of this matrix.
     */
    operator fun plus(v: Float) = Mat4(x + v, y + v, z + v, w + v)

    /**
     * Subtracts a scalar from all components of this matrix.
     */
    operator fun minus(v: Float) = Mat4(x - v, y - v, z - v, w - v)

    /**
     * Multiplies all components of this matrix by a scalar.
     */
    operator fun times(v: Float) = Mat4(x * v, y * v, z * v, w * v)

    /**
     * Divides all components of this matrix by a scalar.
     */
    operator fun div(v: Float) = Mat4(x / v, y / v, z / v, w / v)

    /**
     * Compares this matrix to a scalar.
     */
    inline fun compareTo(v: Float, delta: Float = 0.0f) = Mat4(
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
     * Multiplies this matrix by another matrix.
     */
    operator fun times(m: Mat4) = Mat4(
            Float4(
                    x.x * m.x.x + y.x * m.x.y + z.x * m.x.z + w.x * m.x.w,
                    x.y * m.x.x + y.y * m.x.y + z.y * m.x.z + w.y * m.x.w,
                    x.z * m.x.x + y.z * m.x.y + z.z * m.x.z + w.z * m.x.w,
                    x.w * m.x.x + y.w * m.x.y + z.w * m.x.z + w.w * m.x.w,
            ),
            Float4(
                    x.x * m.y.x + y.x * m.y.y + z.x * m.y.z + w.x * m.y.w,
                    x.y * m.y.x + y.y * m.y.y + z.y * m.y.z + w.y * m.y.w,
                    x.z * m.y.x + y.z * m.y.y + z.z * m.y.z + w.z * m.y.w,
                    x.w * m.y.x + y.w * m.y.y + z.w * m.y.z + w.w * m.y.w,
            ),
            Float4(
                    x.x * m.z.x + y.x * m.z.y + z.x * m.z.z + w.x * m.z.w,
                    x.y * m.z.x + y.y * m.z.y + z.y * m.z.z + w.y * m.z.w,
                    x.z * m.z.x + y.z * m.z.y + z.z * m.z.z + w.z * m.z.w,
                    x.w * m.z.x + y.w * m.z.y + z.w * m.z.z + w.w * m.z.w,
            ),
            Float4(
                    x.x * m.w.x + y.x * m.w.y + z.x * m.w.z + w.x * m.w.w,
                    x.y * m.w.x + y.y * m.w.y + z.y * m.w.z + w.y * m.w.w,
                    x.z * m.w.x + y.z * m.w.y + z.z * m.w.z + w.z * m.w.w,
                    x.w * m.w.x + y.w * m.w.y + z.w * m.w.z + w.w * m.w.w,
            )
    )

    /**
     * Compares two matrices.
     */
    inline fun compareTo(m: Mat4, delta: Float = 0.0f) = Mat4(
        x.compareTo(m.x, delta),
        y.compareTo(m.y, delta),
        z.compareTo(m.z, delta),
        w.compareTo(m.w, delta)
    )

    /**
     * Returns true if two matrices are equal.
     */
    inline fun equals(m: Mat4, delta: Float = 0.0f) =
        x.equals(m.x, delta) && y.equals(m.y, delta) && z.equals(m.z, delta) && w.equals(m.w, delta)

    /**
     * Multiplies this matrix by a vector.
     */
    operator fun times(v: Float4) = Float4(
            x.x * v.x + y.x * v.y + z.x * v.z+ w.x * v.w,
            x.y * v.x + y.y * v.y + z.y * v.z+ w.y * v.w,
            x.z * v.x + y.z * v.y + z.z * v.z+ w.z * v.w,
            x.w * v.x + y.w * v.y + z.w * v.z+ w.w * v.w
    )

    /**
     * Get the Euler angles in degrees from this rotation Matrix
     *
     * Don't forget to extract the rotation with [rotation] if this is a transposed matrix
     *
     * @param order The order in which to apply rotations.
     * Default is [RotationsOrder.ZYX] which means that the object will first be rotated around its Z
     * axis, then its Y axis and finally its X axis.
     *
     * @see eulerAngles
     */
    fun toEulerAngles(order: RotationsOrder = RotationsOrder.ZYX) = eulerAngles(this, order)

    /**
     * Get the [Quaternion] from this rotation Matrix
     *
     * Don't forget to extract the rotation with [rotation] if this is a transposed matrix
     *
     * @see quaternion
     */
    fun toQuaternion() = quaternion(this)

    /**
     * This matrix as a [FloatArray] in row-major order
     */
    fun toFloatArray() = floatArrayOf(
            x.x, y.x, z.x, w.x,
            x.y, y.y, z.y, w.y,
            x.z, y.z, z.z, w.z,
            x.w, y.w, z.w, w.w
    )

    /**
     * This matrix as a [FloatArray] in column-major order
     */
    fun toFloatArrayColumn() = floatArrayOf(
        x.x, x.y, x.z, x.w,
        y.x, y.y, y.z, y.w,
        z.x, z.y, z.z, z.w,
        w.x, w.y, w.z, w.w
    )

    override fun toString(): String {
        return """
            |${x.x} ${y.x} ${z.x} ${w.x}|
            |${x.y} ${y.y} ${z.y} ${w.y}|
            |${x.z} ${y.z} ${z.z} ${w.z}|
            |${x.w} ${y.w} ${z.w} ${w.w}|
            """.trimIndent()
    }
}

/**
 * Returns a [Bool2] indicating if each component of [a] is equal to [b] within [delta].
 */
inline fun equal(a: Mat2, b: Float, delta: Float = 0.0f) = Bool2(
    a.x.equals(b, delta),
    a.y.equals(b, delta)
)

/**
 * Returns a [Bool2] indicating if each component of [a] is equal to the corresponding component of [b] within [delta].
 */
inline fun equal(a: Mat2, b: Mat2, delta: Float = 0.0f) = Bool2(
    a.x.equals(b.x, delta),
    a.y.equals(b.y, delta)
)

/**
 * Returns a [Bool2] indicating if each component of [a] is not equal to [b] within [delta].
 */
inline fun notEqual(a: Mat2, b: Float, delta: Float = 0.0f) = Bool2(
    !a.x.equals(b, delta),
    !a.y.equals(b, delta)
)

/**
 * Returns a [Bool2] indicating if each component of [a] is not equal to the corresponding component of [b] within [delta].
 */
inline fun notEqual(a: Mat2, b: Mat2, delta: Float = 0.0f) = Bool2(
    !a.x.equals(b.x, delta),
    !a.y.equals(b.y, delta)
)

/**
 * Returns a [Bool3] indicating if each component of [a] is equal to [b] within [delta].
 */
inline fun equal(a: Mat3, b: Float, delta: Float = 0.0f) = Bool3(
    a.x.equals(b, delta),
    a.y.equals(b, delta),
    a.z.equals(b, delta)
)

/**
 * Returns a [Bool3] indicating if each component of [a] is equal to the corresponding component of [b] within [delta].
 */
inline fun equal(a: Mat3, b: Mat3, delta: Float = 0.0f) = Bool3(
    a.x.equals(b.x, delta),
    a.y.equals(b.y, delta),
    a.z.equals(b.z, delta)
)

/**
 * Returns a [Bool3] indicating if each component of [a] is not equal to [b] within [delta].
 */
inline fun notEqual(a: Mat3, b: Float, delta: Float = 0.0f) = Bool3(
    !a.x.equals(b, delta),
    !a.y.equals(b, delta),
    !a.z.equals(b, delta)
)

/**
 * Returns a [Bool3] indicating if each component of [a] is not equal to the corresponding component of [b] within [delta].
 */
inline fun notEqual(a: Mat3, b: Mat3, delta: Float = 0.0f) = Bool3(
    !a.x.equals(b.x, delta),
    !a.y.equals(b.y, delta),
    !a.z.equals(b.z, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is equal to [b] within [delta].
 */
inline fun equal(a: Mat4, b: Float, delta: Float = 0.0f) = Bool4(
    a.x.equals(b, delta),
    a.y.equals(b, delta),
    a.z.equals(b, delta),
    a.w.equals(b, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is equal to the corresponding component of [b] within [delta].
 */
inline fun equal(a: Mat4, b: Mat4, delta: Float = 0.0f) = Bool4(
    a.x.equals(b.x, delta),
    a.y.equals(b.y, delta),
    a.z.equals(b.z, delta),
    a.w.equals(b.w, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is not equal to [b] within [delta].
 */
inline fun notEqual(a: Mat4, b: Float, delta: Float = 0.0f) = Bool4(
    !a.x.equals(b, delta),
    !a.y.equals(b, delta),
    !a.z.equals(b, delta),
    !a.w.equals(b, delta)
)

/**
 * Returns a [Bool4] indicating if each component of [a] is not equal to the corresponding component of [b] within [delta].
 */
inline fun notEqual(a: Mat4, b: Mat4, delta: Float = 0.0f) = Bool4(
    !a.x.equals(b.x, delta),
    !a.y.equals(b.y, delta),
    !a.z.equals(b.z, delta),
    !a.w.equals(b.w, delta)
)

/**
 * Returns the transpose of the matrix [m].
 */
fun transpose(m: Mat2) = Mat2(
        Float2(m.x.x, m.y.x),
        Float2(m.x.y, m.y.y)
)

/**
 * Returns the transpose of the matrix [m].
 */
fun transpose(m: Mat3) = Mat3(
        Float3(m.x.x, m.y.x, m.z.x),
        Float3(m.x.y, m.y.y, m.z.y),
        Float3(m.x.z, m.y.z, m.z.z)
)

/**
 * Returns the inverse of the matrix [m].
 */
@Suppress("LocalVariableName")
fun inverse(m: Mat3): Mat3 {
    val a = m.x.x
    val b = m.x.y
    val c = m.x.z
    val d = m.y.x
    val e = m.y.y
    val f = m.y.z
    val g = m.z.x
    val h = m.z.y
    val i = m.z.z

    val A = e * i - f * h
    val B = f * g - d * i
    val C = d * h - e * g

    val det = a * A + b * B + c * C

    return Mat3.of(
            A / det, B / det, C / det,
            (c * h - b * i) / det, (a * i - c * g) / det, (b * g - a * h) / det,
            (b * f - c * e) / det, (c * d - a * f) / det, (a * e - b * d) / det
    )
}

/**
 * Returns the transpose of the matrix [m].
 */
fun transpose(m: Mat4) = Mat4(
        Float4(m.x.x, m.y.x, m.z.x, m.w.x),
        Float4(m.x.y, m.y.y, m.z.y, m.w.y),
        Float4(m.x.z, m.y.z, m.z.z, m.w.z),
        Float4(m.x.w, m.y.w, m.z.w, m.w.w)
)

/**
 * Returns the inverse of the matrix [m].
 */
fun inverse(m: Mat4): Mat4 {
    val result = Mat4()

    var pair0  = m.z.z * m.w.w
    var pair1  = m.w.z * m.z.w
    var pair2  = m.y.z * m.w.w
    var pair3  = m.w.z * m.y.w
    var pair4  = m.y.z * m.z.w
    var pair5  = m.z.z * m.y.w
    var pair6  = m.x.z * m.w.w
    var pair7  = m.w.z * m.x.w
    var pair8  = m.x.z * m.z.w
    var pair9  = m.z.z * m.x.w
    var pair10 = m.x.z * m.y.w
    var pair11 = m.y.z * m.x.w

    result.x.x  = pair0 * m.y.y + pair3 * m.z.y + pair4 * m.w.y
    result.x.x -= pair1 * m.y.y + pair2 * m.z.y + pair5 * m.w.y
    result.x.y  = pair1 * m.x.y + pair6 * m.z.y + pair9 * m.w.y
    result.x.y -= pair0 * m.x.y + pair7 * m.z.y + pair8 * m.w.y
    result.x.z  = pair2 * m.x.y + pair7 * m.y.y + pair10 * m.w.y
    result.x.z -= pair3 * m.x.y + pair6 * m.y.y + pair11 * m.w.y
    result.x.w  = pair5 * m.x.y + pair8 * m.y.y + pair11 * m.z.y
    result.x.w -= pair4 * m.x.y + pair9 * m.y.y + pair10 * m.z.y
    result.y.x  = pair1 * m.y.x + pair2 * m.z.x + pair5 * m.w.x
    result.y.x -= pair0 * m.y.x + pair3 * m.z.x + pair4 * m.w.x
    result.y.y  = pair0 * m.x.x + pair7 * m.z.x + pair8 * m.w.x
    result.y.y -= pair1 * m.x.x + pair6 * m.z.x + pair9 * m.w.x
    result.y.z  = pair3 * m.x.x + pair6 * m.y.x + pair11 * m.w.x
    result.y.z -= pair2 * m.x.x + pair7 * m.y.x + pair10 * m.w.x
    result.y.w  = pair4 * m.x.x + pair9 * m.y.x + pair10 * m.z.x
    result.y.w -= pair5 * m.x.x + pair8 * m.y.x + pair11 * m.z.x

    pair0 = m.z.x * m.w.y
    pair1 = m.w.x * m.z.y
    pair2 = m.y.x * m.w.y
    pair3 = m.w.x * m.y.y
    pair4 = m.y.x * m.z.y
    pair5 = m.z.x * m.y.y
    pair6 = m.x.x * m.w.y
    pair7 = m.w.x * m.x.y
    pair8 = m.x.x * m.z.y
    pair9 = m.z.x * m.x.y
    pair10 = m.x.x * m.y.y
    pair11 = m.y.x * m.x.y

    result.z.x  = pair0 * m.y.w + pair3 * m.z.w + pair4 * m.w.w
    result.z.x -= pair1 * m.y.w + pair2 * m.z.w + pair5 * m.w.w
    result.z.y  = pair1 * m.x.w + pair6 * m.z.w + pair9 * m.w.w
    result.z.y -= pair0 * m.x.w + pair7 * m.z.w + pair8 * m.w.w
    result.z.z  = pair2 * m.x.w + pair7 * m.y.w + pair10 * m.w.w
    result.z.z -= pair3 * m.x.w + pair6 * m.y.w + pair11 * m.w.w
    result.z.w  = pair5 * m.x.w + pair8 * m.y.w + pair11 * m.z.w
    result.z.w -= pair4 * m.x.w + pair9 * m.y.w + pair10 * m.z.w
    result.w.x  = pair2 * m.z.z + pair5 * m.w.z + pair1 * m.y.z
    result.w.x -= pair4 * m.w.z + pair0 * m.y.z + pair3 * m.z.z
    result.w.y  = pair8 * m.w.z + pair0 * m.x.z + pair7 * m.z.z
    result.w.y -= pair6 * m.z.z + pair9 * m.w.z + pair1 * m.x.z
    result.w.z  = pair6 * m.y.z + pair11 * m.w.z + pair3 * m.x.z
    result.w.z -= pair10 * m.w.z + pair2 * m.x.z + pair7 * m.y.z
    result.w.w  = pair10 * m.z.z + pair4 * m.x.z + pair9 * m.y.z
    result.w.w -= pair8 * m.y.z + pair11 * m.z.z + pair5 * m.x.z

    val determinant = m.x.x * result.x.x + m.y.x * result.x.y + m.z.x * result.x.z + m.w.x * result.x.w

    return result / determinant
}

/**
 * Returns a scaling matrix.
 */
fun scale(s: Float3) = Mat4(Float4(x = s.x), Float4(y = s.y), Float4(z = s.z))

/**
 * Returns a scaling matrix from the scale of the given matrix [m].
 */
fun scale(m: Mat4) = scale(m.scale)

/**
 * Returns a translation matrix.
 */
fun translation(t: Float3) = Mat4(w = Float4(t, 1.0f))

/**
 * Returns a translation matrix from the translation of the given matrix [m].
 */
fun translation(m: Mat4) = translation(m.translation)

/**
 * Returns a rotation matrix from the rotation of the given matrix [m].
 */
fun rotation(m: Mat4) = Mat4(normalize(m.right), normalize(m.up), normalize(m.forward))

/**
 * Construct a rotation matrix from Euler angles using YPR around a specified order
 *
 * Uses intrinsic Tait-Bryan angles. This means that rotations are performed with respect to the
 * local coordinate system.
 * That is, for order 'XYZ', the rotation is first around the X axis (which is the same as the
 * world-X axis), then around local-Y (which may now be different from the world Y-axis),
 * then local-Z (which may be different from the world Z-axis)
 *
 * @param d Per axis Euler angles in degrees
 * Yaw, pitch, roll (YPR) are taken accordingly to the rotations order input.
 * @param order The order in which to apply rotations.
 * Default is [RotationsOrder.ZYX] which means that the object will first be rotated around its Z
 * axis, then its Y axis and finally its X axis.
 *
 * @return The rotation matrix
 */
fun rotation(d: Float3, order: RotationsOrder = RotationsOrder.ZYX): Mat4 {
    val r = transform(d, ::radians)
    return rotation(r[order.yaw], r[order.pitch], r[order.roll], order)
}

/**
 * Construct a rotation matrix from Euler yaw, pitch, roll around a specified order.
 *
 * @param roll about 1st rotation axis in radians. Z in case of ZYX order
 * @param pitch about 2nd rotation axis in radians. Y in case of ZYX order
 * @param yaw about 3rd rotation axis in radians. X in case of ZYX order
 * @param order The order in which to apply rotations.
 * Default is [RotationsOrder.ZYX] which means that the object will first be rotated around its Z
 * axis, then its Y axis and finally its X axis.
 *
 * @return The rotation matrix
 */
fun rotation(yaw: Float = 0.0f, pitch: Float = 0.0f, roll: Float = 0.0f, order: RotationsOrder = RotationsOrder.ZYX): Mat4 {
    val c1 = cos(yaw)
    val s1 = sin(yaw)
    val c2 = cos(pitch)
    val s2 = sin(pitch)
    val c3 = cos(roll)
    val s3 = sin(roll)

    return when (order) {
        RotationsOrder.XZY -> Mat4.of(
                c2 * c3, -s2, c2 * s3, 0.0f,
                s1 * s3 + c1 * c3 * s2, c1 * c2, c1 * s2 * s3 - c3 * s1, 0.0f,
                c3 * s1 * s2 - c1 * s3, c2 * s1, c1 * c3 + s1 * s2 * s3, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
        RotationsOrder.XYZ -> Mat4.of(
                c2 * c3, -c2 * s3, s2, 0.0f,
                c1 * s3 + c3 * s1 * s2, c1 * c3 - s1 * s2 * s3, -c2 * s1, 0.0f,
                s1 * s3 - c1 * c3 * s2, c3 * s1 + c1 * s2 * s3, c1 * c2, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
        RotationsOrder.YXZ -> Mat4.of(
                c1 * c3 + s1 * s2 * s3, c3 * s1 * s2 - c1 * s3, c2 * s1, 0.0f,
                c2 * s3, c2 * c3, -s2, 0.0f,
                c1 * s2 * s3 - c3 * s1, c1 * c3 * s2 + s1 * s3, c1 * c2, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
        RotationsOrder.YZX -> Mat4.of(
                c1 * c2, s1 * s3 - c1 * c3 * s2, c3 * s1 + c1 * s2 * s3, 0.0f,
                s2, c2 * c3, -c2 * s3, 0.0f,
                -c2 * s1, c1 * s3 + c3 * s1 * s2, c1 * c3 - s1 * s2 * s3, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
        RotationsOrder.ZYX -> Mat4.of(
                c1 * c2, c1 * s2 * s3 - c3 * s1, s1 * s3 + c1 * c3 * s2, 0.0f,
                c2 * s1, c1 * c3 + s1 * s2 * s3, c3 * s1 * s2 - c1 * s3, 0.0f,
                -s2, c2 * s3, c2 * c3, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
        RotationsOrder.ZXY -> Mat4.of(
                c1 * c3 - s1 * s2 * s3, -c2 * s1, c1 * s3 + c3 * s1 * s2, 0.0f,
                c3 * s1 + c1 * s2 * s3, c1 * c2, s1 * s3 - c1 * c3 * s2, 0.0f,
                -c2 * s3, s2, c2 * c3, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
    }
}

/**
 * Construct a rotation matrix from an [axis] and an [angle] in degrees.
 *
 * @param axis The rotation axis (should be normalized).
 * @param angle The rotation angle in degrees.
 * @return The rotation matrix.
 */
fun rotation(axis: Float3, angle: Float): Mat4 {
    val x = axis.x
    val y = axis.y
    val z = axis.z

    val r = radians(angle)
    val c = cos(r)
    val s = sin(r)
    val d = 1.0f - c

    return Mat4.of(
            x * x * d + c, x * y * d - z * s, x * z * d + y * s, 0.0f,
            y * x * d + z * s, y * y * d + c, y * z * d - x * s, 0.0f,
            z * x * d - y * s, z * y * d + x * s, z * z * d + c, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
    )
}

/**
 * Construct a Quaternion Rotation Matrix following the Hamilton convention
 *
 * Assume the destination and local coordinate spaces are initially aligned, and the local
 * coordinate space is then rotated counter-clockwise about a unit-length axis, k, by an angle,
 * theta.
 */
fun rotation(quaternion: Quaternion): Mat4 {
    val n = normalize(quaternion)
    return Mat4(
            Float4(
                1.0f - 2.0f * (n.y * n.y + n.z * n.z),
                2.0f * (n.x * n.y + n.z * n.w),
                2.0f * (n.x * n.z - n.y * n.w),
            ),
            Float4(
                2.0f * (n.x * n.y - n.z * n.w),
                1.0f - 2.0f * (n.x * n.x + n.z * n.z),
                2.0f * (n.y * n.z + n.x * n.w),
            ),
            Float4(
                2.0f * (n.x * n.z + n.y * n.w),
                2.0f * (n.y * n.z - n.x * n.w),
                1.0f - 2.0f * (n.x * n.x + n.y * n.y)
            )
    )
}

/**
 * Get the Euler angles in degrees from a rotation Matrix
 *
 * @param m The rotation matrix.
 * Don't forget to extract the rotation with [rotation] if it's transposed
 * @param order The order in which to apply rotations.
 * Default is [RotationsOrder.ZYX] which means that the object will first be rotated around its Z
 * axis, then its Y axis and finally its X axis.
 */
fun eulerAngles(m: Mat4, order: RotationsOrder = RotationsOrder.ZYX): Float3 {
    // We need to more simplify this with RotationsOrder VectorComponents mapped to MatrixColumn
    return transform(Float3().apply {
        when (order) {
            RotationsOrder.XYZ -> {
                this[order.pitch] = asin(clamp(m.z.x, -1.0f, 1.0f))
                if (abs(m.z.x) < 0.9999999f) {
                    this[order.yaw] = atan2(-m.z.y, m.z.z)
                    this[order.roll] = atan2(-m.y.x, m.x.x)
                } else {
                    this[order.yaw] = atan2(m.y.z, m.y.y)
                    this[order.roll] = 0.0f
                }
            }
            RotationsOrder.XZY -> {
                this[order.pitch] = asin(-clamp(m.y.x, -1.0f, 1.0f))
                if (abs(m.y.x) < 0.9999999f) {
                    this[order.yaw] = atan2(m.y.z, m.y.y)
                    this[order.roll] = atan2(m.z.x, m.x.x)
                } else {
                    this[order.yaw] = atan2(-m.z.y, m.z.z)
                    this[order.roll] = 0.0f
                }
            }
            RotationsOrder.YXZ -> {
                this[order.pitch] = asin(-clamp(m.z.y, -1.0f, 1.0f))
                if (abs(m.z.y) < 0.9999999f) {
                    this[order.yaw] = atan2(m.z.x, m.z.z)
                    this[order.roll] = atan2(m.x.y, m.y.y)
                } else {
                    this[order.yaw] = atan2(-m.x.z, m.x.x)
                    this[order.roll] = 0.0f
                }
            }
            RotationsOrder.YZX -> {
                this[order.pitch] = asin(clamp(m.x.y, -1.0f, 1.0f))
                if (abs(m.x.y) < 0.9999999f) {
                    this[order.roll] = atan2(-m.z.y, m.y.y)
                    this[order.yaw] = atan2(-m.x.z, m.x.x)
                } else {
                    this[order.roll] = 0.0f
                    this[order.yaw] = atan2(m.z.x, m.z.z)
                }
            }
            RotationsOrder.ZXY -> {
                this[order.pitch] = asin(clamp(m.y.z, -1.0f, 1.0f))
                if (abs(m.y.z) < 0.9999999f) {
                    this[order.roll] = atan2(-m.x.z, m.z.z)
                    this[order.yaw] = atan2(-m.y.x, m.y.y)
                } else {
                    this[order.roll] = 0.0f
                    this[order.yaw] = atan2(m.x.y, m.x.x)
                }
            }
            RotationsOrder.ZYX -> {
                this[order.pitch] = asin(-clamp(m.x.z, -1.0f, 1.0f))
                if (abs(m.x.z) < 0.9999999f) {
                    this[order.roll] = atan2(m.y.z, m.z.z)
                    this[order.yaw] = atan2(m.x.y, m.x.x)
                } else {
                    this[order.roll] = 0.0f
                    this[order.yaw] = atan2(-m.y.x, m.y.y)
                }
            }
        }
    }, ::degrees)
}

/**
 * Get the [Quaternion] from a rotation Matrix
 *
 * @param m The rotation matrix.
 * Don't forget to extract the rotation with [rotation] if it's transposed
 */
fun quaternion(m: Mat4): Quaternion {
    val trace = m.x.x + m.y.y + m.z.z
    return normalize(
        when {
            trace > 0.0f -> {
                val s = 2.0f * sqrt(trace + 1.0f)
                Quaternion(
                    (m.y.z - m.z.y) / s,
                    (m.z.x - m.x.z) / s,
                    (m.x.y - m.y.x) / s,
                    0.25f * s
                )
            }
            m.x.x > m.y.y && m.x.x > m.z.z -> {
                val s = 2.0f * sqrt(1.0f + m.x.x - m.y.y - m.z.z)
                Quaternion(
                    0.25f * s,
                    (m.y.x + m.x.y) / s,
                    (m.z.x + m.x.z) / s,
                    (m.y.z - m.z.y) / s
                )
            }
            m.y.y > m.z.z -> {
                val s = 2.0f * sqrt(1.0f + m.y.y - m.x.x - m.z.z)
                Quaternion(
                    (m.y.x + m.x.y) / s,
                    0.25f * s,
                    (m.z.y + m.y.z) / s,
                    (m.z.x - m.x.z) / s
                )
            }
            else -> {
                val s = 2.0f * sqrt(1.0f + m.z.z - m.x.x - m.y.y)
                Quaternion(
                    (m.z.x + m.x.z) / s,
                    (m.z.y + m.y.z) / s,
                    0.25f * s,
                    (m.x.y - m.y.x) / s
                )
            }
        }
    )
}

/**
 * Returns a normal matrix from the given matrix [m].
 */
fun normal(m: Mat4) = scale(1.0f / Float3(length2(m.right), length2(m.up), length2(m.forward))) * m

/**
 * Returns a view matrix looking from [eye] towards [target].
 *
 * @param eye The position of the eye/camera.
 * @param target The position to look at.
 * @param up The up vector of the camera (default is Z-up).
 */
fun lookAt(eye: Float3, target: Float3, up: Float3 = Float3(z = 1.0f)): Mat4 {
    return lookTowards(eye, target - eye, up)
}

/**
 * Returns a view matrix looking from [eye] in the direction [forward].
 *
 * @param eye The position of the eye/camera.
 * @param forward The direction to look towards.
 * @param up The up vector of the camera (default is Z-up).
 */
fun lookTowards(eye: Float3, forward: Float3, up: Float3 = Float3(z = 1.0f)): Mat4 {
    val f = normalize(forward)
    val r = normalize(f x up)
    val u = normalize(r x f)
    return Mat4(Float4(r), Float4(u), Float4(-f), Float4(eye, 1.0f))
}

/**
 * Returns a perspective projection matrix.
 *
 * @param fov The vertical field of view in degrees.
 * @param ratio The aspect ratio (width / height).
 * @param near The distance to the near clipping plane.
 * @param far The distance to the far clipping plane.
 */
fun perspective(fov: Float, ratio: Float, near: Float, far: Float): Mat4 {
    val t = 1.0f / tan(radians(fov) * 0.5f)
    val a = (far + near) / (far - near)
    val b = (2.0f * far * near) / (far - near)
    val c = t / ratio
    return Mat4(Float4(x = c), Float4(y = t), Float4(z = a, w = 1.0f), Float4(z = -b))
}

/**
 * Returns an orthographic projection matrix.
 *
 * @param l The left clipping plane.
 * @param r The right clipping plane.
 * @param b The bottom clipping plane.
 * @param t The top clipping plane.
 * @param n The near clipping plane.
 * @param f The far clipping plane.
 */
fun ortho(l: Float, r: Float, b: Float, t: Float, n: Float, f: Float) = Mat4(
    Float4(x = 2.0f / (r - l)),
    Float4(y = 2.0f / (t - b)),
    Float4(z = -2.0f / (f - n)),
    Float4(
        -(r + l) / (r - l),
        -(t + b) / (t - b),
        -(f + n) / (f - n),
        1.0f
    )
)
