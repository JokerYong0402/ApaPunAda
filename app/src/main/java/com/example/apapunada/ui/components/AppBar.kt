package com.example.apapunada.ui.components

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R
import com.example.apapunada.StaffScreen
import com.example.apapunada.model.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    profileName: String, 
    @DrawableRes img: Int
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name).uppercase(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        actions = {
            Image(
                painter = painterResource(img),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable { /* TODO */ }
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MyBottomNavBar(
    selectedBar: Int = 1,
    navController: NavHostController = rememberNavController()
) {
    val primaryColor = colorResource(R.color.primary)
    val shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)

    data class Item(
        val title: @Composable () -> Unit,
        val icon: @Composable () -> Unit,
        val actions: () -> Unit,
    )

    val navItems = listOf(
        Item(
            title = {
                Text(
                    text = "Home",
                    color = if (selectedBar == 1) primaryColor else Color.DarkGray
                )}
            ,
            icon = {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Home",
                    tint = if (selectedBar == 1) primaryColor else Color.DarkGray
                )
            },
            actions = { navController.navigate("home") }
        ),
        Item(
            title = { Text(text = "Wait-list")},
            icon = { Icon(Icons.Rounded.List, contentDescription = "Wait-list") },
            actions = { navController.navigate("waitlist") }
        ),
        Item(
            title = { Text(text = "Order")},
            icon = { Icon(Icons.Rounded.ShoppingCart, contentDescription = "Order") },
            actions = { navController.navigate("order") }
        ),
        Item(
            title = { Text(text = "Rewards")},
            icon = { Icon(Icons.Rounded.Favorite, contentDescription = "Rewards") },
            actions = { navController.navigate("rewards") }
        ),
        Item(
            title = {
                Text(
                    text = "More",
                    color = if (selectedBar == 5) primaryColor else Color.DarkGray
                )}
            ,
            icon = {
                Icon(
                    Icons.Rounded.MoreVert,
                    contentDescription = "More",
                    tint = if (selectedBar == 5) primaryColor else Color.DarkGray
                )
            },
            actions = { navController.navigate("more") }
        ),
    )
    
    NavigationBar(
        modifier = Modifier.clip(shape),
        containerColor = colorResource(R.color.primary_100),
        contentColor = Color(0xFF565d6d),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            navItems.forEachIndexed { _, item ->
                NavigationBarItem(
                    selected = false,
                    onClick = item.actions,
                    icon = item.icon,
                    label = item.title,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopTitleBar(
    title: String,
    onBackButtonClicked: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.primary_200),
            titleContentColor = Color.Black,
        ),
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(250.dp)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClicked
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp)
    )
}

@Composable
fun MyBottomButton(content: String, order: Order? = null, price: Double? = null) {

    val arrangement = if (order != null) { Arrangement.SpaceBetween } else { Arrangement.Center }

    Card(
        colors = CardDefaults.cardColors(colorResource(R.color.primary_200)),
        shape = RoundedCornerShape(15.dp, 15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium), 20.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = arrangement,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (order != null) {
                    Text(text = order.orderDetails.count().toString() + " items")
                }

                Text(
                    text = content +
                        if (price != null) {
                            " (RM " + formattedString(price) + ")"
                        } else {
                               ""
                        }
                    ,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                if (order != null) {
                    Text(text = "RM " + formattedString(order.amount))
                }
            }
        }
    }
}

//@Composable
//fun StaffTopAppBar(
//    modifier: Modifier = Modifier,
//    coroutineScope: CoroutineScope = rememberCoroutineScope()
//) {
//    val configuration = LocalConfiguration.current
//    when (configuration.orientation) {
//        Configuration.ORIENTATION_LANDSCAPE -> {
//            StaffAppBarLandscape()
//        }
//        else -> {
//            StaffAppBarPortrait()
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffAppBarPortrait(
    currentScreen: StaffScreen,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(250.dp)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "side navigation bar",
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        actions = {
            Image(
                painter = painterResource(R.drawable.profile_image),
                contentDescription = "staff",
                modifier = Modifier
                    .size(45.dp)
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffAppBarLandscape(
    modifier: Modifier = Modifier
) {
    val appName = stringResource(R.string.app_name)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.primary_200),
            titleContentColor = Color.Black,
        ),
        title = {
            Text(
                text = appName,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(250.dp)
            )
        },
        actions = {
            Image(
                painter = painterResource(R.drawable.profile_image),
                contentDescription = "staff",
                modifier = Modifier
                    .size(45.dp)
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp)
    )
}

@Preview(showBackground = true, device = PHONE)
@Composable
fun CommonUiPortraitPreview() {
    Scaffold(
        topBar = { StaffAppBarPortrait(StaffScreen.Dashboard) },
        bottomBar = { }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}

@Preview(showBackground = true, device = TABLET)
@Composable
fun CommonUiLandscapePreview() {
    Scaffold(
        topBar = { StaffAppBarLandscape() },
        bottomBar = {  }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}