@file:Suppress("UnusedReceiverParameter")

package com.example.calctoy.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Color.Black,
    surface = DarkModernPad,
    onBackground = DarkResult,
    primary = DarkNumberButton,
    onPrimary = Color.Black,
    secondary = DarkOperatorButton,
    onSecondary = Color.Gray,
    tertiary = DarkEqualButton,
    onTertiary = Color.White,
).copy(
    scrim = DarkACButton
)

private val LightColorScheme = lightColorScheme(
    background = Color.White,
    surface = LightModernPad,
    onBackground = LightResult,
    primary = LightNumberButton,
    onPrimary = Color.Black,
    secondary = LightOperatorButton,
    onSecondary = Color.Gray,
    tertiary = LightEqualButton,
    onTertiary = Color.Black
).copy(
        scrim = LightACButton
)


private val RetroColorScheme = darkColorScheme(
    background = RetroBackground,
    surface = RetroPad,
    onBackground = RetroResult,
    primary = RetroButton,
    onPrimary = RetroButtonText,
    secondary = RetroOperator,
    onSecondary = Color.Gray,
    tertiary = RetroEqual,
    onTertiary = Color.White
).copy(
    scrim = RetroSpecial,
)

val ColorScheme.acButton get() = scrim

val ColorScheme.equalButton get() = tertiary

val ColorScheme.operatorButton get() = secondary

val ColorScheme.numberButton get() = primary
val ColorScheme.pad get() = surface
val ColorScheme.resultColor get() = onBackground
val ColorScheme.expressionColor get() = onTertiary
val ColorScheme.operatorTextColor get() = onSecondary

@Composable
fun CalcToyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    retroTheme: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        retroTheme -> RetroColorScheme
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}