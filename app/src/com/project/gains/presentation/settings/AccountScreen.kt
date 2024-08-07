package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.presentation.components.EditProfileDialog
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.settings.events.SignOutEvent
import com.project.gains.presentation.settings.events.UpdateEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.Constants.SIGN_OUT_SUCCESS
import com.project.gains.util.Constants.UPDATE_SUCCESS


@Composable
fun AccountScreen(
    settingsHandler: (UpdateEvent.Update) -> Unit,
    signOutHandler: (SignOutEvent.SignOut) -> Unit,
    viewModel: SettingsViewModel,
    navController: NavController,
    completionMessage: MutableState<String>
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val data by viewModel.data.observeAsState()
    val flag = remember { mutableStateOf(false) }
    remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(userProfile?.displayName ?: "New Name") }
    var newEmail by remember { mutableStateOf(userProfile?.email ?: "New Email") }
    var newPassword by remember { mutableStateOf("New Password") }
    val showDialog = remember { mutableStateOf(false) }
    val showExitDialog = remember { mutableStateOf(false) }
    val showDialogComplete = remember { mutableStateOf(false) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pexels1), // Placeholder image
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Display Name
                    Text(
                        text = userProfile?.displayName ?: "Username",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )

                    // Display Email
                    Text(
                        text = userProfile?.email ?: "Email",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Edit Profile Button
                    Button(
                        onClick = { showDialog.value = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                    ) {
                        Text(text = "Edit Profile")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Logout Button
                    Button(
                        onClick = { showExitDialog.value = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text(text = "Logout", color = MaterialTheme.colorScheme.onTertiary)
                    }


                    // Observe changes in data
                    if (data?.isNotEmpty() == true) {
                        if (data == UPDATE_SUCCESS && !flag.value) {
                            showDialog.value = true
                            flag.value = true
                        } else if (data != SIGN_OUT_SUCCESS && data != UPDATE_SUCCESS && !flag.value) {
                            showDialog.value = true
                            flag.value = true
                        }
                        else if (data == SIGN_OUT_SUCCESS) {
                            // Change page if all ok
                            if (viewModel.navigateToAnotherScreen.value == true) {
                                navController.navigate(Route.SignInScreen.route)
                                viewModel.onNavigationComplete()
                            }
                        }
                    }
                }
            }

            if (showExitDialog.value) {
                FeedbackAlertDialog(
                    onDismissRequest = { showExitDialog.value=false },
                    onConfirm = {
                        showExitDialog.value=false
                        signOutHandler(SignOutEvent.SignOut)},
                    title = "Logout check",
                    text = "Are you sure to logout?",
                    icon = Icons.Default.Warning
                )
            }


            if (showDialog.value) {
                EditProfileDialog(
                    newName = newName,
                    newEmail = newEmail,
                    newPassword = newPassword,
                    onNameChange = { newName = it },
                    onEmailChange = { newEmail = it },
                    onPasswordChange = { newPassword = it },
                    onSave = {
                        settingsHandler(UpdateEvent.Update(newName, newEmail, newPassword))
                        showDialog.value = false
                        flag.value=true
                        showDialogComplete.value = true
                    },
                    onDismiss = { showDialog.value = false },
                    icon = Icons.Default.Mode
                )
            }
            if (showDialogComplete.value) {
                completionMessage.value="Profile Updated!"
                showDialogComplete.value=false
            }
        }
    }
}









@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun PreviewAccount(){
    val viewModel:SettingsViewModel = hiltViewModel()
    GainsAppTheme {
        AccountScreen(
            settingsHandler = {},
            signOutHandler = {},
            viewModel =viewModel,
            navController = rememberNavController(),
            completionMessage = mutableStateOf("")
        )
    }
}



