package com.example.apapunada.ui.users

import androidx.annotation.DrawableRes
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
import com.example.apapunada.MoreScreen
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyBottomNavBar
import com.example.apapunada.ui.components.MyTopAppBar
import com.example.apapunada.viewmodel.AuthViewModel

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {

    val primaryColor = colorResource(R.color.primary)
    val currentUser = authViewModel.userState.value.user

    Scaffold(
        topBar = {
            MyTopAppBar(authViewModel.userState.value.user, navController, authViewModel)
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
                            .clickable { navController.navigate("UserProfile") }
                    ) {
                        // Profile pic, username, points
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Row {
                                Image(
                                    painter = painterResource(R.drawable.profile_image), //TODO user profile
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
                                        text = currentUser.username,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(5.dp))

                                    Text(
                                        text = "Points ${currentUser.point}",
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
                                    fontWeight = FontWeight.SemiBold,
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
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                        ) {
                            MoreCard(
                                title = "Menu",
                                id = R.drawable.menuicon,
                                onClicked = { navController.navigate("UserMenu") }
                            )

                            MoreCard(
                                title = "Feedback",
                                id = R.drawable.feedbackicon,
                                onClicked = { navController.navigate(MoreScreen.Feedback.name) }
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                        ) {
                            MoreCard(
                                title = "About Us",
                                id = R.drawable.aboutusicon,
                                onClicked = { navController.navigate(MoreScreen.AboutUs.name) }
                            )

                            MoreCard(
                                title = "Order History",
                                id = R.drawable.historyicon,
                                onClicked = { navController.navigate(MoreScreen.OrderHistory.name) }
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                        ) {
                            MoreCard(
                                title = "Faq",
                                id = R.drawable.faqicon,
                                onClicked = { navController.navigate(MoreScreen.Faq.name) }
                            )

                            MoreCard(
                                title = "T&C",
                                id = R.drawable.tncicon,
                                onClicked = { navController.navigate(MoreScreen.Tnc.name) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoreCard(
    title: String,
    @DrawableRes id: Int,
    onClicked: () -> Unit
) {
    val primaryColor = colorResource(R.color.primary)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .size(170.dp, 82.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = primaryColor,
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClicked() },
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id),
                    contentDescription = title,
                    modifier = Modifier.size(40.dp, 40.dp)
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = primaryColor,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoreScreenPreview() {
//    MoreScreen(navController = rememberNavController())
}