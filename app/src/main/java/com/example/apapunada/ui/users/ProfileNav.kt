package com.example.apapunada.ui.users

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R
import com.example.apapunada.data.UserSample.Users

enum class ProfileNav(@StringRes val title: Int){
    Profile(R.string.profile),
    EditProfile(R.string.edit_profile),
    DeleteProfile(R.string.delete_profile),
    Login(R.string.login)
}

@Composable
fun ProfileNav(
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController, startDestination = ProfileNav.Profile.name){
        composable(ProfileNav.Profile.name){
            ProfileScreen(
                onEdit = { navController.navigate("EditProfile")},
                onDelete = { navController.navigate("DeleteProfile")},
                onLogin = { navController.navigate("login")}
            )

        }
        composable(ProfileNav.EditProfile.name){
            EditProfileScreen(
                //TODO
                user = Users.first(),
                onProfile = { navController.navigate("Profile")}
            )
        }
    }

}