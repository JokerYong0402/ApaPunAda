package com.example.apapunada.ui.users

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.viewmodel.AuthViewModel

enum class ProfileNav(@StringRes val title: Int){
    More(R.string.more),
    Profile(R.string.profile),
    EditProfile(R.string.edit_profile),
    DeleteProfile(R.string.delete_profile),
    Introduction(R.string.introduction)
}

@Composable
fun ProfileNav(
    navController: NavHostController = rememberNavController()
){
    val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(navController = navController, startDestination = ProfileNav.Profile.name){
        composable(route = ProfileNav.Introduction.name){
           IntroductionPager(onLoginButtonClicked = { /*TODO*/ }) {

           }
        }
        composable(route = ProfileNav.More.name){
            MoreScreen( navController = navController)
        }

        composable(ProfileNav.Profile.name){
            ProfileScreen(
                onBackButtonClicked = {navController.navigate(ProfileNav.More.name)},

                onEdit = { navController.navigate("EditProfile")},
                onDelete = { navController.navigate("DeleteProfile")},
                onLogin = { navController.navigate(ProfileNav.Introduction.name)}
            )

        }
        composable(route = ProfileNav.EditProfile.name){
            EditProfileScreen(

                onBackButtonClicked = {navController.navigate(ProfileNav.Profile.name)},

                onProfile = { navController.navigate(ProfileNav.Profile.name)},
                //authViewModel = authViewModel,

                )
        }
        composable(route = ProfileNav.DeleteProfile.name) {
            DeleteProfileScreen(
                onBackButtonClicked = { navController.navigate(ProfileNav.Profile.name) },

                onLogin = { navController.navigate(ProfileNav.Introduction.name) }
            )
        }

        }

}

@Preview
@Composable
fun ProfileNavPreview() {
    ProfileNav(
    )
}