package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import com.example.apapunada.R
import androidx.compose.material3.Button
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun FeedbackSuccessPager() {
    FeedbackSuccessScreen(
        onClick = { }
    )
}

@Composable
fun FeedbackSuccessScreen(
    onClick: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
//        Column (
//            horizontalAlignment = Alignment.End,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(18.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.feedback5),
//                contentDescription = "feedback5",
//                modifier = Modifier
//                    .size(18.dp)
//            )
//        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.feedback4),
                contentDescription = "feedback4",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.feedback_7),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 30.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.feedback_8),
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 30.dp)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(95.dp)
                    .padding(
                        top = 30.dp
                    )
                ,
                onClick = onClick
            ) {
                Text(
                    text = "Back to \n Home Page",
                    fontSize = 18.sp,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackSuccessPreview () {
    FeedbackSuccessScreen(
        onClick = { }
    )
}

