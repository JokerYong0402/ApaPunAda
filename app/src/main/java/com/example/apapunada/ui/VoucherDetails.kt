package com.example.apapunada.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apapunada.R
import com.example.apapunada.ui.components.MyTopTitleBar

@Composable
fun VoucherDetails(
    image: Painter,
    voucherRM: String
) {
    Scaffold(
        topBar = { MyTopTitleBar(title = "Voucher Details") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            // content of page
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = image,
                contentDescription = "Voucher",
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp, 25.dp)
                ) {
                    Text(
                        text = "Validity",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "None",
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier.padding(top = 25.dp),
                        text = "Min. Spend",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = voucherRM,
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier.padding(top = 25.dp),
                        text = "Terms and Conditions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = "1. This Voucher is valid for delivery only.",
                        fontSize = 16.sp,
                    )
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = "2. This Voucher is not exchangeable or replaceable for cash.",
                        fontSize = 16.sp,
                    )
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = "3. APAPUNADA shall be entitled to withhold any benefits under the services rendered," +
                                " with or without notice to the users, if the user is found to have breached APAPUNADA Users Terms and Conditions.",
                        fontSize = 16.sp,
                    )
                }
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primary)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .widthIn(min = 300.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Text(text = "Apply This Voucher")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherDetailsPreview() {
    VoucherDetails(image = painterResource(R.drawable.voucher_details_rm1), voucherRM = "RM1")
}