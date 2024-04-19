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
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(profileName: String, @DrawableRes img: Int) {
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
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MyBottomNavBar(selectedBar: Int = 1) {
    val primaryColor = colorResource(R.color.primary)
    val shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)

    data class Item(
        val title: @Composable () -> Unit,
        val icon: @Composable () -> Unit
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
            }
        ),
        Item(
            title = { Text(text = "Wait-list")},
            icon = { Icon(Icons.Rounded.List, contentDescription = "Wait-list") }
        ),
        Item(
            title = { Text(text = "Order")},
            icon = { Icon(Icons.Rounded.ShoppingCart, contentDescription = "Order") }
        ),
        Item(
            title = { Text(text = "Rewards")},
            icon = { Icon(Icons.Rounded.Favorite, contentDescription = "Rewards\"") }
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
            }
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
                    onClick = { /*TODO*/ },
                    icon = item.icon,
                    label = item.title,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopTitleBar(title: String) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

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
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        scrollBehavior = scrollBehaviour,
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp)
    )
}

@Composable
fun MyBottomButton(content: String, order: Boolean = false) {

    val arrangement = if (order) { Arrangement.SpaceBetween } else { Arrangement.Center }

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
                if (order) {
                    Text(text = "0 items")
                }

                Text(
                    text = content,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                if (order) {
                    Text(text = "RM 0.00")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonUiPreview() {
    Scaffold(
        topBar = { MyTopTitleBar(title = "Order") },
        bottomBar = { MyBottomButton(content = "Next") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // content of page
        }
    }
}