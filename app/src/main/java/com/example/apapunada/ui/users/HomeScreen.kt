package com.example.apapunada.ui.users

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apapunada.R
import com.example.apapunada.data.dataclass.MenuItem
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.MyBottomNavBar
import com.example.apapunada.ui.components.MyTopAppBar
import com.example.apapunada.ui.components.SetPortraitOrientationOnly
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.MenuItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    menuItemViewModel: MenuItemViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    SetPortraitOrientationOnly()

    val density = LocalDensity.current
    val primaryColor = colorResource(R.color.primary)

    menuItemViewModel.loadAllMenuItem()
    val topFoods: List<MenuItem> = menuItemViewModel.menuListState.value.menuItemList

    var imgPager by remember { mutableStateOf(1) }

    val imageRes = when(imgPager){
        1 -> R.drawable.intro1
        2 -> R.drawable.intro2
        else -> R.drawable.intro3
    }

    LaunchedEffect(Unit) {
        launch {
            while (true) {
                delay(3000)
                if (imgPager < 3) {
                    withContext(Dispatchers.Main) {
                        imgPager += 1
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        imgPager = 1
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                authViewModel.userState.value.user,
                navController,
                authViewModel
            )
        },
        bottomBar = { MyBottomNavBar(1, navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .height(265.dp)
                        .fillMaxWidth()

                        .padding(dimensionResource(R.dimen.padding_medium)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = " ",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(500.dp, 200.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .padding(top = 10.dp)
                    ) {
                        for (i in 1..3) {
                            if (imgPager == i) {
                                Canvas(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(0.dp)
                                        .clip(CircleShape)
                                        .clickable { imgPager = i }
                                    ,
                                    onDraw = {
                                        drawCircle(
                                            color = primaryColor,
                                            radius = this.size.minDimension / 3.5f
                                        )
                                        drawCircle(
                                            color = primaryColor,
                                            radius = this.size.minDimension / 4.5f
                                        )
                                    }
                                )
                            } else {
                                Canvas(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(0.dp)
                                        .clip(CircleShape)
                                        .clickable { imgPager = i }
                                    ,
                                    onDraw = {
                                        drawCircle(
                                            color = primaryColor,
                                            radius = this.size.minDimension / 3.5f
                                        )
                                        drawCircle(
                                            color = Color.White,
                                            radius = this.size.minDimension / 4.5f
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = "Top Rated",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        topFoods.forEach { food ->
                            if (food.rating >= 4.8) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .size(150.dp, 275.dp)
                                        .padding(horizontal = 8.dp)
                                        .shadow(
                                            elevation = 15.dp,
                                            spotColor = primaryColor,
                                        )
                                        .clickable(enabled = false) {}
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(dimensionResource(R.dimen.padding_small))
                                            .fillMaxSize()
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.staricon), // TODO
                                            contentDescription = "Food",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(110.dp)
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(16.dp, 16.dp))
                                        )

                                        Text(
                                            text = food.itemName,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = "Star",
                                                tint = Color.Yellow
                                            )

                                            Text(
                                                text = (food.rating).toString(),
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Normal,
                                                modifier = Modifier.padding(horizontal = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Deals
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = "Deals",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        for (i in 1..3) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(275.dp)
                                        .shadow(
                                            elevation = 20.dp,
                                            spotColor = primaryColor,
                                        )
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { }
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(dimensionResource(R.dimen.padding_medium))
                                            .fillMaxSize()
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.intro2),
                                            contentDescription = "Food",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp)
                                                .clip(RoundedCornerShape(16.dp, 16.dp))
                                        )

                                        Text(
                                            text = "Promotions Order NOW !",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.size(300.dp, 50.dp)
                                        )

                                        IconButton(
                                            onClick = { /*TODO*/ },
                                            modifier = Modifier
                                                .background(
                                                    shape = CircleShape,
                                                    color = primaryColor
                                                )
                                                .align(Alignment.End)
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                                contentDescription = "Next Icon Button",
                                                tint = Color.White
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
//    HomeScreen()
}