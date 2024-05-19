package com.example.apapunada.ui.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun FAQScreen(
    onBackClicked: () -> Unit
) {
    val faqLists = listOf(
        Triple("01  ",stringResource(id = R.string.faq1), stringResource(id = R.string.faq2)),
        Triple("02  ",stringResource(id = R.string.faq3), stringResource(id = R.string.faq4)),
        Triple("03  ",stringResource(id = R.string.faq5), stringResource(id = R.string.faq6)),
        Triple("04  ",stringResource(id = R.string.faq7), stringResource(id = R.string.faq8)),
        Triple("05  ",stringResource(id = R.string.faq9), stringResource(id = R.string.faq10)),
        Triple("06  ",stringResource(id = R.string.faq11), stringResource(id = R.string.faq12)),
        Triple("07  ",stringResource(id = R.string.faq13), stringResource(id = R.string.faq14))

    )

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.faq),onBackClicked) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                for (faqList in faqLists){
                    ExpandableCard(faqList.first,faqList.second,faqList.third)
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    number: String,
    title: String,
    details: String
) {
    val primaryColor = colorResource(id = R.color.primary)
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = primaryColor,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 15.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                Text(text = number, fontSize = 20.sp)
                Text(text = title, fontSize = 20.sp)
            }
            if (expanded) {
                Card (
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = primaryColor
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column {
                        Text(
                            text = details,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}