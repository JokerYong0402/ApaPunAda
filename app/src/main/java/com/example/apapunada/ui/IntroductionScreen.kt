package com.example.apapunada.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroductionPager(initialPage: Int = 0) {
    val pagerState = rememberPagerState(initialPage) {
        3
    }

    HorizontalPager(state = pagerState) {currentPage ->
        IntroductionScreen(selectedPage = currentPage + 1)
    }
}

@Composable
fun IntroductionScreen(
    modifier: Modifier = Modifier,
    selectedPage: Int
){
    val primaryColor = colorResource(R.color.primary)
    var imgPager by remember { mutableStateOf(selectedPage) }

    val imageRes = when(imgPager){
        1 -> R.drawable.intro1
        2 -> R.drawable.intro2
        else -> R.drawable.intro3
    }

    val colorRes = when(imgPager){
        1 -> 0xFF8bcefe
        2 -> 0xFFffd88d
        else -> 0xFFc9fff0
    }

    val stringRes1 = when(imgPager){
        1 -> R.string.intro_1
        2 -> R.string.intro_2
        else -> R.string.intro_3
    }

    val stringRes2 = when(imgPager){
        1 -> R.string.intro_sub_1
        2 -> R.string.intro_sub_2
        else -> R.string.intro_sub_3
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Spacer(modifier = Modifier)

            // Image and background
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(width = 325.dp, height = 325.dp)
                        .background(
                            color = Color(colorRes),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = "",
                        modifier = Modifier
                            .background(color = Color(colorRes))
                            .size(
                                width = 275.dp,
                                height = 275.dp
                            )
                    )
                }
            }

            // Introduction text and subtext
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(stringRes1),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                Text(
                    text = stringResource(stringRes2),
                    fontSize = 15.sp,
                    color = Color(0xFF9095a1)
                )
            }

            Spacer(modifier = Modifier)

            // Login and sign up button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.size(325.dp, 110.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            fontSize = 18.sp
                        )
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.sign_up),
                            color = primaryColor,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            for (i in 1..3) {
                if (imgPager == i) {
                    Canvas(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(10.dp)
                            .clip(CircleShape)
                            .clickable { imgPager = i /* TODO */},
                        onDraw = {
                            drawCircle(color = primaryColor, radius = this.size.minDimension / 3.5f)
                            drawCircle(color = primaryColor, radius = this.size.minDimension / 4.5f)
                        }
                    )
                } else {
                    Canvas(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(10.dp)
                            .clip(CircleShape)
                            .clickable { imgPager = i  /* TODO */},
                        onDraw = {
                            drawCircle(color = primaryColor, radius = this.size.minDimension / 3.5f)
                            drawCircle(color = Color.White, radius = this.size.minDimension / 4.5f)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionScreenPreview() {
    IntroductionScreen(
        modifier = Modifier,
        selectedPage = 1
    )
}