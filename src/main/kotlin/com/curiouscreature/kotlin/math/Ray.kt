package com.curiouscreature.kotlin.math

data class Ray(var origin: Float3 = Float3(), var direction: Float3)

fun pointAt(r: Ray, t: Float) = r.origin + r.direction * t
