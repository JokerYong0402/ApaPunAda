package com.example.apapunada.ui.staff

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StaffDashboardScreen() {
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

@Composable
fun StaffDashboardPortrait() {

}

@Composable
fun StaffDashboardLandscape() {

}

@Preview(showBackground = true)
@Composable
fun StaffDashboardPortraitPreview(){
    StaffDashboardScreen()
}

@Preview(showBackground = true, device = TABLET)
@Composable
fun StaffDashboardLandscapePreview(){
    StaffDashboardScreen()
}