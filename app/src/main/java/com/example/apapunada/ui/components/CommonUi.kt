package com.example.apapunada.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apapunada.R

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
            title = { Text(text = "Home", color = primaryColor) },
            icon = {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Home",
                    tint = primaryColor)
            }
        ),
        Item(
            title = { Text(text = "Waitlist")},
            icon = { Icon(Icons.Rounded.List, contentDescription = "Waitlist") }
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
            title = { Text(text = "More")},
            icon = { Icon(Icons.Rounded.MoreVert, contentDescription = "More") }
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

            navItems.forEachIndexed { index, item ->
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

@Preview(showBackground = true)
@Composable
fun CommonUiPreview() {
    Scaffold(
        bottomBar = { MyBottomNavBar(1) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // content of page
        }
    }
}