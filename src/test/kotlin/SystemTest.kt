package ru.suntyra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.Mockito.*
import kotlin.math.E

class SystemTest {
    fun mockTrigEvaluator(
        x: Double,
        sin: Double,
        tan: Double,
        cot: Double,
        eps: Double
    ): ITrigonometryFunc =
        mock(ITrigonometryFunc::class.java).apply {
            `when`(sin(eq(x), eq(eps))).thenReturn(sin)
            `when`(tan(eq(x), eq(eps))).thenReturn(tan)
            `when`(cot(eq(x), eq(eps))).thenReturn(cot)
        }

    fun mockLogEvaluator(
        x: Double,
        ln: Double,
        log2: Double,
        log3: Double,
        log10: Double,
        eps: Double
    ): ILogarithmFunc =
        mock(ILogarithmFunc::class.java).apply {
            `when`(log(eq(x), eq(E), eq(eps))).thenReturn(ln)
            `when`(log(eq(x), eq(2.0), eq(eps))).thenReturn(log2)
            `when`(log(eq(x), eq(3.0), eq(eps))).thenReturn(log3)
            `when`(log(eq(x), eq(10.0), eq(eps))).thenReturn(log10)
        }

    @Nested
    inner class SystemUnitTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/system-test.csv"], numLinesToSkip = 1)
        fun `it computes the equation for valid inputs`(
            x: Double,
            y: Double,
            sin: Double,
            tan: Double,
            cot: Double,
            ln: Double,
            log2: Double,
            log3: Double,
            log10: Double,
            eps: Double
        ) {
            val mockTrig = mockTrigEvaluator(x, sin, tan, cot, eps)
            val mockLog = mockLogEvaluator(x, ln, log2, log3, log10, eps)
            val system = System(mockTrig, mockLog)
            Assertions.assertEquals(y, system.compute(x, eps), eps)
        }
    }

    @Nested
    inner class SystemIntegrationTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/system-test.csv"], numLinesToSkip = 1)
        fun `it integrates logarithm evaluator`(
            x: Double,
            y: Double,
            sin: Double,
            tan: Double,
            cot: Double,
            _ln: Double,
            _log2: Double,
            _log3: Double,
            _log10: Double,
            eps: Double
        ) {
            val mockTrig = mockTrigEvaluator(x, sin, tan, cot,  eps)
            val system = System(mockTrig, LogarithmFunc(Logarithm()))
            Assertions.assertEquals(y, system.compute(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/system-test.csv"], numLinesToSkip = 1)
        fun `it integrates trigonometry evaluator`(
            x: Double,
            y: Double,
            _sin: Double,
            _tan: Double,
            _cot: Double,
            ln: Double,
            log2: Double,
            log3: Double,
            log10: Double,
            eps: Double
        ) {
            val mockLog = mockLogEvaluator(x, ln, log2, log3, log10, eps)
            val system = System(TrigonometryFunc(Sin()), mockLog)
            Assertions.assertEquals(y, system.compute(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/system-test.csv"], numLinesToSkip = 1)
        fun `it integrates all evaluators`(
            x: Double,
            y: Double,
            _sin: Double,
            _tan: Double,
            _cot: Double,
            _ln: Double,
            _log2: Double,
            _log3: Double,
            _log10: Double,
            eps: Double
        ) {
            val system = System(
                TrigonometryFunc(Sin()),
                LogarithmFunc(Logarithm())
            )
            Assertions.assertEquals(y, system.compute(x, eps), eps)
        }
    }
}
