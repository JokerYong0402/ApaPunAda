package com.example.apapunada.ui.staff

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.placeholder
import coil.compose.rememberImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.FoodCuisinesSample
import com.example.apapunada.data.MenuSample
import com.example.apapunada.data.OrderSample
import com.example.apapunada.model.Cuisine
import com.example.apapunada.model.Menu
import com.example.apapunada.model.Order
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.users.EditProfileAlertDialog
import com.example.apapunada.ui.users.EditTextFieldProfile

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffMenuScreen(
    menuType: String,
    dishCuisine: List<Cuisine> = FoodCuisinesSample.FoodCuisines,
    menus: List<Menu> = MenuSample.Menus
) {
    val headerList = listOf(
        // (Header name, Column width)
        Pair("  No.", 90.dp),
        Pair("Picture", 180.dp),
        Pair("Foodname", 250.dp),
        Pair("Cuisine",180.dp),
        Pair("Amount", 180.dp),
        Pair("Status", 160.dp),
        Pair("Action", 200.dp),

    )

    var openAddCuisineDialog by remember { mutableStateOf(false) }
    var openAddDishDialog by remember { mutableStateOf(false) }

    var search by remember { mutableStateOf("") }


    if (openAddCuisineDialog) {
        AddCuisineDialog(
            onDismissRequest = { openAddCuisineDialog = false },
            onConfirmation = { openAddCuisineDialog = false },
        )
    }

    if (openAddDishDialog) {
        AddDishDialog(
            onDismissRequest = { openAddDishDialog = false },
            onConfirmation = { openAddDishDialog = false },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ){
            Text(
                text = "Menu Management",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(end = 40.dp)
            )
            Image(
                painterResource(R.drawable.searchicon),
                contentDescription = "Gender Icon",
                modifier = Modifier
                    .padding(start = 25.dp)
                    .size(
                        width = 45.dp,
                        height = 45.dp
                    )
            )
            MenuSearchBar(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
            )
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    //.fillMaxWidth()
            ) {
                OutlinedButton(//add dish button
                    onClick = { openAddCuisineDialog = true },
//                    colors = ButtonDefaults.buttonColors(
//                        colorResource(R.color.primary)
//                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(75.dp)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .border(
                            2.dp,
                            color = colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        )
                ) {
                    Text(
                        text = "Add Cusine",
                        fontSize = 18.sp,
                        color = colorResource(R.color.primary)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(//add dish button
                    onClick = { openAddDishDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.primary)
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(75.dp)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Add Dish",
                        fontSize = 18.sp
                    )
                }
            }
        }


        //Row of Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ){

            CuisineButtonAll()

            dishCuisine.forEach{ cuisine ->
                ElevatedButton(
                    onClick = { /* Filter menus by this cuisine (if applicable) */ },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.white)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp)
                        .padding(horizontal = 15.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = cuisine.cuisineName, // Assuming cuisine has a name property
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_large))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
            ) {
                // header row
                stickyHeader {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                colorResource(R.color.primary_200),
                                RoundedCornerShape(10.dp)
                            )
                    ) {
                        headerList.forEach { header ->
                            Text(
                                text = header.first,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .width(header.second)
                            )
                        }
                    }
                }
                items(menus.size) { i ->
                    val menu = menus[i]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    ) {
                        Text(
                            text = menu.id.toString(),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[0].second)
                                .padding(start = 10.dp)
                        )
                        Image(
                            painter = painterResource(menu.image),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(12.dp)
                                .width(headerList[1].second)
                                .fillMaxSize()
                                .size(
                                    width = 50.dp,
                                    height = 50.dp
                                ),
                            //contentScale = ContentScale.Crop,
                            alignment = Alignment.CenterStart
                        )

                        Text(
                            text = menu.name,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[2].second)
                        )

                        Row {
                            Text(
                                text = menu.cuisine.cuisineName,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[3].second)
                                    //.padding(start = 70.dp)
                            )
                        }

                        Text(
                            text = "RM " + formattedString(menu.price),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[4].second)
                        )

                        Text(
                            text = menu.status,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[5].second)
                                .padding(end = 50.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .width(headerList[6].second)
                                .padding(end = 50.dp)
                        ) {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Create,
                                    contentDescription = "Edit button",
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "De button",
                                    modifier = Modifier.size(30.dp)
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
fun MenuSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(end = 20.dp)
            .background(color = Color.Transparent)
            .height(50.dp)
            .width(320.dp)
            .clip(
                shape = RoundedCornerShape(
                    size = 10.dp,
                ),
            )
            .border(
                BorderStroke(width = 2.dp, colorResource(id = R.color.black)),
                shape = RoundedCornerShape(
                    size = 10.dp,
                )
            )
        ,
        placeholder = {
            Text(
                text = "Search",
                fontSize = 14.sp,
                color = colorResource(id = R.color.black),
                modifier = modifier
                //.padding(bottom = 100.dp)
            ) },
    )
}

