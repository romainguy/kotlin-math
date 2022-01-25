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

class QuaternionTest {

    @Test
    fun fromAxisAngle() {
        MatrixTest.assertArrayEquals(
                Quaternion(0.0093f, 0.0186f, 0.0280f, 0.9994f).toFloatArray(),
                Quaternion.fromAxisAngle(Float3(1.0f, 2.0f, 3.0f), 4.0f).toFloatArray()
        )
    }

    @Test
    fun fromEuler() {
        MatrixTest.assertArrayEquals(
                Quaternion(0.0083f, 0.0177f, 0.0263f, 0.9995f).toFloatArray(),
                Quaternion.fromEuler(Float3(1.0f, 2.0f, 3.0f)).toFloatArray()
        )
    }

    @Test
    fun toEuler() {
        MatrixTest.assertArrayEquals(
                Float3(45.0f, 19.4712f, 81.8699f).toFloatArray(),
                eulerAngles(Quaternion(1.0f, 2.0f, 3.0f, 4.0f)).toFloatArray()
        )
    }
}
