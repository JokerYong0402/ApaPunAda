package com.example.apapunada.ui.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyBottomNavBar
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun MenuScreen() {
    var textInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.menu)) },
        bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        )
        {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
            // content of page add here
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(dimensionResource(R.dimen.padding_small)),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(
                        text = "Apa Pun Ada Menu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                ){
                    Image(
                        painter = painterResource(R.drawable.searchicon),
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            //.padding(dimensionResource(R.dimen.padding_small))
                            //.fillMaxSize()
                            .size(
                                width = 30.dp,
                                height = 30.dp
                            ),
                        alignment = Alignment.CenterEnd
                    )
                    SearchBar(
                        value = textInput,
                        onValueChange = { textInput = it }
                        ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                        )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ){

                }
                /*Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),
                    shape = RoundedCornerShape(50.dp)


                ){

                }*/

            }
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(horizontal = 20.dp)
            .background(color = Color.White)
            .height(60.dp)
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp,

                ),
            )
            .border(
                BorderStroke(width = 1.dp, colorResource(R.color.black)),
                shape = RoundedCornerShape(
                    size = 12.dp,
                )
            )
        ,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
            text = "Search",
            fontSize = 10.sp,
            color = colorResource(id = R.color.black),
            modifier = modifier
                //.padding(bottom = 100.dp)
        )},
        //Design for the text that user type in
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = colorResource(id = R.color.white)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
}



@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen(
    )
}