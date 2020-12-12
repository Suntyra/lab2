package ru.suntyra

import kotlin.math.abs
import kotlin.math.pow

interface ILogarithm {
    fun ln(x: Double, eps: Double): Double
}

class Logarithm : ILogarithm {
    override fun ln(x: Double, eps: Double): Double = when {
        x <= 0 || x <= eps || x.isNaN() || eps.isNaN() || eps.isInfinite() -> {
            Double.NaN
        }
        x == Double.POSITIVE_INFINITY -> {
            Double.POSITIVE_INFINITY
        }
        x < 1 -> {
            val z = x - 1
            var term = z
            var sum = z
            var i = 2
            while (abs(term) >= eps) {
                term *= -z
                sum += term / i
                i++
            }
            sum
        }
        else -> {
            val z = x / (x - 1.0)
            var term = z
            var sum = 0.0
            var i = 1
            while (abs(term) > eps) {
                term = 1.0 / (i * z.pow(i))
                sum += term
                i++
            }
            sum
        }
    }
}
