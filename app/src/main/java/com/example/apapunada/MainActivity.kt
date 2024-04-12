package com.example.apapunada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.ui.IntroductionScreen
import com.example.apapunada.ui.theme.ApaPunAdaTheme

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
    IntroductionPager()
}

@Preview(showBackground = true)
@Composable
fun ApaPunAdaPreview() {
    ApaPunAda()
}