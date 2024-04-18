package com.example.apapunada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.apapunada.ui.DeleteProfileScreen
import com.example.apapunada.ui.EditProfileScreen
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.ui.IntroductionScreen
import com.example.apapunada.ui.ProfileScreen
import com.example.apapunada.ui.components.MyBottomNavBar
import com.example.apapunada.ui.components.MyTopAppBar
import com.example.apapunada.ui.theme.ApaPunAdaTheme
import com.example.apapunada.ui.MoreScreen

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
        /*topBar = {
            MyTopAppBar(
                "User_1",
                R.drawable.profile_image
            )
        },*/
        //bottomBar = { MyBottomNavBar(1) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            //IntroductionPager()
            //ProfileScreen()
            //EditProfileScreen()
            DeleteProfileScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApaPunAdaPreview() {
    ApaPunAda()
}