package com.example.apapunada.ui

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
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
import com.example.apapunada.data.MenuSample
import com.example.apapunada.model.Menu
import com.example.apapunada.ui.components.MyBottomNavBar
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun MenuScreen( menus: List<Menu> = MenuSample.Menus) {

    var textInput by remember { mutableStateOf("") }

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
                // content of page add here
                /*Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 5.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Apa Pun Ada Menu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }*/
                Row(
                    modifier = Modifier
                        .fillMaxWidth().fillMaxHeight()
                        //.height(60.dp)
                        .padding(horizontal = 10.dp, vertical  = 8.dp),
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
                    MenuScreenSearchBar(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 5.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(R.color.white)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(55.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Western",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(R.color.white)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(55.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Korean",
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(R.color.white)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(55.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Malaysian",
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 5.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(R.color.white)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(55.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Japanese",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(R.color.white)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(55.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Thai",
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(R.color.white)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(55.dp)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Beverage",
                            fontSize = 13.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                Row( //Popular dish wording
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                    ) {
                    Text(
                        text = "Popular Dishes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,

                        )
                    Spacer(modifier = Modifier.width(130.dp))
                    TextButton(
                        onClick = {},
                        modifier = Modifier.fillMaxSize()

                    ) {
                        Text(
                            text = "View all--",
                            color = colorResource(R.color.primary),
                            fontSize = 15.sp,
                            textAlign = TextAlign.End

                        )

                    }
                }

                Row( //Popular dishes pic
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        //.padding(vertical = 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                    ) {
                    menus.forEach { menu ->
                        if (menu.rating >= 3.0) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                modifier = Modifier
                                    .clickable { /*TODO*/ }
                                    .size(
                                        160.dp,
                                        180.dp
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
                                        painter = painterResource(menu.image),
                                        contentDescription = "Beef Burger",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            //.fillMaxSize()
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                    Text(
                                        text = menu.name,
                                        fontSize = 17.sp,
                                        textAlign = TextAlign.Start
                                    )
                                    Row(
                                        //modifier = Modifier
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.fireicon),
                                            contentDescription = "Beef Burger",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(25.dp)
                                                .width(25.dp)
                                            //.clip(RoundedCornerShape(16.dp, 16.dp))
                                        )
                                        Text(
                                            text = (menu.rating).toString(),
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start
                                        )


                                    }


                                }

                            }
                        }
                    }
                }

                Row( //Recommended dish wording
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                    ) {
                    Text(
                        text = "Recommended",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,

                        )
                    Spacer(modifier = Modifier.width(130.dp))
                    TextButton(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Text(
                            text = "View all--",
                            color = colorResource(R.color.primary),
                            fontSize = 15.sp,
                            textAlign = TextAlign.End

                        )

                    }
                }

                Row( //Recommended dishes pic
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        //.padding(vertical = 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                    ) {
                    menus.forEach { menu ->
                        if (menu.rating >= 3.0) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                modifier = Modifier
                                    .clickable{/*TODO*/}
                                    .size(
                                        250.dp,
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
                                        painter = painterResource(menu.image),
                                        contentDescription = "Beef Burger",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            //.fillMaxSize()
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                    Text(
                                        text = menu.name,
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
                                            text = (menu.rating).toString(),
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start
                                        )


                                    }


                                }

                            }
                        }
                    }
                }
                Row( //ALL dish wording
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                    ) {
                    Text(
                        text = "All",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,

                        )
                    Spacer(modifier = Modifier.width(240.dp))
                    TextButton(
                        onClick = {},
                        modifier = Modifier.fillMaxSize()

                    ) {
                        Text(
                            text = "View all--",
                            color = colorResource(R.color.primary),
                            fontSize = 15.sp,
                            textAlign = TextAlign.End

                        )

                    }
                }
                Row( //All dishes pic
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        //.padding(vertical = 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,

                    ) {
                    menus.forEach { menu ->
                        if (menu.rating != 0.00) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                modifier = Modifier
                                    .clickable { /*TODO*/ }
                                    .size(
                                        160.dp,
                                        180.dp
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
                                        painter = painterResource(menu.image),
                                        contentDescription = menu.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                    Text(
                                        text = menu.name,
                                        fontSize = 17.sp,
                                        textAlign = TextAlign.Start
                                    )
                                    Row(
                                        //modifier = Modifier
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.fireicon),
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(25.dp)
                                                .width(25.dp)
                                            //.clip(RoundedCornerShape(16.dp, 16.dp))
                                        )
                                        Text(
                                            text = (menu.rating).toString(),
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



@Composable
fun MenuScreenSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .padding(start = 5.dp, end = 20.dp)
            //.fillMaxSize()
            .height(60.dp)
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
                fontSize = 10.sp,
                color = colorResource(id = R.color.black),
                modifier = modifier
                    .fillMaxSize()
                    //.height(20.dp)
                //.padding(bottom = 100.dp)
            )
        },
        //Design for the text that user type in
        /*textStyle = TextStyle(
            fontSize = 12.sp,
            color = colorResource(id = R.color.black)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)*/
    )
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen(MenuSample.Menus)
}