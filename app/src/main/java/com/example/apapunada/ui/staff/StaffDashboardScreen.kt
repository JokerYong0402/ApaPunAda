package com.example.apapunada.ui.staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R

@Composable
fun StaffDashboardScreen() {
    val configuration = LocalConfiguration.current
//    when (configuration.orientation) {
//        Configuration.ORIENTATION_LANDSCAPE -> {
//            StaffDashboardLandscape()
//        }
//        else -> {
//            StaffDashboardPortrait()
//        }
//    }
    StaffDashboardLandscape()
}

@Composable
fun StaffDashboardPortrait() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Composable
fun StaffDashboardLandscape(

) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Overview",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(3) {
                Card(
                    modifier = Modifier
                        .size(350.dp, 200.dp)
                ) {

                }
            }
        }
    }
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