package com.example.calctoy.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calctoy.ui.theme.Lato
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat


@Composable
fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = buildAnnotatedString {
                val numberStyle = SpanStyle(
                    fontSize = 60.sp,
                    fontFamily = Lato,
                    fontWeight = FontWeight.Bold
                )
                val operatorStyle = SpanStyle(
                    fontSize = 40.sp,
                    fontFamily = Lato,
                    fontWeight = FontWeight.Bold,
                    baselineShift = BaselineShift(0.5f)
                )
                for (ch in expression) {
                    when (ch ) {
                        '+', '-', 'x', '/' -> withStyle(operatorStyle) { append(ch) }
                        else -> withStyle(numberStyle) { append(ch) }
                    }
                }
                },
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = result,
                fontSize = 30.sp,
                fontFamily = Lato,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Buttons
        val buttons = listOf(
            listOf("AC", "( )", "%", "/"),
            listOf("7", "8", "9", "x"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "⌫", "=")
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { symbol ->
                        CalculatorButton(
                            symbol = symbol,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        ) {
                            when (symbol) {
                                "=" -> result = evaluateExpression(expression)
                                "AC" -> { expression = ""
                                          result = ""
                                }
                                "⌫" -> {
                                    if (expression.isNotEmpty())
                                    expression = expression.dropLast(1)}
                                "( )" -> expression = handleParentheses(expression)
                                else -> {
                                    expression += symbol}
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            onClick = {onClick()},
            modifier = Modifier.size(85.dp),
            shape = CircleShape
            ) {
            Text(text = symbol, fontFamily = Lato, fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

private fun evaluateExpression(expr: String): String {
    return try {
        var clean = expr
            .replace("x", "*")

        clean = clean.replace(Regex("""(\d+(?:\.\d+)?)%""")) { m ->
            "(${m.groupValues[1]}/100)"
        }

        val value = ExpressionBuilder(clean).build().evaluate()

        formatResult(value)
    } catch (e: Exception) {
        "Error"
    }
}

private fun formatResult(value: Double): String {
    if (value.isFinite() && value % 1.0 == 0.0) {
        return value.toLong().toString()
    }

    val df = DecimalFormat("0.##########") // up to 10 decimal places
    return df.format(value)
}

private fun handleParentheses(expr: String): String {
    val openCount = expr.count { it == '(' }
    val closeCount = expr.count { it == ')' }
    if (expr.isEmpty() || expr.last() in "+-*/(" )
        return "$expr("
    if (openCount > closeCount)
        return "$expr)"

    return "$expr("
}



