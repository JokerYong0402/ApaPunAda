package com.example.apapunada.ui.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    itemList: List<String>
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedField by remember { mutableStateOf("Field") }
    var primaryColor = colorResource(id = R.color.primary)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedField,
            onValueChange = {},
            label = {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.feedback6),
                        contentDescription = "Filter",
                        modifier = Modifier
                            .size(15.dp)
                            .background(Color.Transparent)
                    )
                    Text(
                        color =  primaryColor,
                        text = "  Field",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .background(Color.Transparent)
                    )
                }

            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .background(Color.Transparent)
                .width(180.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = primaryColor, // Change the focused outline color
                unfocusedIndicatorColor = colorResource(id = R.color.primary_200), // Change the unfocused outline color
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { /*TODO*/ },
            modifier = Modifier
                .background(Color.White)
        ) {
            itemList.forEach { field ->
                DropdownMenuItem(
                    text = { Text(text = field) },
                    onClick = {
                        selectedField = field
                        expanded = false
                        Toast.makeText(context, field, Toast.LENGTH_SHORT)
                            .show()
                    }
                )
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
    val primaryColor = colorResource(id = R.color.primary)

    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.feedback8),
                contentDescription = "search",
                alignment = Alignment.CenterEnd,
                modifier = modifier
                    .size(20.dp)
            )
        },
        modifier = modifier
            .padding(top = 7.5.dp)
            .background(color = Color.Transparent)
            .height(56.dp)
            .width(180.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = primaryColor, // Change the focused outline color
            unfocusedIndicatorColor = colorResource(id = R.color.primary_200), // Change the unfocused outline color
        ),
        placeholder = {
            Text(
                text = "  Search",
                fontSize = 14.sp,
                color = colorResource(id = R.color.black)
            )
        },
    )
}