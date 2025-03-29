package me.ibrahim.bonialbrochures.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import me.ibrahim.bonialbrochures.presentation.composables.BrochuresScreen
import me.ibrahim.bonialbrochures.ui.theme.BonialBrochuresTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BonialBrochuresTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Color.LightGray.copy(alpha = 0.5f)
                ) { innerPadding ->
                    BrochuresScreen(innerPadding)
                }
            }
        }
    }
}