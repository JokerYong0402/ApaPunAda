package com.example.apapunada.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DisplayImagesFromByteArray
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.viewmodel.FoodDetailsState
import com.example.apapunada.viewmodel.MenuItemState
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.NutritionFactsState

@Composable
fun FoodDetailScreen(
    viewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onOrder: () -> Unit,
    onBackButtonClicked: () -> Unit,
    currentDishId: Int
) {
    val menuItemState = viewModel.menuItemState.collectAsState(initial = MenuItemState())
    viewModel.loadMenuItemByMenuItemId(currentDishId)
    val menu = menuItemState.value.menuItem

    //serving size
    val foodDetailState = viewModel.foodDetailsState.collectAsState(initial = FoodDetailsState())
    viewModel.loadFoodDetailsByMenuItemId(currentDishId)
    val foodDetails = foodDetailState.value.foodDetails

    //nutrition facts
    val nutritionFactsState = viewModel.nutritionFactsState.collectAsState(initial = NutritionFactsState())
    viewModel.loadNutritionFactsByFoodDetailsId(foodDetails.foodDetailsID)
    val nutritionFacts = nutritionFactsState.value.nutritionFacts

    var servingSizeC by remember { mutableDoubleStateOf(foodDetails.servingSize) }
    var carbohydratesC by remember { mutableDoubleStateOf(nutritionFacts.carbohydrates) }
    var proteinC by remember { mutableDoubleStateOf(nutritionFacts.carbohydrates) }
    var fatC by remember { mutableDoubleStateOf(nutritionFacts.carbohydrates) }
    var saltC by remember { mutableDoubleStateOf(nutritionFacts.carbohydrates) }
    var sugarC by remember { mutableDoubleStateOf(nutritionFacts.carbohydrates) }

    var carbohydratespercentageC by remember { mutableStateOf("") }
    var proteinpercentageC by remember { mutableStateOf("") }
    var fatpercentageC by remember { mutableStateOf("") }
    var saltpercentageC by remember { mutableStateOf("") }
    var sugarpercentageC by remember { mutableStateOf("") }

    servingSizeC = foodDetails.servingSize

    carbohydratesC = nutritionFacts.carbohydrates
    proteinC = nutritionFacts.proteins
    fatC = nutritionFacts.fats
    saltC = nutritionFacts.salt
    sugarC = nutritionFacts.sugar


    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.menu), onBackButtonClicked = onBackButtonClicked) },
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
                    DisplayImagesFromByteArray(
                        byteArray = menu.image,
                        modifier = Modifier
                            .width(400.dp)
                            .height(400.dp),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxSize()
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
                                    text = menu.itemName,
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
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
                                    Text(//price
                                        text = "RM" + formattedString(menu.price),
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
                                        text = "1", /*TODO add a serving person into database*/
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
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ){
                                Text(
                                    text = menu.description,
                                    fontSize = 18.sp,
                                )
                            }
                            HorizontalDivider(
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
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                            ){
                                Text(
                                    text = foodDetails.ingredient,
                                    fontSize = 18.sp,
                                    lineHeight = 30.sp
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .width(330.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 30.dp, bottom = 10.dp)
                            )
                            Row(//Nutrition Facts Heading
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 25.dp),
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
                                    carbohydratespercentageC = if (servingSizeC > 0) {
                                        val percentageDoublecarbohydrates = (carbohydratesC / servingSizeC) * 100
                                        val decimalPart = percentageDoublecarbohydrates % 1.0 // Get the decimal part using modulo
                                        if (decimalPart == 0.0) {
                                            percentageDoublecarbohydrates.toInt().toString()
                                        } else {
                                            String.format("%.0f", percentageDoublecarbohydrates) // Round up to nearest integer and format as string
                                        }
                                    } else {
                                        // Handle the case where serving size is unavailable (e.g., show "—" or informative message)
                                        "-"
                                    }
                                    Text(
                                        text = "$carbohydratespercentageC%",

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
                                        text = "Carbohydrates\n" + nutritionFacts.carbohydrates + "g",
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
                                        progress = 0.2f,
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
                                    proteinpercentageC = if (servingSizeC > 0) {
                                        val percentageDouble = (proteinC / servingSizeC) * 100
                                        val decimalPart = percentageDouble % 1.0 // Get the decimal part using modulo
                                        if (decimalPart == 0.0) {
                                            percentageDouble.toInt().toString()
                                        } else {
                                            String.format("%.0f", percentageDouble) // Round up to nearest integer and format as string
                                        }
                                    } else {
                                        // Handle the case where serving size is unavailable (e.g., show "—" or informative message)
                                        "-"
                                    }
                                    Text(
                                        text = "$proteinpercentageC%",
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
                                        text = "Protein\n" + nutritionFacts.proteins + "g",
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
                                        progress = 0.2f,
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
                                    fatpercentageC = if (servingSizeC > 0) {
                                        val percentageDouble = (fatC / servingSizeC) * 100
                                        val decimalPart = percentageDouble % 1.0 // Get the decimal part using modulo
                                        if (decimalPart == 0.0) {
                                            percentageDouble.toInt().toString()
                                        } else {
                                            String.format("%.0f", percentageDouble) // Round up to nearest integer and format as string
                                        }
                                    } else {
                                        // Handle the case where serving size is unavailable (e.g., show "—" or informative message)
                                        "-"
                                    }
                                    Text(
                                        text = "$fatpercentageC%",
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
                                        text = "Fats\n" + nutritionFacts.fats + "g",
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
                                        progress = 0.02f,
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
                                    saltpercentageC = if (servingSizeC > 0) {
                                        val percentageDouble = (saltC / servingSizeC) * 100
                                        val decimalPart = percentageDouble % 1.0 // Get the decimal part using modulo
                                        if (decimalPart == 0.0) {
                                            percentageDouble.toInt().toString()
                                        } else {
                                            String.format("%.0f", percentageDouble) // Round up to nearest integer and format as string
                                        }
                                    } else {
                                        // Handle the case where serving size is unavailable (e.g., show "—" or informative message)
                                        "-"
                                    }
                                    Text(
                                        text = "$saltpercentageC%",
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
                                        text = "Salt\n" + nutritionFacts.salt + "g",
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
                                        progress = 0.1f,
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
                                    sugarpercentageC = if (servingSizeC > 0) {
                                        val percentageDouble = (sugarC / servingSizeC) * 100
                                        val decimalPart = percentageDouble % 1.0 // Get the decimal part using modulo
                                        if (decimalPart == 0.0) {
                                            percentageDouble.toInt().toString()
                                        } else {
                                            String.format("%.0f", percentageDouble) // Round up to nearest integer and format as string
                                        }
                                    } else {
                                        // Handle the case where serving size is unavailable (e.g., show "—" or informative message)
                                        "-"
                                    }
                                    Text(
                                        text = "$sugarpercentageC%",
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
                                        text = "Sugar\n" + nutritionFacts.sugar + "g",
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
                                    text = "* Serving Size : " + foodDetails.servingSize.toInt() + "g",
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
                                    onClick = { onOrder() },
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

//@Preview(showBackground = true)
//@Composable
//fun FoodDetailScreenPreview() {
//    FoodDetailScreen(
//        onOrder = {}
//
//    )
//}