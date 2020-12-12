package ru.suntyra

import kotlin.math.abs

interface ISin {
    fun sin(x: Double, eps: Double): Double
}

class Sin : ISin {
    override fun sin(x: Double, eps: Double): Double = when {
        x.isInfinite() || x.isNaN() || eps.isInfinite() || eps.isNaN() -> {
            Double.NaN
        }
        else -> {
            var sum = 0.0
            var term = x
            var i = 1

            while (abs(term) > eps) {
                sum += term
                term *= -x * x / (2 * i * (2 * i + 1))
                i++
            }
            sum
        }
    }
}
