package com.example.apapunada.ui.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.MainActivity
import com.example.apapunada.MoreScreen
import com.example.apapunada.R
import com.example.apapunada.StaffScreen
import com.example.apapunada.UserScreen
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    user: User,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name).uppercase(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        actions = {
            DisplayImagesFromByteArray(
                byteArray = user.image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable {
                        expanded = true
                    }
                ,
                contentScale = ContentScale.Crop
            )

            if (expanded) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = user.username,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        onClick = { navController.navigate(MoreScreen.Profile.name) }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        thickness = 3.dp
                    )

                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            authViewModel.logout()
                            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                            navController.navigate("Start") { // Navigate back to the Start route
                                popUpTo(navController.graph.startDestinationId)
                            }
                        }
                    )
                }
            }
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
            actions = { navController.navigate(UserScreen.Home.name) }
        ),
        Item(
            title = { Text(text = "Wait-list")},
            icon = { Icon(Icons.AutoMirrored.Rounded.List, contentDescription = "Wait-list") },
            actions = { navController.navigate(UserScreen.Waitlist.name) }
        ),
        Item(
            title = { Text(text = "Order")},
            icon = { Icon(Icons.Rounded.ShoppingCart, contentDescription = "Order") },
            actions = { navController.navigate("Ordering") }
        ),
        Item(
            title = { Text(text = "Rewards")},
            icon = { Icon(Icons.Rounded.Favorite, contentDescription = "Rewards") },
            actions = { navController.navigate("Rewarding") }
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
            actions = { navController.navigate(UserScreen.More.name) }
        ),
    )
    
    NavigationBar(
        modifier = Modifier.clip(shape),
        containerColor = colorResource(R.color.primary_100),
        contentColor = Color(0xFF565d6d),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
            if (onBackButtonClicked != {}) {
                IconButton(
                    onClick = onBackButtonClicked
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier.fillMaxSize()
                    )
                }
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
fun MyBottomButton(
    content: String,
    order: Order? = null,
    price: Double? = null,
    detailCount: Int? = null,
    amount: Double? = null,
    onClick: () -> Unit ={}
) {
    Card(
        colors = CardDefaults.cardColors(colorResource(R.color.primary_200)),
        shape = RoundedCornerShape(15.dp, 15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium), 20.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if(order == null) {
                    Arrangement.Center
                } else {
                    Arrangement.SpaceBetween
                }
                ,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (order != null) {
                    Text(text = detailCount.toString() + " items")
                }

                Text(
                    text = content +
                        if (price != null) {
                            " (RM " + formattedString(price) + ")"
                        } else {
                               ""
                        }
                    ,
                    textAlign = if (price == null) TextAlign.Center else null,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                if (order != null) {
                    Text(text = "RM " + amount?.let { formattedString(it) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffAppBar(
    currentScreen: StaffScreen,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    user: User,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            DisplayImagesFromByteArray(
                byteArray = user.image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable {
                        expanded = true
                    }
                ,
                contentScale = ContentScale.Crop
            )

            if (expanded) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(user.username) },
                        onClick = {},
                        enabled = false
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        thickness = 3.dp
                    )

                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            authViewModel.logout()
                            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Clear back stack
                            context.startActivity(intent)
                        }
                    )
                }
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
fun IndeterminateCircularIndicator(title: String = "") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.1f))
            .zIndex(2f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = colorResource(R.color.primary),
                trackColor = colorResource(R.color.primary_200),
                strokeWidth = 5.dp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = title)
        }
    }

}

@Composable
fun MyAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Dialog Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview(showBackground = true, device = PHONE)
@Composable
fun CommonUiPortraitPreview() {
    Scaffold(
//        topBar = { StaffAppBar(currentScreen = StaffScreen.Dashboard) },
        bottomBar = { MyBottomNavBar(1, rememberNavController()) }
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
//        topBar = { StaffAppBar(currentScreen = StaffScreen.Dashboard) },
        bottomBar = {  }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
        }
    }
}