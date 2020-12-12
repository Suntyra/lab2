package ru.suntyra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito

class LogarithmFuncTest {
    @Nested
    inner class LogarithmFuncUnitTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/logarithm-func-test.csv"], numLinesToSkip = 1)
        fun `it computes logarithm of any base`(
            y: Double,
            x: Double,
            base: Double,
            logX: Double,
            logBase: Double,
            eps: Double
        ) {
            val mockLnEval = Mockito.mock(ILogarithm::class.java)
            Mockito.`when`(mockLnEval.ln(eq(x), anyDouble())).thenReturn(logX)
            Mockito.`when`(mockLnEval.ln(eq(base), anyDouble())).thenReturn(logBase)

            val logEval = LogarithmFunc(mockLnEval)
            Assertions.assertEquals(y, logEval.log(x, base, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/invalid-logarithm-func-test.csv"], numLinesToSkip = 1)
        fun `it returns NaN when called with invalid parameters`(
            y: Double,
            x: Double,
            base: Double,
            logX: Double,
            logBase: Double,
            eps: Double
        ) {
            val mockLnEval = Mockito.mock(ILogarithm::class.java)
            Mockito.`when`(mockLnEval.ln(eq(x), doubleThat { !(it.isNaN() || it.isInfinite()) })).thenReturn(logX)
            Mockito.`when`(mockLnEval.ln(eq(base), doubleThat { !(it.isNaN() || it.isInfinite()) })).thenReturn(logBase)
            Mockito.`when`(mockLnEval.ln(eq(x), doubleThat { it.isNaN() || it.isInfinite() })).thenReturn(Double.NaN)
            Mockito.`when`(mockLnEval.ln(eq(base), doubleThat { it.isNaN() || it.isInfinite() })).thenReturn(Double.NaN)

            val logEval = LogarithmFunc(mockLnEval)
            Assertions.assertEquals(y, logEval.log(x, base, eps), 1E-6)
        }
    }

    @Nested
    inner class LogarithmFuncIntegrationTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/logarithm-func-test.csv"], numLinesToSkip = 1)
        fun `it computes logarithm of any base`(
            y: Double,
            x: Double,
            base: Double,
            _logX: Double,
            _logBase: Double,
            eps: Double
        ) {
            val lnEval = Logarithm()
            val logEval = LogarithmFunc(lnEval)
            Assertions.assertEquals(y, logEval.log(x, base, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/invalid-logarithm-func-test.csv"], numLinesToSkip = 1)
        fun `it returns NaN when called with invalid parameters`(
            y: Double,
            x: Double,
            base: Double,
            _logX: Double,
            _logBase: Double,
            eps: Double
        ) {
            val lnEval = Logarithm()
            val logEval = LogarithmFunc(lnEval)
            Assertions.assertEquals(y, logEval.log(x, base, eps), 1E-6)
        }
    }
}
