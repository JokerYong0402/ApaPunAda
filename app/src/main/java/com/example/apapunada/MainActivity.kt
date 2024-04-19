package com.example.apapunada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apapunada.ui.FeedbackPager
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.ui.WaitlistPager
import com.example.apapunada.ui.components.MyBottomNavBar
import com.example.apapunada.ui.components.MyTopAppBar

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
    Scaffold(
        topBar = {
            MyTopAppBar(
                "User_1",
                R.drawable.profile_image
            )
        },
        bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            WaitlistPager()
            //FeedbackPager()
            //IntroductionPager()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApaPunAdaPreview() {
    ApaPunAda()
}