package com.example.apapunada.ui.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun TermsAndCondition(
    navController: NavController
) {
    val text1 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("1.1 Eligibility")
        }
        append(stringResource(R.string.TNC1))
    }
    val text2 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("1.2 Account Registration")
        }
        append(stringResource(R.string.TNC2))
    }
    val text3 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("1.3 User Responsibilities")
        }
        append(stringResource(R.string.TNC3))
    }
    val text4 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("2.1 Placing Orders")
        }
        append(stringResource(R.string.TNC4))
    }
    val text5 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("2.2 Payments")
        }
        append(stringResource(R.string.TNC5))
    }
    val text6 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("2.3 Cancellations and Refunds")
        }
        append(stringResource(R.string.TNC6))
    }
    val text7 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("3.1 Accuracy of Information")
        }
        append(stringResource(R.string.TNC7))
    }
    val text8 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("3.2 Restaurant Responsibility")
        }
        append(stringResource(R.string.TNC8))
    }
    val text9 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("4.1 Reviews and Feedback")
        }
        append(stringResource(R.string.TNC9))
    }
    val text10 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("4.2 Prohibited Content")
        }
        append(stringResource(R.string.TNC10))
    }
    val text11 = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("5.1 Privacy Policy")
        }
        append(stringResource(R.string.TNC11))
    }
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = { MyTopTitleBar(
            title = "Terms & Conditions",
            onBackButtonClicked = { navController.navigateUp() }
        ) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Welcome to APAPUNADA! These Terms and Conditions (\"Terms\") govern your use of our food ordering " +
                                "application (\"App\") and the services provided through it. By accessing or using our App, you agree " +
                                "to be bound by these Terms. If you do not agree with these Terms, please do not use the App.\n",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "1. Use of The App\n",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = text1,
                        fontSize = 18.sp,
                    )
                    Text(
                        text = text2,
                        fontSize = 18.sp
                    )
                    Text(
                        text = text3,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "2. Orders and Payments\n",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = text4,
                        fontSize = 18.sp
                    )
                    Text(
                        text = text5,
                        fontSize = 18.sp
                    )
                    Text(
                        text = text6,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "3. Restaurant Listings and Menu Information\n",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = text7,
                        fontSize = 18.sp
                    )
                    Text(
                        text = text8,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "4. User Content\n",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = text9,
                        fontSize = 18.sp
                    )
                    Text(
                        text = text10,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "5. Privacy\n",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = text11,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TermsAndConditionPreview() {
    TermsAndCondition(navController = rememberNavController())
}