package ru.suntyra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class TrigonometryTest {
    @ParameterizedTest
    @CsvFileSource(resources = ["/trigonometry-test.csv"], numLinesToSkip = 1)
    fun `it approximates sine`(
        x: Double,
        sin: Double,
        _cos: Double,
        _tan: Double,
        _cot: Double,
        eps: Double
    ) {
        Assertions.assertEquals(sin, Sin().sin(x, eps), eps)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/invalid-trigonometry-test.csv"], numLinesToSkip = 1)
    fun `it returns NaN when called with invalid parameters`(
        x: Double,
        sin: Double,
        _cos: Double,
        _tan: Double,
        _cot: Double,
        _sec: Double,
        eps: Double
    ) {
        Assertions.assertEquals(sin, Sin().sin(x, eps), 1E-6)
    }
}
