package ru.suntyra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito

class TrigonometryFuncTest {
    @Nested
    inner class GenericTrigonometryEvaluatorUnitTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it computes sine`(
            x: Double,
            sin: Double,
            cos: Double,
            _tan: Double,
            _cot: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ISin::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(sin, TrigonometryFunc(mockTrigEval).sin(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it computes tangent`(
            x: Double,
            sin: Double,
            cos: Double,
            tan: Double,
            _cot: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ISin::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(tan, TrigonometryFunc(mockTrigEval).tan(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it computes cotangent`(
            x: Double,
            sin: Double,
            cos: Double,
            _tan: Double,
            cot: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ISin::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), anyDouble())).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos)

            Assertions.assertEquals(cot, TrigonometryFunc(mockTrigEval).cot(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/invalid-trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it returns NaN when called with invalid parameters`(
            x: Double,
            sin: Double,
            cos: Double,
            tan: Double,
            cot: Double,
            eps: Double
        ) {
            val mockTrigEval = Mockito.mock(ISin::class.java)
            Mockito.`when`(mockTrigEval.sin(eq(x), doubleThat { !(it.isNaN() || it.isInfinite()) })).thenReturn(sin)
            Mockito.`when`(mockTrigEval.sin(eq(x), doubleThat { it.isNaN() || it.isInfinite() })).thenReturn(Double.NaN)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), doubleThat { !(it.isNaN() || it.isInfinite()) }))
                .thenReturn(cos)
            Mockito.`when`(mockTrigEval.sin(eq(x + Math.PI / 2), doubleThat { it.isNaN() || it.isInfinite() }))
                .thenReturn(Double.NaN)

            Assertions.assertEquals(sin, TrigonometryFunc(mockTrigEval).sin(x, eps), 1E-6)
            Assertions.assertEquals(tan, TrigonometryFunc(mockTrigEval).tan(x, eps), 1E-6)
            Assertions.assertEquals(cot, TrigonometryFunc(mockTrigEval).cot(x, eps), 1E-6)
        }
    }

    @Nested
    inner class GenericTrigonometryEvaluatorIntegrationTest {
        @ParameterizedTest
        @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it computes sine`(
            x: Double,
            sin: Double,
            _cos: Double,
            _tan: Double,
            _cot: Double,
            eps: Double
        ) {
            Assertions.assertEquals(sin, TrigonometryFunc(Sin()).sin(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it computes tangent`(
            x: Double,
            _sin: Double,
            _cos: Double,
            tan: Double,
            _cot: Double,
            eps: Double
        ) {
            Assertions.assertEquals(tan, TrigonometryFunc(Sin()).tan(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it computes cotangent`(
            x: Double,
            _sin: Double,
            _cos: Double,
            _tan: Double,
            cot: Double,
            eps: Double
        ) {
            Assertions.assertEquals(cot, TrigonometryFunc(Sin()).cot(x, eps), eps)
        }

        @ParameterizedTest
        @CsvFileSource(resources = ["/invalid-trigonometry-test.csv"], numLinesToSkip = 1)
        fun `it returns NaN when called with invalid parameters`(
            x: Double,
            sin: Double,
            cos: Double,
            tan: Double,
            cot: Double,
            eps: Double
        ) {
            Assertions.assertEquals(sin, TrigonometryFunc(Sin()).sin(x, eps), 1E-6)
            Assertions.assertEquals(tan, TrigonometryFunc(Sin()).tan(x, eps), 1E-6)
            Assertions.assertEquals(cot, TrigonometryFunc(Sin()).cot(x, eps), 1E-6)
        }
    }
}