@Composable
fun CuisineButtonAll(){
    ElevatedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(170.dp)
            .height(55.dp)
            .padding(horizontal = 16.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = "All",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun AddCuisineDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val context = LocalContext.current
    var newcuisine by remember { mutableStateOf("") }
    val dialogTitle = "Add New Cuisine"

    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .width(350.dp)
                .height(160.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                    Text(text = dialogTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = newcuisine,
                        onValueChange = { newcuisine = it },
                        placeholder = { Text(text = "Cuisine Name") },

                        //label = { Text(text = "Cuisine") },
                        modifier = Modifier
                            .border(2.dp, colorResource(R.color.primary), shape = RoundedCornerShape(1.dp))
                    )
                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }

                    TextButton(onClick = {
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                        onConfirmation() }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}


@Composable
fun AddDishDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val context = LocalContext.current
    var newdishname by remember { mutableStateOf("") }
    var newdishdescription by remember { mutableStateOf("") }
    var newdishrating by remember { mutableStateOf("") }
    var newdishingredient by remember { mutableStateOf("") }
    var newdishcuisine by remember { mutableStateOf("") }
    var newdishprice by remember { mutableStateOf("") }
    var newdishstatus by remember { mutableStateOf("") }

    var isUploadImage by remember { mutableStateOf(false) }

    val dialogTitle = "ADD NEW DISH"



    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(text = dialogTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
//                Image(
//                    painter = painterResource(R.drawable.dishimage),
//                    contentDescription = "",
//                    alignment = Alignment.Center,
//                    modifier = Modifier
//                        .size(
//                            width = 100.dp,
//                            height = 100.dp
//                        )
//                        .clickable(
//                            onClick = { isUploadImage = true }
//                        )
//                        .padding(start = 30.dp)
//                )
                UploadDishImage()

                Text(
                    text = "Upload Image",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 12.dp)
                )

                OutlinedTextField(
                    value = newdishname,
                    onValueChange = { newdishname = it },
                    placeholder = { Text(text = "Dish Name") },
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                OutlinedTextField(
                    value = newdishdescription,
                    onValueChange = { newdishdescription = it },
                    placeholder = { Text(text = "Dish Description") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                OutlinedTextField(
                    value = newdishcuisine,
                    onValueChange = { newdishcuisine = it },
                    placeholder = { Text(text = "Dish Cuisine") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                OutlinedTextField(
                    value = newdishrating,
                    onValueChange = { newdishrating = it },
                    placeholder = { Text(text = "Dish Rating") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                OutlinedTextField(
                    value = newdishprice,
                    onValueChange = { newdishprice = it },
                    placeholder = { Text(text = "Dish Price") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                OutlinedTextField(
                    value = newdishstatus,
                    onValueChange = { newdishstatus = it },
                    placeholder = { Text(text = "Dish Status") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
                OutlinedTextField(
                    value = newdishingredient,
                    onValueChange = { newdishingredient = it },
                    placeholder = { Text(text = "Dish Ingredient") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(1.dp)
                        )
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Cancel")
                    }

                    TextButton(
                        onClick =
                        {
                        Toast.makeText(context, "Dish Added Successfully", Toast.LENGTH_SHORT).show()
                        onConfirmation()
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }

        }

    }
}


@Composable
fun UploadDishImage(){
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
        R.drawable.dishimage
        else
        imageUri.value
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }


    }

    Column(
        modifier = Modifier
    ){
        Card(shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ){
            Image(
                painter = painter,
                contentDescription = null,
                Modifier.clickable { launcher.launch("image/*") }
            )
        }
    }


}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun StaffMenuLandscapePreview(){
    StaffMenuScreen("Recommended")
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun StaffMenuScreenPhonePreview() {
    StaffMenuScreen("Recommended")
}

