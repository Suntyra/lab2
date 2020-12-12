package ru.suntyra

import java.io.File
import kotlin.math.pow

const val precision = 8
val epsilon = 10.0.pow(-precision)

val availableFunctions = mapOf<String, (Double) -> Double>(
    "sin" to ({ x -> TrigonometryFunc(Sin()).sin(x, epsilon) }),
    "tan" to ({ x -> TrigonometryFunc(Sin()).tan(x, epsilon) }),
    "cot" to ({ x -> TrigonometryFunc(Sin()).cot(x, epsilon) }),
    "ln" to ({ x -> Logarithm().ln(x, epsilon) }),
    "log2" to ({ x -> LogarithmFunc(Logarithm()).log(x, 2.0, epsilon) }),
    "log3" to ({ x -> LogarithmFunc(Logarithm()).log(x, 3.0, epsilon) }),
    "log10" to ({ x -> LogarithmFunc(Logarithm()).log(x, 10.0, epsilon) }),
    "f" to ({ x ->
        System(
            TrigonometryFunc(Sin()),
            LogarithmFunc(Logarithm())
        ).compute(x, epsilon)
    }),
)

fun main(args: Array<String>) {
    val location = args.getOrNull(0)
    val functionName = args.getOrNull(1)
    val function = functionName?.let(availableFunctions::get)
    val from = args.getOrNull(2)?.toDoubleOrNull()?.takeIf { it.isFinite() }
    val step = args.getOrNull(3)?.toDoubleOrNull()?.takeIf { it.isFinite() }
    val count = args.getOrNull(4)?.toIntOrNull()?.takeIf { it > 0 }

    if (location == null || function == null || from == null || step == null || count == null) {
        return
    }

    val xs = generateSequence(from) { (it + step).roundToPlaces(precision) }.take(count)
    val ys = xs.map(function)

    val csv = xs.zip(ys).joinToString("\n", prefix = "x,${functionName}(x)\n") { (x, y) -> "%.8f,%.8f".format(x, y) }

    File(location).writeText(csv)
}

fun Double.roundToPlaces(places: Int) = "%.${places}f".format(this).toDouble()
