package com.example.apapunada.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedbackPager(initialPage: Int = 0) {
    FeedbackScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .width(350.dp)
            .fillMaxSize(),

    ){
        TopAppBar(
            modifier = modifier
                .shadow(2.dp),
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                        //.contentAlignment(Alignment.Center) // Centers horizontally
                        .padding(top = 8.dp, bottom = 8.dp) // Example padding for vertical centering
                ) {
                    Text(text = stringResource(R.string.feedback))
                }

            },

            navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.feedback_1),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(R.color.primary),
            )

            Text(
                text = stringResource(R.string.feedback_2),
                fontSize = 15.sp,
                color = Color(0xFF9095a1)
            )
        }
        starRating(rating = 4.5F)
    }
}

fun starRating(rating: Float): String {
    val fullStars = rating.toInt()
    val partialStars = rating - fullStars
    var starString = ""
    for (i in 0 until fullStars) {
        starString += "★"
    }
    if (partialStars > 0.5f) {
        starString += "☆"
    }
    return starString
}

@Preview(showBackground = true)
@Composable
fun FeedbackScreenPreview() {
    FeedbackScreen(
        modifier = Modifier
    )
}