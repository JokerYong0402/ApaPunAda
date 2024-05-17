package com.example.apapunada.ui.users

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.viewmodel.MenuItemState
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.MenuListState

@Composable
fun MenuScreen(
    viewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onMenuTypeClick: (String) -> Unit,
    onDish: (Int) -> Unit,
    ) {
    var menuItemState = viewModel.menuItemState.collectAsState(initial = MenuItemState())
    val menuListState = viewModel.menuListState.collectAsState(initial = MenuListState())
    var menus: List<MenuItem> = listOf()

    var currentDishId by remember { mutableIntStateOf(0) }
    //var currentMenu by remember { mutableStateOf(MenuItem()) }

    //viewModel.loadMenuItemByMenuItemId(currentDishId)

    viewModel.loadAllMenuItem()

    var textInput by remember { mutableStateOf("") }

    if (menuListState.value.isLoading) {
        Box( modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable { /* no action */ }
            .zIndex(2f)
            ,
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularIndicator()
        }
    } else {
        if (menuListState.value.errorMessage.isNotEmpty()) {
            Text(text = "Error loading menus: ${menuListState.value.errorMessage}")
            Log.i("Menu", "StaffMenuScreen: ${menuListState.value.errorMessage}")
        } else {
            menus = menuListState.value.menuItemList
        }
    }

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        //.height(60.dp)
                        .padding(horizontal = 10.dp, vertical = 8.dp),
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
                        onClick = { onMenuTypeClick("Western") },
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
                        onClick = { onMenuTypeClick("Korean") },
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
                        onClick = { onMenuTypeClick("Malaysian") },
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
                        onClick = { onMenuTypeClick("Japanese") },
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
                        onClick = { onMenuTypeClick("Thai") },
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
                        onClick = { onMenuTypeClick("Beverage") },
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
                        onClick = {onMenuTypeClick("Popular")},
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
                        if (menu.rating >= 4.5) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                modifier = Modifier
                                    .clickable {
                                        currentDishId = menu.menuItemID
                                        onDish(currentDishId)
                                    }
                                    .size(
                                        180.dp,
                                        190.dp
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
                                        painter = painterResource(R.drawable.staricon),
                                        contentDescription = "Beef Burger",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                    Text(
                                        text = menu.itemName,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start
                                    )
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
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
//                    TextButton(
//                        onClick = {},
//                        modifier = Modifier
//                            .fillMaxSize(),
//                    ) {
//                        Text(
//                            text = "View all--",
//                            color = colorResource(R.color.primary),
//                            fontSize = 15.sp,
//                            textAlign = TextAlign.End
//
//                        )
//
//                    }
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
                        if (menu.rating >= 4.5) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                modifier = Modifier
                                    .clickable {
                                        currentDishId = menu.menuItemID
                                        onDish(currentDishId)
                                    }
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
                                        painter = painterResource(R.drawable.staricon),
                                        contentDescription = "Beef Burger",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            //.fillMaxSize()
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                    Text(
                                        text = menu.itemName,
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
                        onClick = {onMenuTypeClick("All")},
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
                                    .clickable {
                                        currentDishId = menu.menuItemID
                                        onDish(currentDishId) }
                                    .size(
                                        180.dp,
                                        190.dp
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
                                        painter = painterResource(R.drawable.staricon),
                                        contentDescription = menu.itemName,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(20.dp, 20.dp))
                                    )
                                    Text(
                                        text = menu.itemName,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start
                                    )
                                    Row(
                                        //modifier = Modifier
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.staricon),
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
            .padding(start = 5.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
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
    )
}

private fun getMenu(
    index: Int,
    menus: List<MenuItem>
): MenuItem {
    return menus[index] // TODO
}

//@Preview(showBackground = true)
//@Composable
//fun MenuScreenPreview() {
//    MenuScreen(
//
//        onDish = {},
//        )
//}