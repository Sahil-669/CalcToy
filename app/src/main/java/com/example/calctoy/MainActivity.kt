package com.example.calctoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.calctoy.ui.screen.CalculatorScreen
import com.example.calctoy.ui.theme.CalcToyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var retroTheme by remember { mutableStateOf(false) }
            CalcToyTheme (
                retroTheme = retroTheme,
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                Surface (color = MaterialTheme.colorScheme.background) {

                    CalculatorScreen(toggleTheme = {retroTheme = !retroTheme})
                }
            }
        }
    }
}


