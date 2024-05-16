package com.example.apapunada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.apapunada.ui.staff.StaffFeedbackScreen
import com.example.apapunada.ui.theme.ApaPunAdaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApaPunAdaTheme {
//                OrderNavHost(navController = rememberNavController())
                StaffFeedbackScreen()
//                StaffUI()
//                ApaPunAdaApp()

            }
        }
    }
}

//@Composable
//fun ApaPunAda() {
//    IntroductionPager()
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ApaPunAdaPreview() {
//    ApaPunAda()
//}
