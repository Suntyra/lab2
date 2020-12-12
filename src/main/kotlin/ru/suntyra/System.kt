package ru.suntyra

import kotlin.math.E
import kotlin.math.pow

class System(
    private val trigEval: ITrigonometryFunc,
    private val logEval: ILogarithmFunc
) {
    fun compute(x: Double, eps: Double): Double =
        if (x <= 0) {
            val sinx = trigEval.sin(x, eps)
            val tanx = trigEval.tan(x, eps)
            val cotx = trigEval.cot(x, eps)

            val numerator = ((cotx - tanx).pow(2) / sinx).pow(3)
            val denominator = cotx.pow(3) - sinx
            (numerator / denominator)
        } else {
            val log2x = logEval.log(x, base = 2.0, eps)
            val log3x = logEval.log(x, base = 3.0, eps)
            val log10x = logEval.log(x, base = 10.0, eps)
            val lnx = logEval.log(x, base = E, eps)

            (((((log2x / log3x) + lnx) - lnx) * log10x).pow(3))
        }
}
