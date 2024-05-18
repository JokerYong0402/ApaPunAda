package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun AboutUsScreen(
    onBackClicked: () -> Unit,
    onFeedbackClicked: () -> Unit,
    onOrderClicked: () -> Unit,
) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.app_name))
        }
        append(" is a 3-star michelin restaurant. Our restaurant was founded in 2021. As the name of our restaurant, " +
                "we are serving many styles of food such as Malaysian, Japanese, Korean, Western and also Thai cuisine." +
                " We only have one branch located in Jalan Genting Kelang, Setapak, 53300 Kuala Lumpur, Federal Territory" +
                " of Kuala Lumpur. There are 4 founders in the restaurant. Yong Jia Ying, Kong Weng Cheng, Ryan Moey Kai " +
                "Xiang and Yoong Hong Sheng.")
    }
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = { MyTopTitleBar(title = "About Us", onBackClicked ) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // content of page
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(300.dp)
                    ){
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 25.dp, top = 10.dp, end = 25.dp, bottom = 100.dp)
                        //.height(470.dp)
                    ) {
                        Text(
                            text = text,
                            fontSize = 18.sp,
                            lineHeight = 30.sp
                        )
                    }

                    Text(
                        modifier = Modifier.padding(25.dp, 10.dp, 25.dp, 10.dp),
                        text = "Best Selling",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column(
                        modifier = Modifier
                            .padding(25.dp, 10.dp, 25.dp, 100.dp)
                            .fillMaxSize()
                    ) {
                        Row{
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(R.drawable.intro2),
                                contentDescription = "Beef Burger"
                            )
                            Row (
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(
                                        text = "Beef Burger",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row {
                                        Icon(
                                            imageVector = Icons.Rounded.Star,
                                            contentDescription = "Star",
                                            tint = Color(0xFFFFD700)
                                        )
                                        Text(
                                            text = "4.7",
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                        Row{
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(R.drawable.intro2),
                                contentDescription = "Prawn Noodle"
                            )
                            Row (
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(
                                        text = "Prawn Noodle",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row {
                                        Icon(
                                            imageVector = Icons.Rounded.Star,
                                            contentDescription = "Star",
                                            tint = Color(0xFFFFD700)
                                        )
                                        Text(
                                            text = "4.5",
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                        Row{
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(R.drawable.intro2),
                                contentDescription = "Grilled Salmon"
                            )
                            Row (
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(
                                        text = "Grilled Salmon",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row {
                                        Icon(
                                            imageVector = Icons.Rounded.Star,
                                            contentDescription = "Star",
                                            tint = Color(0xFFFFD700)
                                        )
                                        Text(
                                            text = "4.8",
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 40.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 30.dp),
                            text = "Write Us A FeedBack!!!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick =  onFeedbackClicked ,
                            colors = ButtonDefaults.buttonColors(),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .widthIn(min = 200.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 15.dp, bottom = 50.dp)
                        ) {
                            Text(text = "FeedBack")
                        }
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(15.dp),
                            text = "Order Now!!!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick =  onOrderClicked ,
                            colors = ButtonDefaults.buttonColors(),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .widthIn(min = 200.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 15.dp, bottom = 20.dp)
                        ) {
                            Text(text = "Order")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutUsScreenPreview() {
//    AboutUsScreen()
}