package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyBottomNavBar

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val primaryColor = colorResource(R.color.primary)
    val point = 100

    Scaffold(
        topBar = {
//            MyTopAppBar(User(), navController) // TODO
        },
        bottomBar = { MyBottomNavBar(5, navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .shadow(
                                elevation = 20.dp,
                                spotColor = primaryColor,
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { /*TODO*/ }
                    ) {
                        // Profile pic, username, points
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Row {
                                Image(
                                    painter = painterResource(R.drawable.profile_image),
                                    contentDescription = "profile pic",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(10.dp)
                                )

                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(dimensionResource(R.dimen.padding_small))
                                ) {
                                    Text(
                                        text = "User",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(5.dp))

                                    Text(
                                        text = "Points $point",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        // My profile and arrow
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(
                                        dimensionResource(R.dimen.padding_medium),
                                        dimensionResource(R.dimen.padding_small)
                                    )
                            ) {
                                Text(
                                    text = "My Profile",
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .padding(2.dp)
                                )

                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                    contentDescription = "Arrow",
                                    tint = primaryColor,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.height(110.dp)
                    ) {
                        Text(
                            text = "More",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 15.dp)
                        )

                        Image(
                            painter = painterResource(R.drawable.more),
                            contentDescription = "more",
                            alignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxSize()
                                .width(200.dp)
                        )
                    }

                    // More options
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        for (i in 1..4) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                            ) {
                                for (j in 1..2) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .size(170.dp, 80.dp)
                                            .shadow(
                                                elevation = 5.dp,
                                                spotColor = primaryColor,
                                            )
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { }
                                    ) {

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoreScreenPreview() {
    MoreScreen(navController = rememberNavController())
}