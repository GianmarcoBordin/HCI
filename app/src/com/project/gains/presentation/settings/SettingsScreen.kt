package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.SettingItem
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme


@Composable
fun SettingsScreen(
    viewModel: GeneralViewModel,
    navController: NavController
) {
    var notification = remember {
        mutableStateOf(false)
    }
    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = " General Settings" ,
                    button= {
                        LogoUser(
                            modifier = Modifier.size(60.dp), R.drawable.pexels5
                        ) { navController.navigate(Route.AccountScreen.route) }
                    },
                    button1 = {

                    }
                )
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
                }
            },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    item {
                        SettingItem(
                            icon = Icons.Default.Person, // Replace with your desired icon
                            title = "Account Preferences",
                            onClick = {navController.navigate(Route.AccountScreen.route) }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    item {
                        SettingItem(
                            icon = Icons.Default.Send, // Replace with your desired icon
                            title = "Sharing Preferences",
                            onClick = { navController.navigate(Route.SettingScreen.route) }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    item {
                        SettingItem(
                            icon = Icons.Default.Group, // Replace with your desired icon
                            title = "Tutorial",
                            onClick = { /* Handle click */ }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    item {
                        SettingItem(
                            icon = Icons.Default.Support, // Replace with your desired icon
                            title = "Write to support",
                            onClick = {  }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    item {
                        SettingItem(
                            icon = Icons.Default.Share, // Replace with your desired icon
                            title = "Tell a friend",
                            onClick = { /* Handle click */ }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    item {
                        SettingItem(
                            icon = Icons.Default.Star, // Replace with your desired icon
                            title = "Rate the app",
                            onClick = { /* Handle click */ }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun settingsPreview(){
    val generalViewModel:GeneralViewModel= hiltViewModel()
    GainsAppTheme {
        SettingsScreen(viewModel = generalViewModel, navController = rememberNavController())
    }
}




