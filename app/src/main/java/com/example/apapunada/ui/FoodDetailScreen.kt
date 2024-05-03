package com.example.apapunada.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.data.MenuSample
import com.example.apapunada.data.MenuSample.Menus
import com.example.apapunada.model.Menu
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedString

@Composable
fun FoodDetailScreen(
    food: Menu = Menus[0]
) {
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
                        //painter = painterResource(R.drawable.fish_chipspic),
                        painter = painterResource(food.image),
                        contentDescription = "",
                        //contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp, 20.dp))
                            .width(400.dp)
                            .height(400.dp)
                    )
                }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                            //.padding(vertical = 10.dp)
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
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
                                    .padding(horizontal = 20.dp, vertical = 20.dp),
                                    //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ){
                                Text(
                                    text = food.name,
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround,
                            ){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ){
                                    Image(
                                        painter = painterResource(R.drawable.staricon),
                                        contentDescription = "",
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(25.dp)
                                    )
                                    Text(//rating
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
                                    /*Image(
                                        painter = painterResource(R.drawable.moneyicon),
                                        contentDescription = "",
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(25.dp)
                                    )*/
                                    Text(//price
                                        text = "RM" + formattedString(food.price),
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
                            Row(//Description Heading
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 5.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ){
                                    Text(
                                        text = "Description",
                                        fontSize = 23.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                            }
                            Row(//Description content
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ){
                                Text(
                                    text = food.description,
                                    fontSize = 20.sp,
                                    //fontWeight = FontWeight.Bold
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .width(330.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 30.dp, bottom = 10.dp)
                                )
                            Row(//Ingredient Heading
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 20.dp,
                                        bottom = 10.dp,
                                        start = 20.dp,
                                        end = 20.dp
                                    ),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ){
                                Text(
                                    text = "Ingredient",
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(//Ingredient content
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ){
                                Text(
                                    text = "1 beef\n2 sliced lemon",
                                    fontSize = 20.sp,
                                    //fontWeight = FontWeight.Bold
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .width(330.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 30.dp, bottom = 10.dp)
                            )
                            Row(//Nutrition Facts Heading
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 25.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ){
                                Text(
                                    text = "Nutrition Facts",
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row( //Nutrition Facts Content (Carbohydrates)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 15.dp),
                                //.horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Column(
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    NutritionBar(
                                        progress = 0.5f,
                                        progressColor = R.color.primary,
                                        modifier = Modifier
                                            .height(15.dp)
                                            .clip(RoundedCornerShape(20.dp)),
                                        backgroundColor = R.color.purple_200
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "47%",
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Image(
                                        painterResource(R.drawable.fireicon),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Carbohydrates\n80g",
                                        fontSize = 17.sp
                                    )
                                }
                            }
                            Row( //Nutrition Facts Content (Protein)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 15.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Column(
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    NutritionBar(
                                        progress = 0.5f,
                                        progressColor = R.color.primary,
                                        modifier = Modifier
                                            .height(15.dp)
                                            .clip(RoundedCornerShape(20.dp)),
                                        backgroundColor = R.color.purple_200
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "23%",
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Image(
                                        painterResource(R.drawable.fireicon),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Protein\n43g",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            Row( //Nutrition Facts Content (Fats)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 15.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Column(
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    NutritionBar(
                                        progress = 0.5f,
                                        progressColor = R.color.primary,
                                        modifier = Modifier
                                            .height(15.dp)
                                            .clip(RoundedCornerShape(20.dp)),
                                        backgroundColor = R.color.purple_200
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "12%",
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Image(
                                        painterResource(R.drawable.fireicon),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Fats\n20g",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            Row( //Nutrition Facts Content (Salt)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 15.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Column(
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    NutritionBar(
                                        progress = 0.05f,
                                        progressColor = R.color.primary,
                                        modifier = Modifier
                                            .height(15.dp)
                                            .clip(RoundedCornerShape(20.dp)),
                                        backgroundColor = R.color.purple_200
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "3%",
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Image(
                                        painterResource(R.drawable.fireicon),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Salt\n4g",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            Row( //Nutrition Facts Content (Sugar)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 15.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Column(
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    NutritionBar(
                                        progress = 0.5f,
                                        progressColor = R.color.primary,
                                        modifier = Modifier
                                            .height(15.dp)
                                            .clip(RoundedCornerShape(20.dp)),
                                        backgroundColor = R.color.purple_200
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "23%",
                                        fontSize = 16.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier.width(35.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Image(
                                        painterResource(R.drawable.fireicon),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Sugar\n7g",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            Row( //Nutrition Facts Content (Sugar)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Text(
                                    text = "* Serving Size : 150g",
                                    fontSize = 17.sp

                                )

                            }
                            Row( //Nutrition Facts Content (Sugar)
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Button(
                                    modifier = Modifier.size(300.dp,50.dp),
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        colorResource(R.color.primary)
                                    ),
                                ){
                                    Text(
                                        text = "Order Now",
                                        fontSize = 20.sp,
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


@Composable
fun NutritionBar(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Int = R.color.primary,
    backgroundColor: Int = R.color.purple_200,
    clipShape: Shape = RoundedCornerShape(20.dp)
) {
    Box(
        modifier = modifier
            .clip(clipShape)
            .background(colorResource(backgroundColor))
            .height(15.dp)
            .width(150.dp)
    ) {
        Box(
            modifier = Modifier
                .background(colorResource(progressColor))
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    FoodDetailScreen()
}