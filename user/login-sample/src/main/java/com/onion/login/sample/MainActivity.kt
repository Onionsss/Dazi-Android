package com.onion.login.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.onion.login.LoginActivity
import com.onion.login.sample.ui.theme.DaziTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login()
        setContent {
            DaziTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android") {
                        login()
                    }
                }
            }
        }
    }

    fun login(){
        startActivity(Intent(this,LoginActivity::class.java))
    }
}

@Composable
fun Greeting(name: String, login: () -> Unit) {
    Text(
        text = "Hello $name!",
        modifier = Modifier.clickable {
            login.invoke()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DaziTheme {
        Greeting("Android",{})
    }
}