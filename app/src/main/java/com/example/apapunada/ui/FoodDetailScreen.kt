package com.example.apapunada.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.data.DataOfPopularDishes
import com.example.apapunada.model.PopularDishes
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun FoodDetailScreen() {
    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.menu)) },
        //bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
        {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.fish_chipspic),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp, 20.dp))
                            .width(400.dp)
                            .height(400.dp)
                    )
                }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(550.dp),
                            //.padding(vertical = 10.dp)
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                /*.size(
                                    250.dp,
                                    150.dp
                                )*/
                                .padding(horizontal = 8.dp)
                                .shadow(
                                    elevation = 15.dp,
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp,vertical = 20.dp),
                                    //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ){
                                Text(
                                    text = "Fish & Chips",
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp,vertical = 10.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ){
                                    Image(
                                        painter = painterResource(R.drawable.staricon),
                                        contentDescription = "Beef Burger",
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(25.dp)
                                    )
                                    Text(
                                        text = "4.7",
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ){
                                    Image(
                                        painter = painterResource(R.drawable.moneyicon),
                                        contentDescription = "",
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(25.dp)
                                    )
                                    Text(
                                        text = "22.90",
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )

                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.servingicon),
                                        contentDescription = "",
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(25.dp)
                                    )
                                    Text(
                                        text = "1",
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
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
fun FoodDetailScreenPreview() {
    FoodDetailScreen()
}