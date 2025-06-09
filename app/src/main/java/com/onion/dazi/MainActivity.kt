package com.onion.dazi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.onion.center.ui.MainPage
import com.onion.resource.theme.AppThemeBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberDaziAppState()
            AppThemeBox {
                // A surface container using the 'background' color from the theme
                MainPage()
            }
        }

    }
}

