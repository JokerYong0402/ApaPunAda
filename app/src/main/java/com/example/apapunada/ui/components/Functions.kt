package com.example.apapunada.ui.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.apapunada.R

fun formattedString(value: Double): String {
    return String.format("%.2f", value)
}

@Composable
fun SetPortraitOrientationOnly() {
    val context = LocalContext.current
    val activity = context.findActivity()

    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

@Composable
fun EnableScreenOrientation() {
    val context = LocalContext.current
    val activity = context.findActivity()
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun PopupWindowDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    confirmMessage: String,
    containerColor: Color
){
    val primaryColor = colorResource(R.color.primary)

    AlertDialog(
        containerColor = Color.White,
        shape = RoundedCornerShape(5.dp),
        title = {
            Text(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 10.dp,
                        bottom = 30.dp
                    ),
                fontWeight = FontWeight.Bold,
                text = dialogTitle
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 5.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor,
                    contentColor = Color.White
                ),
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmMessage)
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier
                    .padding(
                        start = 70.dp
                    )
                    .size(width = 75.dp, height = 35.dp),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(width = 1.dp, primaryColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = primaryColor
                ),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
