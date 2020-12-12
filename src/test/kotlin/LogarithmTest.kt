package ru.suntyra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class LogarithmTest {
    @ParameterizedTest
    @CsvFileSource(resources = ["/logarithm-test.csv"], numLinesToSkip = 1)
    fun `it approximates natural logarithm`(x: Double, ln: Double, eps: Double) {
        Assertions.assertEquals(ln, Logarithm().ln(x, eps), eps)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/invalid-logarithm-test.csv"], numLinesToSkip = 1)
    fun `it returns NaN when called with invalid parameters`(x: Double, ln: Double, eps: Double) {
        Assertions.assertEquals(ln, Logarithm().ln(x, eps), 1E-6)
    }
}
