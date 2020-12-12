package ru.suntyra

interface ILogarithmFunc {
    fun log(x: Double, base: Double, eps: Double): Double
}

class LogarithmFunc(private val lnEval: ILogarithm) : ILogarithmFunc {
    override fun log(x: Double, base: Double, eps: Double): Double = when {
        x <= eps -> {
            Double.NaN
        }
        else -> {
            lnEval.ln(x, eps / 1000) / lnEval.ln(base, eps / 1000)
        }
    }
}
