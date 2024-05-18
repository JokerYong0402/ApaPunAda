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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.FoodDetails
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.data.dataclass.NutritionFacts
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.IndeterminateCircularIndicator
import com.example.apapunada.ui.components.SearchBar
import com.example.apapunada.ui.components.formattedString
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.viewmodel.Cuisine
import com.example.apapunada.viewmodel.FoodDetailsState
import com.example.apapunada.viewmodel.MenuItemState
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.MenuListState
import com.example.apapunada.viewmodel.NutritionFactsState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaffMenuScreen(
    viewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val menuListState = viewModel.menuListState.collectAsState(initial = MenuListState())
    var menus: List<MenuItem> = listOf()
    var menuType by remember { mutableStateOf("All") }

    if (menuListState.value.isLoading) {
        IndeterminateCircularIndicator("Loading menu...")
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
        Pair("Cuisine", 180.dp),
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
    val addMenu by remember { mutableStateOf(MenuItem()) }

    var nutritionFacts = NutritionFacts()
    var foodDetails = FoodDetails()
    var currentMenuItemId by remember { mutableIntStateOf(0) }

    if (openAddDishDialog) {
        AddDishDialog(
            onDismissRequest = { openAddDishDialog = false },
            onConfirmation = { newMenuItem, newFoodDetails, newNutritionFacts ->
                viewModel.updateMenuItemState(newMenuItem)
                viewModel.updateFoodDetailsState(newFoodDetails)
                viewModel.updateNutritionFactsState(newNutritionFacts)

                viewModel.saveMenuItem()
                viewModel.saveFoodDetails()
                viewModel.saveNutritionFacts()

                openAddDishDialog = false
            },
            menu = addMenu,
            foodDetails = foodDetails,
            nutritionFacts = nutritionFacts
        )
    }

    if (openEditDishDialog) {
        viewModel.loadFoodDetailsByMenuItemId(currentMenuItemId)
        val foodDetailsState =
            viewModel.foodDetailsState.collectAsState(initial = FoodDetailsState())
        foodDetails = foodDetailsState.value.foodDetails

        viewModel.loadNutritionFactsByFoodDetailsId(foodDetails.foodDetailsID)
        val nutritionFactsState =
            viewModel.nutritionFactsState.collectAsState(initial = NutritionFactsState())
        nutritionFacts = nutritionFactsState.value.nutritionFacts
        EditDishDialog(
            onDismissRequest = { openEditDishDialog = false },
            onConfirmation = { updatedMenuItem, updatedMoodDetails, updatedNutritionFacts ->
                viewModel.updateMenuItemState(updatedMenuItem)
                viewModel.updateFoodDetailsState(updatedMoodDetails)
                viewModel.updateNutritionFactsState(updatedNutritionFacts)

                viewModel.updateMenuItem()
                viewModel.updateFoodDetails()
                viewModel.updateNutritionFacts()
                openEditDishDialog = false
            },
            menu = currentMenu,
            foodDetails = foodDetails,
            nutritionFacts = nutritionFacts
        )
    }

    if (openStatusDishDialog) {
        viewModel.loadMenuItemByMenuItemId(currentMenuItemId)
        val menuItemsState = viewModel.menuItemState.collectAsState(initial = MenuItemState())

        ChangeDishStatusDialog(
            menu = currentMenu,

            onDismissRequest = { openStatusDishDialog = false },
            onConfirmation = { changedMenuItemStatus ->
                viewModel.updateMenuItemState(changedMenuItemStatus)

                viewModel.updateMenuItem()
                openStatusDishDialog = false
            },

            )
    }


    if (openDishDetailDialog) {
        viewModel.loadFoodDetailsByMenuItemId(currentMenuItemId)
        val foodDetailsState =
            viewModel.foodDetailsState.collectAsState(initial = FoodDetailsState())
        foodDetails = foodDetailsState.value.foodDetails

        viewModel.loadNutritionFactsByFoodDetailsId(foodDetails.foodDetailsID)
        val nutritionFactsState =
            viewModel.nutritionFactsState.collectAsState(initial = NutritionFactsState())
        nutritionFacts = nutritionFactsState.value.nutritionFacts

        DishDetailDialog(
            onDismissRequest = { openDishDetailDialog = false },
            menu = currentMenu,
            foodDetails = foodDetails,
            nutritionFacts = nutritionFacts,
            labelList = headerList
        )
    }

    var launchAll by remember { mutableStateOf(false) }
    var launchMenuItem by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }

    if(isSearching){
        if (launchMenuItem) {
            viewModel.loadMenuItemByName(search)
            launchMenuItem = false
        }
    }
    else {
        if (menuType == "All") {
            viewModel.loadAllMenuItem()
        } else {
            viewModel.loadMenuItemByCuisine(menuType)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SearchBar(
                value = search,
                onValueChange = {
                    search = it
                    if(search.isNotEmpty()){
                        isSearching = true
                    }
                    else{
                        isSearching = false
                    } },

                modifier = Modifier.padding(start = 20.dp)
            )
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

        ) {
            ElevatedButton(
                onClick = { menuType = "All" },
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

            dishCuisine.forEach { cuisine ->
                ElevatedButton(
                    onClick = { menuType = cuisine },
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
                if (search == "") {
                    launchAll = true
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
                                text = (i + 1).toString(),
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
                                        currentMenuItemId = menu.menuItemID
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
                                        currentMenuItemId = menu.menuItemID
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
                } else {
                    launchMenuItem = true
                    if (menuListState.value.menuItemList.isNotEmpty()) {
                        items(menuListState.value.menuItemList.size) { i ->
                            val menu = menus[i]

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .fillMaxWidth()
                                    .height(110.dp)
                            ) {
                                Text(
                                    text = (i + 1).toString(),
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
                                            currentMenuItemId = menu.menuItemID
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
                                            currentMenuItemId = menu.menuItemID
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
    }

}

@Composable
fun UploadAddDishImage(){
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
                //.padding(10.dp)
                .size(70.dp)
        ){
            Image(
                painter = painter,
                contentDescription = null,
                //contentScale = ContentScale.Crop,

                modifier = Modifier.clickable { launcher.launch("image/*") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDishDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (MenuItem,FoodDetails,NutritionFacts) -> Unit,
    menu: MenuItem,
    foodDetails: FoodDetails,
    nutritionFacts: NutritionFacts
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
    var newDishCarbohydrates by remember { mutableStateOf("") }
    var newDishProtein by remember { mutableStateOf("") }
    var newDishFats by remember { mutableStateOf("") }
    var newDishSalt by remember { mutableStateOf("") }
    var newDishSugar by remember { mutableStateOf("") }


    var isUploadImage by remember { mutableStateOf(false) }
    val dialogTitle = "ADD NEW DISH"

    var expandedCuisine by remember { mutableStateOf(false) }
    var selectedCuisine by remember { mutableStateOf("") }
    val menuCuisine = listOf("Western","Japanese","Korean","Malaysian","Thai","Beverages")

    var expandedStatus by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("") }
    val menuStatus = listOf("Active","Disabled","Deleted")


    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                UploadAddDishImage()
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
                                text = "Dish Status",
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
                        .padding(vertical = 10.dp)
                )
                OutlinedTextField(//new dish Carbohydrates
                    value = newDishCarbohydrates,
                    onValueChange = { newDishCarbohydrates = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Carbohydrates",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                    .padding(vertical = 10.dp)
                )
                OutlinedTextField(//new dish Protein
                    value = newDishProtein,
                    onValueChange = { newDishProtein = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Protein",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                    .padding(vertical = 10.dp)
                )
                OutlinedTextField(//new dish Fats
                    value = newDishFats,
                    onValueChange = { newDishFats = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Fats",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                    .padding(vertical = 10.dp)
                )
                OutlinedTextField(//new dish Salt
                    value = newDishSalt,
                    onValueChange = { newDishSalt = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Salt",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
                OutlinedTextField(//new dish Sugar
                    value = newDishSugar,
                    onValueChange = { newDishSugar = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {Text(
                        text = "Dish Sugar",
                        fontSize = 17.sp,
                    )},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Cancel")
                    }

                    TextButton(
                        onClick =
                        {
                            val newMenuItem = MenuItem(
                                itemName = newDishName,
                                description = newDishDescription,
                                cuisine = newDishCuisine,
                                rating = newDishRating.toDouble(),
                                price = newDishPrice.toDouble(),
                                status = newDishCuisine,
                            )
                            val newFoodDetails = FoodDetails(
                                servingSize = newDishServingSize.toDouble(),
                                ingredient = newDishIngredient
                            )
                            val newNutritionFacts = NutritionFacts(
                                carbohydrates = newDishCarbohydrates.toDouble(),
                                proteins = newDishProtein.toDouble(),
                                fats = newDishFats.toDouble(),
                                sugar = newDishSugar.toDouble(),
                                salt = newDishSalt.toDouble()
                            )
                        Toast.makeText(context, "Dish Added Successfully", Toast.LENGTH_SHORT).show()
                        onConfirmation(newMenuItem,newFoodDetails,newNutritionFacts)
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
    onConfirmation: (MenuItem,FoodDetails,NutritionFacts) -> Unit,
    menu: MenuItem,
    foodDetails: FoodDetails,
    nutritionFacts: NutritionFacts
    ) {

    val context = LocalContext.current
    var editDishName by remember { mutableStateOf(menu.itemName) }
    var editDishDescription by remember { mutableStateOf(menu.description) }
    var editDishRating by remember { mutableStateOf(menu.rating.toString()) }
    var editDishCuisine by remember { mutableStateOf(menu.cuisine) }
    var editDishPrice by remember { mutableStateOf(menu.price.toString()) }
    var editDishStatus by remember { mutableStateOf(menu.status) }
    var editDishServingSize by remember { mutableStateOf(foodDetails.servingSize.toString()) }
    var editDishIngredient by remember { mutableStateOf(foodDetails.ingredient) }
    var editDishCarbohydrates by remember { mutableStateOf(nutritionFacts.carbohydrates.toString()) }
    var editDishProtein by remember { mutableStateOf(nutritionFacts.proteins.toString()) }
    var editDishFat by remember { mutableStateOf(nutritionFacts.fats.toString()) }
    var editDishSalt by remember { mutableStateOf(nutritionFacts.salt.toString()) }
    var editDishSugar by remember { mutableStateOf(nutritionFacts.sugar.toString()) }

    if (foodDetails != null) {
        editDishIngredient = foodDetails.ingredient
        editDishServingSize = foodDetails.servingSize.toString()
        editDishCarbohydrates = nutritionFacts.carbohydrates.toString()
        editDishProtein = nutritionFacts.proteins.toString()
        editDishFat = nutritionFacts.fats.toString()
        editDishSalt = nutritionFacts.salt.toString()
        editDishSugar = nutritionFacts.sugar.toString()
    }

    val dialogTitle = "EDIT DISH"

    var expandedEditCuisine by remember { mutableStateOf(false) }
    var selectedEditCuisine by remember { mutableStateOf("") }
    val menuCuisine = listOf("Western","Japanese","Korean","Malaysian","Thai","Beverages")

    var expandedEditStatus by remember { mutableStateOf(false) }
    var selectedEditStatus by remember { mutableStateOf("") }
    val menuStatus = listOf("Active","Disabled", "Deleted")

    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty {menu.image }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
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

                Column(
                    modifier = Modifier
                ){
                    Card(//shape = CircleShape,
                        modifier = Modifier
                            //.padding(10.dp)
                            .size(80.dp)
                    ){
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier.clickable { launcher.launch("image/*") }
                        )
                    }
                }
                //UploadDishImage()

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
                    label = {
                        Text(
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
                                text = "Dish Status",
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

                OutlinedTextField(//edit dish ingredient
                    value = editDishIngredient,
                    onValueChange = { editDishIngredient = it },
                    singleLine = false,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    label = {
                        Text(
                        text = "Dish Ingredient",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )

                OutlinedTextField(//edit dish serving size
                    value = editDishServingSize,
                    onValueChange = { editDishServingSize = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                        text = "Dish Serving Size",
                        fontSize = 17.sp,
                    )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                OutlinedTextField(//edit dish carbohydrates
                    value = editDishCarbohydrates,
                    onValueChange = { editDishCarbohydrates = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Dish Carbohydrates",
                            fontSize = 17.sp,
                        )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                OutlinedTextField(//edit dish protein
                    value = editDishProtein,
                    onValueChange = { editDishProtein = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Dish Protein",
                            fontSize = 17.sp,
                        )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                OutlinedTextField(//edit dish fats
                    value = editDishFat,
                    onValueChange = { editDishFat = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Dish Fat",
                            fontSize = 17.sp,
                        )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                OutlinedTextField(//edit dish salt
                    value = editDishSalt,
                    onValueChange = { editDishSalt = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Dish Salt",
                            fontSize = 17.sp,
                        )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )
                OutlinedTextField(//edit dish salt
                    value = editDishSugar,
                    onValueChange = { editDishSugar = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Dish Sugar",
                            fontSize = 17.sp,
                        )},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )



                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Cancel")
                    }

                    TextButton(
                        onClick =
                        {
                            val latestMenuItem = MenuItem(
                                menuItemID = menu.menuItemID,
                                itemName = editDishName,
                                description = editDishDescription,
                                cuisine = editDishCuisine,
                                rating = editDishRating.toDouble(),
                                price = editDishPrice.toDouble(),
                                status = editDishStatus,
                            )
                            val latestFoodDetails = FoodDetails(
                                foodDetailsID = foodDetails.foodDetailsID,
                                servingSize = editDishServingSize.toDouble(),
                                ingredient = editDishIngredient
                            )
                            val latestNutritionFacts = NutritionFacts(
                                nutritionFactsID = nutritionFacts.nutritionFactsID,
                                carbohydrates = editDishCarbohydrates.toDouble(),
                                proteins = editDishProtein.toDouble(),
                                fats = editDishFat.toDouble(),
                                sugar = editDishSugar.toDouble(),
                                salt = editDishSalt.toDouble()
                            )

                            Toast.makeText(context, "Dish Edited Successfully", Toast.LENGTH_SHORT).show()
                            onConfirmation(latestMenuItem,latestFoodDetails,latestNutritionFacts)

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
    onConfirmation: (MenuItem) -> Unit,
    menu: MenuItem,
){

    val context = LocalContext.current
    var expandedChangeStatus by remember { mutableStateOf(false) }
    var selectedChangeStatus by remember { mutableStateOf(menu.status) }
    val changeStatusOptions = listOf("Active","Disabled", "Deleted")
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
                                text = "Dish Status",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            ) {
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = "Cancel")
                }

                TextButton(
                    onClick =
                    {
                        Log.i("change status", "ChangeDishStatusDialog: $selectedChangeStatus")
                        val changedMenuItemStatus = MenuItem(
                            menuItemID = menu.menuItemID,
                            itemName = menu.itemName,
                            price = menu.price,
                            rating = menu.rating,
                            description = menu.description,
                            cuisine = menu.cuisine,
                            status = selectedChangeStatus,
                        )
                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show()
                        onConfirmation(changedMenuItemStatus)
                        MenuItem(
                            status = menu.status
                        )
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
    foodDetails: FoodDetails,
    nutritionFacts: NutritionFacts,
    labelList: List<Pair<String, Dp>>

) {
    val image = menu.image
    val name = menu.itemName
    val price = menu.price
    val rating = menu.rating
    val description = menu.description
    val status = menu.status
    val ingredient = foodDetails.ingredient
    val cuisine = menu.cuisine
    val nutritionCarbohydrates = nutritionFacts.carbohydrates
    val nutritionProtein = nutritionFacts.proteins
    val nutritionFat = nutritionFacts.fats
    val nutritionSalt = nutritionFacts.salt
    val nutritionSugar = nutritionFacts.sugar


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
                            text = ingredient,
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
                        Text(//nutrition
                            text = userHeader[8].first,
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp
                        )
                        Text(
                            text = "Carbohydrates : " + nutritionCarbohydrates.toString() + "g\n" +
                                    "Protein  : " + nutritionProtein.toString() + "g\n" +
                                    "Fats         : " + nutritionFat.toString() + "g\n" +
                                    "Salt         : " + nutritionSalt.toString() + "g\n" +
                                    "Sugar        : " + nutritionSugar.toString() + "g\n"

                                    ,fontSize = 20.sp
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


//@Preview(showBackground = true, device = Devices.TABLET)
//@Composable
//fun StaffMenuLandscapePreview(){
//    StaffMenuScreen()
//}
//
//@Preview(showBackground = true, device = Devices.PHONE)
//@Composable
//fun StaffMenuScreenPhonePreview() {
//    StaffMenuScreen()
//}

