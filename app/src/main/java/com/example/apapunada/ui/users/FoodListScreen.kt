package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DisplayImagesFromByteArray
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.viewmodel.MenuItemViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FoodListScreen(
    menuType: String,
    onDish: (Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    viewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var menus by remember { mutableStateOf(listOf(MenuItem())) }

    var currentDishId by remember { mutableIntStateOf(0) }


    viewModel.loadAllMenuItem()

    var isLoading by remember { mutableStateOf(true) }
    if (isLoading) {
        IndeterminateCircularIndicator("Loading...")
    }

    LaunchedEffect(Unit){
        launch {
            delay(2000)
            isLoading = false

            menus = viewModel.menuListState.value.menuItemList
        }
    }

    val filteredFood = if (menuType != "Recommended" && menuType != "Popular" && menuType != "All") {
        menus.filter { it.cuisine == menuType }
    } else if(menuType == "Popular"){
        menus.filter { it.rating >= 4.5 }
    } else{
        menus
    }

    Scaffold(
        topBar = { MyTopTitleBar(title = menuType, onBackButtonClicked = onBackButtonClicked) },
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
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
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .clickable {
                                                currentDishId = filteredFood[i].menuItemID
                                                onDish(currentDishId) }
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
                                            DisplayImagesFromByteArray(
                                                byteArray = filteredFood[i].image,
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(20.dp, 20.dp))
                                                    .fillMaxWidth()
                                                    .height(120.dp),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop
                                            )
//                                            Image(
//                                                painter = painterResource(R.drawable.dishimage),
//                                                contentDescription = filteredFood[i].description,
//                                                contentScale = ContentScale.Crop,
//                                                modifier = Modifier
//                                                    .height(120.dp)
//                                                    .fillMaxWidth()
//                                                    .clip(RoundedCornerShape(20.dp, 20.dp))
//                                            )
                                            Text(
                                                text = filteredFood[i].itemName,
                                                fontSize = 17.sp,
                                                textAlign = TextAlign.Start
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.staricon),
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .height(25.dp)
                                                        .width(25.dp)
                                                )
                                                Text(
                                                    text = (filteredFood[i].rating).toString(),
                                                    fontSize = 14.sp,
                                                    textAlign = TextAlign.Start
                                                )


                                            }


                                        }

                                }

                                if (i < filteredFood.count() - 1) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .clickable {
                                                currentDishId = filteredFood[i + 1].menuItemID
                                                onDish(currentDishId) }
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
                                            DisplayImagesFromByteArray(
                                                byteArray = filteredFood[i + 1].image,
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(20.dp, 20.dp))
                                                    .fillMaxWidth()
                                                    .height(120.dp),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop
                                            )

                                            Text(
                                                text = filteredFood[i + 1].itemName,
                                                fontSize = 17.sp,
                                                textAlign = TextAlign.Start
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.staricon),
                                                    contentDescription = "Beef Burger",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .height(25.dp)
                                                        .width(25.dp)
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



//@Preview(showBackground = true)
//@Composable
//fun FoodListScreenPreview() {
//    FoodListScreen("Japanese", onDish = {})
//}