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

val LightACButton = Color(0xFFFF0000)
val LightEqualButton = Color(0xFF4ECDC4)
val LightOperatorButton = Color(0xFFFFC75F)
val LightNumberButton = Color(0xFFE0E0E0)


val DarkACButton = Color(0xFFEF5350)
val DarkEqualButton = Color(0xFF26A69A)
val DarkOperatorButton = Color(0xFFFFB74D)
val DarkNumberButton = Color(0xFF424242)

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

val ColorScheme.acButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkACButton else LightACButton

val ColorScheme.equalButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkEqualButton else LightEqualButton

val ColorScheme.operatorButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkOperatorButton else LightOperatorButton

val ColorScheme.numberButton: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkNumberButton else LightNumberButton

@Composable
fun CalcToyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
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