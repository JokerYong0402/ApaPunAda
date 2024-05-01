package com.example.apapunada.ui.staff

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StaffHomeScreen(
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Text("Landscape")
        }
        else -> {
            Text("Portrait")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StaffHomeScreenPreview(){
    StaffHomeScreen()
}