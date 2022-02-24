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

import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class MatrixTest {
    @Test
    fun mat2Multiplication() {
        val a = Mat2.of(1.0f, 2.0f, 3.0f, 4.0f)
        val b = Mat2.of(2.0f, 0.0f, 1.0f, 2.0f)
        assertEquals(Mat2.of(4.0f, 4.0f, 10.0f, 8.0f), a * b)
    }

    @Test
    fun mat3Multiplication() {
        val a = Mat3.of(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f)
        val b = Mat3.of(2.0f, 0.0f, 1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 0.0f, 1.0f)
        assertEquals(
            Mat3.of(12.0f, 6.0f, 6.0f, 30.0f, 15.0f, 15.0f, 48.0f, 24.0f, 24.0f),
            a * b,
        )
    }

    @Test
    fun mat4Multiplication() {
        val a = Mat4.of(
             1.0f,  2.0f,  3.0f,  4.0f,
             5.0f,  6.0f,  7.0f,  8.0f,
             9.0f, 10.0f, 11.0f, 12.0f,
            13.0f, 14.0f, 15.0f, 16.0f,
        )
        val b = Mat4.of(
            2.0f, 0.0f, 1.0f, 2.0f,
            1.0f, 1.0f, 2.0f, 0.0f,
            2.0f, 1.0f, 2.0f, 2.0f,
            0.0f, 1.0f, 1.0f, 2.0f,
        )
        assertEquals(
            Mat4.of(
                10.0f,  9.0f, 15.0f, 16.0f,
                30.0f, 21.0f, 39.0f, 40.0f,
                50.0f, 33.0f, 63.0f, 64.0f,
                70.0f, 45.0f, 87.0f, 88.0f,
            ),
            a * b
        )
    }

    @Test
    fun mat3Identity() {
        assertEquals(
            Mat3(
                Float3(1f, 0f, 0f),
                Float3(0f, 1f, 0f),
                Float3(0f, 0f, 1f),
            ),
            Mat3.identity()
        )
    }

    @Test
    fun mat3OfFailsIfLessThan9Arguments() {
        assertFailsWith<IllegalArgumentException> {
            Mat3.of(*8.floatArray())
        }
    }

    @Test
    fun mat3Of() {
        assertEquals(MAT_3, Mat3.of(*9.floatArray()))
    }

    @Test
    fun mat4Identity() {
        assertEquals(
            Mat4(
                Float4(1f, 0f, 0f, 0f),
                Float4(0f, 1f, 0f, 0f),
                Float4(0f, 0f, 1f, 0f),
                Float4(0f, 0f, 0f, 1f),
            ),
            Mat4.identity()
        )
    }

    @Test
    fun mat4OfFailsIfLessThan16Arguments() {
        assertFailsWith<IllegalArgumentException> {
            Mat4.of(*15.floatArray())
        }
    }

    @Test
    fun mat4Of() {
        assertEquals(MAT_4, Mat4.of(*16.floatArray()))
    }

    @Test
    fun transposeMat3() {
        assertEquals(
            Mat3(
                Float3(1f, 2f, 3f),
                Float3(4f, 5f, 6f),
                Float3(7f, 8f, 9f)
            ),
            transpose(MAT_3)
        )
    }

    @Test
    fun transposeMat3OfIdentityIsIdentity() {
        assertEquals(transpose(Mat3.identity()), Mat3.identity())
    }

    @Test
    fun inverseMat3() {
        assertEquals(
            Mat3(
                Float3(0f, 1f, 0f),
                Float3(-2f, 1f, 1f),
                Float3(2f, -2f, 0f)
            ),
            inverse(Mat3(
                Float3(1f, 0f, 0.5f),
                Float3(1f, 0f, 0f),
                Float3(1f, 1f, 1f)
            ))
        )
    }

    @Test
    fun inverseMat3OfIdentityIsIdentity() {
        assertEquals(Mat3.identity(), inverse(Mat3.identity()))
    }

    @Test
    fun scaleFloat3() {
        assertEquals(Mat4.identity(), scale(Float3(1f, 1f, 1f)))
    }

    @Test
    fun scaleMat4() {
        assertEquals(
            Mat4(
                Float4(2f, 0f, 0f, 0f),
                Float4(0f, 4f, 0f, 0f),
                Float4(0f, 0f, 6f, 0f),
                Float4(0f, 0f, 0f, 1f)
            ),
            scale(Mat4(
                Float4(2f, 0f, 0f, 0f),
                Float4(4f, 0f, 0f, 0f),
                Float4(6f, 0f, 0f, 0f),
                Float4(0f, 0f, 0f, 0f)
            ))
        )
    }

    @Test
    fun translationFloat3() {
        assertEquals(
            Mat4(
                Float4(1f, 0f, 0f, 0f),
                Float4(0f, 1f, 0f, 0f),
                Float4(0f, 0f, 1f, 0f),
                Float4(1f, 2f, 3f, 1f),
            ),
            translation(Float3(1f, 2f, 3f))
        )
    }

    @Test
    fun translationMat4() {
        assertEquals(
            Mat4(
                Float4(1f, 0f, 0f, 0f),
                Float4(0f, 1f, 0f, 0f),
                Float4(0f, 0f, 1f, 0f),
                Float4(4f, 8f, 12f, 1f),
            ),
            translation(MAT_4)
        )
    }

    @Test
    fun inverseMat4OfIdentityIsIdentity() {
        assertEquals(Mat4.identity(), inverse(Mat4.identity()))
    }

    @Test
    fun inverseMat4() {
        assertEquals(
            Mat4(
                Float4( 1f,  0f, 0f,  0f),
                Float4(-1f,  1f, 0f,  0f),
                Float4( 4f, -4f, 1f, -2f),
                Float4(-2f,  2f, 0f,  1f)

            ),
            inverse(Mat4(
                Float4(1f,  0f, 0f, 0f),
                Float4(1f,  1f, 0f, 0f),
                Float4(0f,  0f, 1f, 2f),
                Float4(0f, -2f, 0f, 1f),
            ))
        )
    }

    @Test
    fun inverseNonInvertibleMat4() {
        val expected = Mat4(
            Float4(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NaN, Float.NaN),
            Float4(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NaN, Float.NaN),
            Float4(Float.NaN,               Float.NaN,               Float.NaN, Float.NaN),
            Float4(Float.NaN,               Float.NaN,               Float.NaN, Float.NaN),
        )

        val actual = inverse(Mat4(
            Float4(1f, 1f, 0f, 0f),
            Float4(1f, 1f, 0f, 0f),
            Float4(0f, 0f, 1f, 2f),
            Float4(0f, 0f, 0f, 1f)
        ))

        // In Kotlin/Native NaN != NaN, unless checking content equality
        assertTrue(expected.toFloatArray() contentEquals actual.toFloatArray())
    }

    @Test
    fun rotationFloat3() {
        assertArrayEquals(
            Mat4(
                Float4(0.9980f, 0.0523f, -0.0349f, 0f),
                Float4(-0.0517f, 0.9985f, 0.0174f, 0f),
                Float4(0.0358f, -0.0156f, 0.9992f, 0f),
                Float4(0f, 0f, 0f, 1f)
            ).toFloatArray(),
            rotation(Float3(1f, 2f, 3f)).toFloatArray()
        )
    }

    @Test
    fun rotationMat4() {
        assertArrayEquals(
            Mat4(
                Float4(0.0966f, 0.4833f, 0.87f, 0f),
                Float4(0.169f, 0.507f, 0.8451f, 0f),
                Float4(0.2242f, 0.5232f, 0.8221f, 0f),
                Float4(0f, 0f, 0f, 1f)
            ).toFloatArray(),
            rotation(MAT_4).toFloatArray()
        )
    }

    @Test
    fun rotationEulerXYZ() {
        assertArrayEquals(
                Mat4(
                    Float4(0.9980f, 0.0529f, -0.0339f, 0f),
                    Float4(-0.0523f, 0.9984f, 0.0192f, 0f),
                    Float4(0.0349f, -0.0174f, 0.9992f, 0f),
                    Float4(0f, 0f, 0f, 1f),
                ).toFloatArray(),
                rotation(Float3(1f, 2f, 3f), order = RotationsOrder.XYZ).toFloatArray()
        )
    }

    @Test
    fun rotationEulerZYX() {
        assertArrayEquals(
                Mat4(
                    Float4(0.9980f, 0.0523f, -0.0349f, 0f),
                    Float4(-0.0518f, 0.9985f, 0.0174f, 0f),
                    Float4(0.0358f, -0.0156f, 0.9992f, 0f),
                    Float4(0f, 0f, 0f, 1f),
                ).toFloatArray(),
                rotation(Float3(1f, 2f, 3f), order = RotationsOrder.ZYX).toFloatArray()
        )
    }

    @Test
    fun rotationAxisAngle() {
        assertArrayEquals(
            Mat4(
                Float4(1f, 5f, 1f, 0f),
                Float4(-1f, 4f, 7f, 0f),
                Float4(5f, 5f, 9f, 0f),
                Float4(0f, 0f, 0f, 1f)
            ).toFloatArray(),
            rotation(Float3(1f, 2f, 3f), 90f).toFloatArray()
        )
    }

    @Test
    fun eulerXYZ() {
        assertArrayEquals(
            Float3(10.0f,90.0f,0.0f).toFloatArray(),
            eulerAngles(
                rotation(
                    Float3(10.0f,90.0f,0.0f)
                    , RotationsOrder.XYZ
                )
                , RotationsOrder.XYZ
            ).toFloatArray()
        )
    }

    @Test
    fun eulerZYX() {
        assertArrayEquals(
            // CQFD: That's why Quaternion exist
            Float3(0.0f, 90.0f, -10.0f).toFloatArray(),
            eulerAngles(
                rotation(
                    Float3(10.0f,90.0f,0.0f)
                    , RotationsOrder.ZYX
                )
                , RotationsOrder.ZYX
            ).toFloatArray()
        )
    }

    @Test
    fun eulerXYZRotation() {
        assertArrayEquals(
            Float3(1f, 2f, 3f).toFloatArray(),
            eulerAngles(
                Mat4(
                    Float4(0.9980212f, 0.0529362f, -0.0339330f),
                    Float4(-0.0523041f, 0.9984456f, 0.0192547f),
                    Float4(0.0348995f, -0.0174418f, 0.9992386f)
                ),
                RotationsOrder.XYZ
            ).toFloatArray()
        )
    }

    @Test
    fun eulerZYXRotation() {
        assertArrayEquals(
            Float3(1f, 2f, 3f).toFloatArray(),
            eulerAngles(
                Mat4(
                    Float4(0.9980212f, 0.0523041f, -0.0348995f),
                    Float4(-0.0517197f, 0.9985093f, 0.0174418f),
                    Float4(0.0357597f, -0.0156023f, 0.9992386f)
                ),
                RotationsOrder.ZYX
            ).toFloatArray()
        )
    }

    @Test
    fun quaternion() {
        assertArrayEquals(
            normalize(Quaternion(1f, 2f, 3f, 1.0f)).toFloatArray(),
            quaternion(rotation(Quaternion(1f, 2f, 3f, 1.0f))).toFloatArray()
        )
    }

    @Test
    fun rotationQuaternion() {
        assertMatEquals(
            Mat4(
                Float4(-0.7333333f, 0.6666667f, 0.1333333f),
                Float4(-0.1333333f, -0.3333333f, 0.9333333f),
                Float4(0.6666667f, 0.6666667f, 0.3333333f)
            ),
            rotation(Quaternion(1f, 2f, 3f, 1f))
        )
    }

    @Test
    fun quaternionRotation() {
        assertArrayEquals(
            normalize(Quaternion(1f, 2f, 3f, 1.0f)).toFloatArray(),
            quaternion(
                Mat4(
                    Float4(-0.7333333f, 0.6666667f, 0.1333333f),
                    Float4(-0.1333333f, -0.3333333f, 0.9333333f),
                    Float4(0.6666667f, 0.6666667f, 0.3333333f)
                )
            ).toFloatArray()
        )
    }

    @Test
    fun normal() {
        assertArrayEquals(
            Mat4(
                Float4(0.0093f, 0.0357f, 0.0502f, 13.0f),
                Float4(0.0186f, 0.0428f, 0.0558f, 14.0f),
                Float4(0.0280f, 0.05f, 0.0614f, 15.0f),
                Float4(0.0373f, 0.0571f, 0.0670f, 16.0f),
            ).toFloatArray(),
            normal(MAT_4).toFloatArray()
        )
    }

    @Test
    fun lookAt() {
        assertArrayEquals(
            Mat4(
                Float4(0.53606f, -0.7862f, 0.30734f, 0.0f),
                Float4(0.28377f, 0.51073f, 0.81155f, 0.0f),
                Float4(0.79504f, 0.34783f, -0.4969f, 0.0f),
                Float4(1.0f, 2.0f, 3.0f, 1.0f),
            ).toFloatArray(),
            lookAt(
                eye = Float3(1f, 2f, 3f),
                target = Float3(9f, 5.5f, -2f),
                up = Float3(3f, 4f, 5f)
            ).toFloatArray()
        )
    }

    @Test
    fun lookTowards() {
        assertArrayEquals(
            Mat4(
                Float4(-0.6549f, -0.3475f, 0.67100f, 0.0f),
                Float4(0.10792f, 0.83584f, 0.53825f, 0.0f),
                Float4(0.74791f, -0.4249f, 0.50994f, 0.0f),
                Float4(1.0f, 2.0f, 3.0f, 1.0f),
            ).toFloatArray(),
            lookTowards(
                eye = Float3(1f, 2f, 3f),
                forward = Float3(4.4f, -2.5f, 3f),
                up = Float3(3f, 4f, 5f),
            ).toFloatArray()
        )
    }

    @Test
    fun perspective() {
        assertArrayEquals(
            Mat4(
                Float4(57.2943f, 0.0f, 0.0f, 0.0f),
                Float4(0.0f, 114.5886f, 0.0f, 0.0f),
                Float4(0.0f, 0.0f, -7.0f, 1.0f),
                Float4(0.0f, 0.0f, 24.0f, 0.0f)
            ).toFloatArray(),
            perspective(
                fov = 1f,
                ratio = 2f,
                far = 3f,
                near = 4f,
            ).toFloatArray()
        )
    }

    @Test
    fun ortho() {
        assertArrayEquals(
            Mat4(
                Float4(2.0f, 0.0f, 0.0f, 0.0f),
                Float4(0.0f, 2.0f, 0.0f, 0.0f),
                Float4(0.0f, 0.0f, -2.0f, 0.0f),
                Float4(-3.0f, -7.0f, -11.0f, 1.0f),
            ).toFloatArray(),
            ortho(
                l = 1f,
                r = 2f,
                b = 3f,
                t = 4f,
                n = 5f,
                f = 6f,
            ).toFloatArray()
        )
    }

    companion object {
        private val MAT_3 = Mat3(
            Float3(1f, 4f, 7f),
            Float3(2f, 5f, 8f),
            Float3(3f, 6f, 9f)
        )
        private val MAT_4 = Mat4(
            Float4(1f, 5f, 9f, 13f),
            Float4(2f, 6f, 10f, 14f),
            Float4(3f, 7f, 11f, 15f),
            Float4(4f, 8f, 12f, 16f)
        )

        internal fun assertArrayEquals(expected: FloatArray, actual: FloatArray, delta: Float = 0.0001f) {
            assertEquals(expected.size, actual.size)
            assertTrue(
                expected.zip(actual).all { (a, b) -> (a - b).absoluteValue < delta },
                "Expected: ${expected.contentToString()}, but got: ${actual.contentToString()}"
            )
        }

        // Use it to display Mat4.toString() instead of FloatArray
        internal fun assertMatEquals(expected: Mat4, actual: Mat4, delta: Float = 0.0001f) {
            assertTrue(
                    expected.toFloatArray().zip(actual.toFloatArray()).all { (a, b) -> (a - b).absoluteValue < delta },
                    "\nExpected:\n${expected}\nbut got:\n${actual}"
            )
        }

        /**
         * @return a FloatArray containing n floats 1f,2f,...,n (float) where n
         * is the @receiver integer.
         */
        private fun Int.floatArray() = FloatArray(this) { (it + 1).toFloat() }
    }
}
