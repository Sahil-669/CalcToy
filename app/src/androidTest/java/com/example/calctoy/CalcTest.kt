package com.example.calctoy

import org.junit.Assert.*
import org.junit.Test
import com.example.calctoy.ui.screen.evaluateExpression
class ExpressionEvaluatorTest {

    @Test
    fun testSimpleAddition() {
        assertEquals("4", evaluateExpression("2+2"))
    }

    @Test
    fun testDivisionByZero() {
        assertEquals("Can't divide by 0", evaluateExpression("5÷0"))
    }

    @Test
    fun testPercentage() {
        assertEquals("0.1", evaluateExpression("10%"))
    }

}
