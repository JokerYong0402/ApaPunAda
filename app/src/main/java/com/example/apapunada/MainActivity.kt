package com.example.apapunada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.apapunada.ui.OrderMenuScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ApaPunAdaTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }
//                ApaPunAda()
//            }
            ApaPunAda()
        }
    }
}

@Composable
fun ApaPunAda() {
    OrderMenuScreen()
}

@Preview(showBackground = true)
@Composable
fun ApaPunAdaPreview() {
    ApaPunAda()
}