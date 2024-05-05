package com.example.apapunada.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.data.FoodCuisinesSample
import com.example.apapunada.data.MenuSample
import com.example.apapunada.data.MenuSample.Menus
import com.example.apapunada.model.Cuisine
import com.example.apapunada.model.Menu
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun FoodListScreen(
    menuType: String,
    menus: List<Menu> = Menus
) {
    var textInput by remember { mutableStateOf("") }

    var filteredFood: List<Menu> = listOf()

    if (menuType != "Recommended" && menuType != "Popular") {
        menus.forEach { menu ->
            if (menu.cuisine.cuisineName == menuType) {
                filteredFood = filteredFood + menu
            }
        }
    } else {
        filteredFood = menus
    }

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.menu)) },
        //bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(40.dp)
                        .padding(horizontal = 10.dp, vertical  = 10.dp),
                    ){
                    Text(
                        text = menuType,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                    .padding(horizontal = 10.dp, vertical  = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Image(
                        painter = painterResource(R.drawable.searchicon),
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .padding(start = 15.dp)
                            //.fillMaxSize()
                            .size(
                                width = 30.dp,
                                height = 30.dp
                            ),
                        alignment = Alignment.Center
                    )
                    FoodListSearchBar(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            //.height(60.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        //.fillMaxSize()
                        .width(400.dp)
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {

                    if (filteredFood.isNotEmpty()) {
                        for (i in 0..<filteredFood.count() step 2) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_small))
                            ) {
//                                if ( i % 2 == 0 && i < filteredFood.count() - 2) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .clickable { /*TODO*/ }
                                            .size(
                                                170.dp,
                                                200.dp
                                            )
                                            .padding(horizontal = 8.dp)
                                            .shadow(
                                                elevation = 15.dp,
                                            )
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .padding(dimensionResource(R.dimen.padding_small))
                                                .fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(filteredFood[i].image),
                                                contentDescription = filteredFood[i].description,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .height(120.dp)
                                                    .fillMaxWidth()
                                                    //.fillMaxSize()
                                                    .clip(RoundedCornerShape(20.dp, 20.dp))
                                            )
                                            Text(
                                                text = filteredFood[i].name,
                                                fontSize = 17.sp,
                                                textAlign = TextAlign.Start
                                            )
                                            Row(
                                                //modifier = Modifier
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.staricon),
                                                    contentDescription = "Beef Burger",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .height(25.dp)
                                                        .width(25.dp)
                                                    //.clip(RoundedCornerShape(16.dp, 16.dp))
                                                )
                                                Text(
                                                    text = (filteredFood[i].rating).toString(),
                                                    fontSize = 14.sp,
                                                    textAlign = TextAlign.Start
                                                )


                                            }


                                        }

//                                    }
                                }

                                if (i < filteredFood.count() - 1) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .clickable { /*TODO*/ }
                                            .size(
                                                170.dp,
                                                200.dp
                                            )
                                            .padding(horizontal = 8.dp)
                                            .shadow(
                                                elevation = 15.dp,
                                            )
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .padding(dimensionResource(R.dimen.padding_small))
                                                .fillMaxSize()
                                        ) {
                                            Image(
                                                painter = painterResource(filteredFood[i + 1].image),
                                                contentDescription = "Beef Burger",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .height(120.dp)
                                                    .fillMaxWidth()
                                                    //.fillMaxSize()
                                                    .clip(RoundedCornerShape(20.dp, 20.dp))
                                            )
                                            Text(
                                                text = filteredFood[i + 1].name,
                                                fontSize = 17.sp,
                                                textAlign = TextAlign.Start
                                            )
                                            Row(
                                                //modifier = Modifier
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.staricon),
                                                    contentDescription = "Beef Burger",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .height(25.dp)
                                                        .width(25.dp)
                                                    //.clip(RoundedCornerShape(16.dp, 16.dp))
                                                )
                                                Text(
                                                    text = (filteredFood[i + 1].rating).toString(),
                                                    fontSize = 14.sp,
                                                    textAlign = TextAlign.Start
                                                )
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
    }
}


@Composable
fun FoodListSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .padding(start = 5.dp,top = 10.dp, bottom = 10.dp, end = 20.dp)
            //.fillMaxWidth()
            .height(50.dp)
            .clip(
                shape = RoundedCornerShape(
                    size = 20.dp,
                    ),
            )
            .background(color = Color.White)
            .border(
                BorderStroke(width = 1.dp, colorResource(R.color.primary)),
                shape = RoundedCornerShape(
                    size = 20.dp,
                )
            ),
        shape = RoundedCornerShape(20.dp),
        placeholder = {
            Text(
                text = "Search",
                fontSize = 14.sp,
                color = colorResource(id = R.color.black),
                modifier = modifier
                    .fillMaxSize()
            )
        },
        //Design for the text that user type in
        //textStyle = TextStyle(
        //    fontSize = 12.sp,
        //    color = colorResource(id = R.color.white)
        //),
        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}


@Preview(showBackground = true)
@Composable
fun FoodListScreenPreview() {
    FoodListScreen("Thai")
}