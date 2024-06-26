package com.example.apapunada.ui.users

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apapunada.R
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.components.DisplayImagesFromByteArray
import com.example.apapunada.ui.components.MyTopTitleBar
import com.example.apapunada.ui.components.formattedDate
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.UserState
import com.example.apapunada.viewmodel.UserViewModel


@Composable
fun ProfileScreen(
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    authViewModel: AuthViewModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onLogin: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val userState = viewModel.userState.collectAsState(initial = UserState())
    viewModel.loadUserByUserId(authViewModel.userState.value.user.userID)

    val user = userState.value.user

    Scaffold(
        topBar = { MyTopTitleBar(title = stringResource(R.string.profile), onBackClicked) },
        //bottomBar = { MyBottomNavBar() }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
        {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                // content of page add here
                Row(//PROFILE CARD
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .border(
                            3.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    DisplayImagesFromByteArray(
                        byteArray = user.image,
                        modifier = Modifier
                            .size(
                                width = 100.dp,
                                height = 100.dp
                            )
                            .clip(CircleShape)
                            .padding(dimensionResource(R.dimen.padding_medium))
                        ,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
//                    Image(//contain image
//                        painter = painterResource(R.drawable.profile_image),
//                        contentDescription = "Profile picture",
//                        modifier = Modifier
//                            //.fillMaxSize()
//                            .size(
//                                width = 100.dp,
//                                height = 100.dp
//                            )
//                            .clip(CircleShape)
//                            .padding(dimensionResource(R.dimen.padding_medium))
//                    )
                    Column(//second column
                        modifier = Modifier
                            //.padding(top = 10.dp)
                            .width(210.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = user.username,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 18.dp,bottom = 10.dp)

                            )
                        Text(
                            text = "Point : " + user.point,
                            fontSize = 18.sp
                        )


                    }
                    Image(//edit icon
                        painter = painterResource(R.drawable.editicon),
                        contentDescription = "edit",
                        modifier = Modifier
                            //.fillMaxSize()
                            .size(
                                width = 30.dp,
                                height = 30.dp
                            )
                            .clickable { onEdit() }
                    )

                }

                Row(//NAME
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.nameicon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = user.username,
                            fontSize = 16.sp,

                            )
                    }
                }

                Spacer(modifier = Modifier)

                Row(//GENDER
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.gendericon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            //.fillMaxSize()
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = user.gender,
                            fontSize = 16.sp,
                        )
                    }
                }

                Row(//Date Of Birth
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.dobicon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            //.fillMaxSize()
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = formattedDate(user.dob,"date"),
                            fontSize = 16.sp,

                            )
                    }
                }

                Spacer(modifier = Modifier)

                Row(//Email Address
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.emailicon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            //.fillMaxSize()
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = user.email,
                            fontSize = 16.sp,

                            )
                    }
                }

                Row(//Password
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.passwordicon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            //.fillMaxSize()
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = user.password,
                            fontSize = 16.sp,

                            )
                    }
                }

                Row(//Phone number
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .border(
                            2.dp,
                            colorResource(R.color.primary),
                            shape = RoundedCornerShape(50.dp)
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.phonenumbericon),
                        contentDescription = "Name Icon",
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            //.fillMaxSize()
                            .size(
                                width = 40.dp,
                                height = 40.dp
                            )
                    )
                    Column(//second column
                        modifier = Modifier
                            .width(300.dp)
                            .height(100.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = user.phoneNo,
                            fontSize = 16.sp,

                            )
                    }
                }

                val context = LocalContext.current

                ElevatedButton(
                    onClick = {
                        Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
                        onLogin()
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.primary)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.logout),
                        fontSize = 18.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 35.dp, vertical = 15.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 35.dp, vertical = 10.dp)

                            .width(300.dp)
                            .height(100.dp),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            modifier =  Modifier
                                .clickable { onDelete() },
                            text = "Request Account Deletion",
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.primary)

                            )
                    }
                }


            }

        }


    }
}



//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen(
//        onDelete = {}, onLogin = {}, BonEdit = {}
//    )
//}
