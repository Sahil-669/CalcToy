package com.example.calctoy.ui.screen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.layout.Layout
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
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calctoy.ui.theme.Lato
import com.example.calctoy.ui.theme.acButton
import com.example.calctoy.ui.theme.equalButton
import com.example.calctoy.ui.theme.numberButton
import com.example.calctoy.ui.theme.operatorButton
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min
import kotlin.text.iterator


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
            autoResizeText(
                expression = expression,
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
                         val buttonColor = when (symbol) {
                            "AC" -> MaterialTheme.colorScheme.acButton
                            "=" -> MaterialTheme.colorScheme.equalButton
                            "+", "-", "x", "/", "%", "( )" -> MaterialTheme.colorScheme.operatorButton
                            else -> MaterialTheme.colorScheme.numberButton
                        }
                        CalculatorButton(
                            symbol = symbol,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .fillMaxHeight(),
                            backgroundColor = buttonColor
                        ) {
                            when (symbol) {
                                "=" -> result = evaluateExpression(expression)
                                "AC" -> { expression = ""
                                          result = ""
                                }
                                "⌫" -> {
                                    if (expression.isNotEmpty())
                                    expression = expression.dropLast(1)
                                }

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
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            onClick = {onClick()},
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            containerColor = backgroundColor
            ) {
            Text(text = symbol, fontFamily = Lato, fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Normal)
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

    val df = DecimalFormat("0.##########")
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
@Composable
fun autoResizeText(
    expression: String,
    modifier: Modifier = Modifier,
    maxNumberFontSize: TextUnit = 60.sp,
    minNumberFontSize: TextUnit = 20.sp,
    maxOperatorFontSize: TextUnit = 40.sp,
    minOperatorFontSize: TextUnit = 10.sp
) {
    var numberFontSize by remember { mutableStateOf(maxNumberFontSize) }
    var operatorFontSize by remember { mutableStateOf(maxOperatorFontSize) }
    var prevLength by remember { mutableIntStateOf(expression.length) }
    val animatedNumberFontSize by animateFloatAsState(targetValue = numberFontSize.value)
    val animatedOperatorFontSize by animateFloatAsState(targetValue = operatorFontSize.value)
    LaunchedEffect(expression.length) {
        if (expression.isEmpty()) {
            numberFontSize = maxNumberFontSize
            operatorFontSize = maxOperatorFontSize
        }
        else if (expression.length < prevLength) {
            numberFontSize = min(maxNumberFontSize.value, numberFontSize.value * 1.1f).sp
            operatorFontSize = min(maxOperatorFontSize.value, operatorFontSize.value * 1.1f).sp
        }
        prevLength = expression.length
    }
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                val numberStyle = SpanStyle(
                    fontSize = animatedNumberFontSize.sp,
                    fontFamily = Lato,
                    fontWeight = FontWeight.Bold
                )
                val operatorStyle = SpanStyle(
                    fontSize = animatedOperatorFontSize.sp,
                    fontFamily = Lato,
                    fontWeight = FontWeight.Bold,
                    baselineShift = BaselineShift(0.5f)
                )
                for (ch in expression) {
                    if (ch in "+-x/") {
                        withStyle(operatorStyle) { append(ch) }
                    } else {
                        withStyle(numberStyle) { append(ch) }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.End,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip,
            onTextLayout = { result ->
                if (result.didOverflowWidth && numberFontSize > minNumberFontSize && operatorFontSize > minOperatorFontSize) {
                    numberFontSize *= .95f
                    operatorFontSize *= .95f
                }
            }
        )
    }
}