package com.example.apapunada.ui.staff

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.Cuisine
import com.example.apapunada.viewmodel.MenuItemState
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.MenuListState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StaffMenuScreen(
//    menuType: String,
//    menus: List<Menu> = Menus
    viewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var menuItemState = viewModel.menuItemState.collectAsState(initial = MenuItemState())
    val menuListState = viewModel.menuListState.collectAsState(initial = MenuListState())
    var menus: List<MenuItem> = listOf()

    viewModel.loadAllMenuItem()

    if (menuListState.value.isLoading) {
        Box( modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            .clickable { /* no action */ }
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

    val dishCuisine = getEnumList(Cuisine::class.java)

    val headerList = listOf(
        // (Header name, Column width)
        Pair("  No.", 90.dp),
        Pair("Picture", 180.dp),
        Pair("Food Name", 250.dp),
        Pair("Cuisine",180.dp),
        Pair("Amount(RM)", 180.dp),
        Pair("Status", 160.dp),
        Pair("Action", 180.dp),

    )
    var openAddDishDialog by remember { mutableStateOf(false) }
    var openEditDishDialog by remember { mutableStateOf(false) }
    var openStatusDishDialog by remember { mutableStateOf(false) }
    var openDishDetailDialog by remember { mutableStateOf(false) }

    var search by remember { mutableStateOf("") }

    var currentMenu by remember { mutableStateOf(MenuItem()) }

    if (openAddDishDialog) {
        AddDishDialog(
            onDismissRequest = { openAddDishDialog = false },
            onConfirmation = { openAddDishDialog = false }
        )
    }

    if (openEditDishDialog) {
        EditDishDialog(
            onDismissRequest = { openEditDishDialog = false },
            onConfirmation = { openEditDishDialog = false },
            menu = currentMenu,
            )
    }

    if (openStatusDishDialog) {
        ChangeDishStatusDialog(
            onDismissRequest = { openStatusDishDialog = false },
            onConfirmation = { openStatusDishDialog = false },
            menu = currentMenu,
            )
    }


    if (openDishDetailDialog) {
        DishDetailDialog(
            onDismissRequest = { openDishDetailDialog = false },
            menu = currentMenu,
            labelList = headerList
            )
    }

    var expandedSearchCuisine by remember { mutableStateOf(false) }
    var selectedSearchCuisine by remember { mutableStateOf("") }
    val searchCuisine = listOf("Western","Japanese","Korean","Malaysian","Thai","Beverages")
    var searchCuisineDropdown by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){

            MenuSearchBar(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
            )

            ExposedDropdownMenuBox(
                expanded = expandedSearchCuisine,
                onExpandedChange = { expandedSearchCuisine = !expandedSearchCuisine },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                TextField(
                    value = searchCuisineDropdown,
                    onValueChange = { searchCuisineDropdown = it },
                    label = {
                        Text(
                            text = "Dish Cuisine",
                            fontSize = 12.sp,
                        ) },
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSearchCuisine)
                    },
                    modifier = Modifier
                        .size(width = 200.dp, height = 50.dp)
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedSearchCuisine,
                    onDismissRequest = { /*TODO*/ }
                ) {
                    searchCuisine.forEach { cuisine ->
                        DropdownMenuItem(
                            text = { Text(text = cuisine ) },
                            onClick = {
                                selectedSearchCuisine = cuisine
                                expandedSearchCuisine = false
                                searchCuisineDropdown = cuisine
                            }
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp)
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
                        text = cuisine, // Assuming cuisine has a name property
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
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()
                            .height(110.dp)
                    ) {
                        Text(
                            text = menu.menuItemID.toString(),
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[0].second)
                                .padding(start = 10.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.steakpic), // TODO
                            contentDescription = "",
                            modifier = Modifier
                                //.padding(12.dp)
                                .width(headerList[1].second)
                                .fillMaxSize()
                                .size(
                                    width = 50.dp,
                                    height = 50.dp
                                ),
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.CenterStart
                        )

                        Text(
                            text = menu.itemName,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .width(headerList[2].second)
                        )

                        Row {
                            Text(
                                text = menu.cuisine,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .width(headerList[3].second)
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
                                onClick = {
                                    currentMenu = getMenu(i, menus)
                                    openEditDishDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Create,
                                    contentDescription = "Edit button",
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    currentMenu = getMenu(i, menus)
                                    openStatusDishDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Settings,
                                    contentDescription = "Change Status button",
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    currentMenu = getMenu(i, menus)
                                    openDishDetailDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Email,
                                    contentDescription = "Detail button",
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
    var searchDish by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchDish,
        singleLine = true,
        onValueChange = { searchDish = it},
        leadingIcon = {
            Image(
            painterResource(R.drawable.searchicon),
            contentDescription = "Gender Icon",
            modifier = Modifier
                .padding(start = 10.dp)
                .size(
                    width = 35.dp,
                    height = 35.dp
                )
        )},
        placeholder = {
            Text(
                text = "Search",
                fontSize = 17.sp,
                color = colorResource(id = R.color.black),
                modifier = modifier
                //.padding(bottom = 100.dp)
            ) },
        modifier = Modifier
            .padding(end = 10.dp)
            .background(color = Color.Transparent)
            .height(50.dp)
            .width(300.dp)
//            .clip(
//                shape = RoundedCornerShape(
//                    size = 10.dp,
//                ),
//            )
//            .border(
//                BorderStroke(width = 2.dp, colorResource(id = R.color.black)),
//                shape = RoundedCornerShape(
//                    size = 10.dp,
//                )
//            )
        ,

    )
}


@Composable
fun UploadDishImage(){
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty { R.drawable.dishimage }
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
                .padding(10.dp)
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDishDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    val context = LocalContext.current
    var newDishName by remember { mutableStateOf("") }
    var newDishDescription by remember { mutableStateOf("") }
    var newDishRating by remember { mutableStateOf("") }
    var newDishIngredient by remember { mutableStateOf("") }
    var newDishCuisine by remember { mutableStateOf("") }
    var newDishPrice by remember { mutableStateOf("") }
    var newDishStatus by remember { mutableStateOf("") }
    var newDishServingSize by remember { mutableStateOf("") }

    var isUploadImage by remember { mutableStateOf(false) }
    val dialogTitle = "ADD NEW DISH"

    var expandedCuisine by remember { mutableStateOf(false) }
    var selectedCuisine by remember { mutableStateOf("") }
    val menuCuisine = listOf("Western","Japanese","Korean","Malaysian","Thai","Beverages")

    var expandedStatus by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("") }
    val menuStatus = listOf("Available","Unavailable")


    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                //.padding(horizontal = dimensionResource(R.dimen.padding_medium))
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
                UploadDishImage()
                Text(
                    text = "Upload Image",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                OutlinedTextField(//new dish name
                    value = newDishName,
                    onValueChange = { newDishName = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Name",
                        fontSize = 17.sp,
                        )},
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                OutlinedTextField(//new dish description
                    value = newDishDescription,
                    onValueChange = { newDishDescription = it },
                    singleLine = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Description",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
                ExposedDropdownMenuBox(//new dish cuisine
                    expanded = expandedCuisine,
                    onExpandedChange = { expandedCuisine = !expandedCuisine }
                ) {
                    OutlinedTextField(
                        value = newDishCuisine,
                        onValueChange = { newDishCuisine = it },
                        label = {
                            Text(
                            text = "Dish Cuisine",
                            fontSize = 17.sp,
                            ) },
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCuisine)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 65.dp)
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCuisine,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        menuCuisine.forEach { cuisine ->
                            DropdownMenuItem(
                                text = { Text(text = cuisine ) },
                                onClick = {
                                    selectedCuisine = cuisine
                                    expandedCuisine = false
                                    newDishCuisine = cuisine
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(//new dish rating
                    value = newDishRating,
                    onValueChange = { newDishRating = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Rating",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
                OutlinedTextField(//new dish price
                    value = newDishPrice,
                    onValueChange = { newDishPrice = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {Text(
                        text = "Dish Price",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                ExposedDropdownMenuBox(//new dish status
                    expanded = expandedStatus,
                    onExpandedChange = { expandedStatus = !expandedStatus }
                ) {
                    OutlinedTextField(
                        value = newDishStatus,
                        onValueChange = { newDishStatus = it },
                        label = {
                            Text(
                                text = "Dish Availability",
                                fontSize = 17.sp,
                            ) },
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 65.dp)
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStatus,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        menuStatus.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status ) },
                                onClick = {
                                    selectedStatus = status
                                    expandedStatus = false
                                    newDishStatus = status
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(// new dish ingredient
                    value = newDishIngredient,
                    onValueChange = { newDishIngredient = it },
                    singleLine = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Ingredient",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
                OutlinedTextField(//new dish serving size
                    value = newDishServingSize,
                    onValueChange = { newDishServingSize = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Serving Size",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        //.padding(vertical = 20.dp)
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDishDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    menu: MenuItem,
    ) {
    val context = LocalContext.current
    var editDishName by remember { mutableStateOf(menu.itemName) }
    var editDishDescription by remember { mutableStateOf(menu.description) }
    var editDishRating by remember { mutableStateOf(menu.rating.toString()) }
//    var editDishIngredient by remember { mutableStateOf(menu.ingredient) }
    var editDishCuisine by remember { mutableStateOf(menu.cuisine) }
    var editDishPrice by remember { mutableStateOf(menu.price.toString()) }
    var editDishStatus by remember { mutableStateOf(menu.status) }
    var editDishServingSize by remember { mutableStateOf("") }

    var isUploadImage by remember { mutableStateOf(false) }

    val dialogTitle = "EDIT DISH"

    var expandedEditCuisine by remember { mutableStateOf(false) }
    var selectedEditCuisine by remember { mutableStateOf("") }
    val menuCuisine = listOf("Western","Japanese","Korean","Malaysian","Thai","Beverages")

    var expandedEditStatus by remember { mutableStateOf(false) }
    var selectedEditStatus by remember { mutableStateOf("") }
    val menuStatus = listOf("Available","Unavailable")


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
                UploadDishImage()

                Text(
                    text = "Upload Image",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 12.dp)
                )

                OutlinedTextField(//edit dish name
                    value = editDishName,
                    onValueChange = { editDishName = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Name",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                OutlinedTextField(//edit dish description
                    value = editDishDescription,
                    onValueChange = { editDishDescription = it },
                    singleLine = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Description",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
                ExposedDropdownMenuBox(//edit dish cuisine
                    expanded = expandedEditCuisine,
                    onExpandedChange = { expandedEditCuisine = !expandedEditCuisine }
                ) {
                    OutlinedTextField(
                        value = editDishCuisine,
                        onValueChange = { editDishCuisine = it },
                        label = {
                            Text(
                                text = "Dish Cuisine",
                                fontSize = 17.sp,
                            ) },
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEditCuisine)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 65.dp)
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEditCuisine,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        menuCuisine.forEach { cuisine ->
                            DropdownMenuItem(
                                text = { Text(text = cuisine ) },
                                onClick = {
                                    selectedEditCuisine = cuisine
                                    expandedEditCuisine = false
                                    editDishCuisine = cuisine
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(//edit dish rating
                    value = editDishRating,
                    onValueChange = { editDishRating = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {Text(
                        text = "Dish Rating",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
                OutlinedTextField(//edit dish price
                    value = editDishPrice,
                    onValueChange = { editDishPrice = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {Text(
                        text = "Dish Price",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                ExposedDropdownMenuBox(//edit dish status
                    expanded = expandedEditStatus,
                    onExpandedChange = { expandedEditStatus = !expandedEditStatus }
                ) {
                    OutlinedTextField(
                        value = editDishStatus,
                        onValueChange = { editDishStatus = it },
                        label = {
                            Text(
                                text = "Dish Availability",
                                fontSize = 17.sp,
                            ) },
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEditStatus)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 65.dp)
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEditStatus,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        menuStatus.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status ) },
                                onClick = {
                                    selectedEditStatus = status
                                    expandedEditStatus = false
                                    editDishStatus = status
                                }
                            )
                        }
                    }
                }
//                OutlinedTextField(//edit dish ingredient
//                    value = editDishIngredient,
//                    onValueChange = { editDishIngredient = it },
//                    singleLine = false,
//                    textStyle = TextStyle(
//                        fontSize = 18.sp,
//                        color = Color.Black
//                    ),
//                    label = {Text(
//                        text = "Dish Ingredient",
//                        fontSize = 17.sp,
//                    )},
//                    modifier = Modifier
//                        .padding(vertical = 20.dp)
//                )
                OutlinedTextField(//edit dish serving size
                    value = editDishServingSize,
                    onValueChange = { editDishServingSize = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {Text(
                        text = "Dish Serving",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Cancel")
                    }

                    TextButton(
                        onClick =
                        {
                            Toast.makeText(context, "Dish Edited Successfully", Toast.LENGTH_SHORT).show()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeDishStatusDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    menu: MenuItem
){

    val context = LocalContext.current
    var expandedChangeStatus by remember { mutableStateOf(false) }
    var selectedChangeStatus by remember { mutableStateOf(menu.status) }
    val changeStatusOptions = listOf("Available","Unavailable")
    var changeStatus by remember { mutableStateOf("") }

    val dialogTitle = "Change Status of Dish"
    Dialog(onDismissRequest = { onDismissRequest() })
    {
        Card(
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .width(400.dp)
                .height(200.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(text = dialogTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expandedChangeStatus,
                    onExpandedChange = { expandedChangeStatus = !expandedChangeStatus }
                ) {
                    OutlinedTextField(
                        value = selectedChangeStatus,
                        onValueChange = { selectedChangeStatus = it },
                        label = {
                            Text(
                                text = "Dish Availability",
                                fontSize = 17.sp,
                            ) },
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedChangeStatus)
                        },
                        modifier = Modifier
                            .size(width = 280.dp, height = 65.dp)
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedChangeStatus,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        changeStatusOptions.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status ) },
                                onClick = {
                                    selectedChangeStatus = status
                                    expandedChangeStatus = false
                                    changeStatus = status
                                }
                            )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth().padding(end = 15.dp)
            ) {
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = "Cancel")
                }

                TextButton(
                    onClick =
                    {
                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show()
                        onConfirmation()
                    }
                ) {
                    Text(text = "Confirm")
                }
            }

        }


    }

}

@Composable
fun DishDetailDialog(
    onDismissRequest: () -> Unit = {},
    menu: MenuItem,
    labelList: List<Pair<String, Dp>>

) {
    val image = menu.image
    val name = menu.itemName
    val price = menu.price
    val rating = menu.rating
    val description = menu.description
    val status = menu.status
//    val ingredient = menu.ingredient
    val cuisine = menu.cuisine
//    val nutrition = menu.nutrition

    val userHeader = listOf(
        // (Header name, Column width)
        Pair("Picture", 100.dp),
        Pair("Dish Name", 100.dp),
        Pair("Description", 100.dp),
        Pair("Cuisine", 100.dp),
        Pair("Price", 100.dp),
        Pair("Rating", 100.dp),
        Pair("Status", 100.dp),
        Pair("Ingredient", 100.dp),
        Pair("Nutrition", 100.dp)
    )

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Row(//Title
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Detail of $name",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(//Image
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.cabonarapastapic),
                            contentDescription = "Dish Image",
                            modifier = Modifier
                                .size(120.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .width(250.dp)
                    ) {

                        Column {
                            Text(//name
                                text = userHeader[1].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = name,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(//description
                                text = userHeader[2].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = description,
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(//cuisine
                                text = userHeader[3].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = cuisine,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier.width(200.dp)
                    ) {
                        Column {
                            Text(//price
                                text = userHeader[4].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = "RM $price",
                                fontSize = 20.sp
                            )
                        }

                        Column {
                            Text(//rating
                                text = userHeader[5].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = rating.toString(),
                                fontSize = 20.sp
                            )
                        }


                        Column {
                            Text(//status
                                text = userHeader[6].first,
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp
                            )
                            Text(
                                text = status,
                                fontSize = 20.sp,
                                color = Color.Green
                            )
                        }
//                        Column {
//                            Text(//ingredient
//                                text = userHeader[7].first,
//                                fontWeight = FontWeight.Medium,
//                                fontSize = 22.sp
//                            )
//                            Text(
//                                text = ingredient,
//                                fontSize = 20.sp
//                            )
//                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Column {
                        Text(//ingredient
                            text = userHeader[7].first,
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp
                        )
                        Text(
//                            text = ingredient,  TODO
                            text = "Ingredient...",
                            fontSize = 20.sp
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Column {
                        Text(//ingredient
                            text = userHeader[8].first,
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp
                        )
                        Text(
//                            text = nutrition, TODO
                            text = "Nutrition...",
                            fontSize = 20.sp
                        )
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Dismiss")
                    }
                }
            }
        }
    }
}

private fun getMenu(
    index: Int,
    menus: List<MenuItem>
): MenuItem {
    return menus[index] // TODO
}


@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun StaffMenuLandscapePreview(){
    StaffMenuScreen()
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun StaffMenuScreenPhonePreview() {
    StaffMenuScreen()
}

