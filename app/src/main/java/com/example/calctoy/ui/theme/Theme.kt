package com.example.calctoy.ui.theme

import android.app.Activity
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
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val RetroColorScheme = darkColorScheme(
    primary = RetroButton,
    secondary = RetroOperator,
    tertiary = RetroEqual,
    background = RetroBackground,
    surface = RetroBackground,
    onPrimary = RetroButtonText,
    onSecondary = RetroButtonText,
    onTertiary = Color.White
)

val ColorScheme.acButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkACButton else LightACButton

val ColorScheme.equalButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkEqualButton else LightEqualButton

val ColorScheme.operatorButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkOperatorButton else LightOperatorButton

val ColorScheme.numberButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkNumberButton else LightNumberButton
val ColorScheme.pad: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkModernPad else LightModernPad

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