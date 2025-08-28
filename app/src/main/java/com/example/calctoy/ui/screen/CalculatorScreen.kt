package com.example.calctoy.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                text = expression,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = result,
                fontSize = 24.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Buttons
        val buttons = listOf(
            listOf("AC", "()", "%", "/"),
            listOf("7", "8", "9", "X"),
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
                                else -> expression += symbol
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
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(text = symbol, fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

private fun evaluateExpression(expr: String): String {
    return try {
        var clean = expr
            .replace("X", "*")

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



