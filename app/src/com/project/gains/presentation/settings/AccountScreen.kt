package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.SelectEvent
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
    navController: NavController
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val data by viewModel.data.observeAsState()
    var flag = remember { mutableStateOf(false) }
    var notification = remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(userProfile?.displayName ?: "New Name") }
    var newEmail by remember { mutableStateOf(userProfile?.email ?: "New Email") }
    var newPassword by remember { mutableStateOf("New Password") }
    var showDialog = remember { mutableStateOf(false) }
    var showDialogComplete = remember { mutableStateOf(false) }

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    message = "Account Settings",
                    button = {
                    },
                    button1 = {
                        BackButton {
                            showDialog.value = false
                            navController.popBackStack()
                        }
                    }
                )
                if (notification.value) {
                    NotificationCard(message = "Notification", onClose = { notification.value = false })
                }
            },
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
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text(text = "Edit Profile", color = MaterialTheme.colorScheme.surface)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Logout Button
                        Button(
                            onClick = { signOutHandler(SignOutEvent.SignOut) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onError)
                        ) {
                            Text(text = "Logout", color = MaterialTheme.colorScheme.onSurface)
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
                        onDismiss = { showDialog.value = false }
                    )
                }

                if (showDialogComplete.value) {

                    FeedbackAlertDialog(
                        title = "You have successfully Updated your profile!",
                        message = "",
                        onDismissRequest = {
                        },
                        onConfirm = {
                            showDialogComplete.value=false
                        },
                        confirmButtonText = "Ok",
                        dismissButtonText = "",
                        color = MaterialTheme.colorScheme.onError,
                        show = showDialogComplete
                    )
                }
            }
        }
    }
}

@Composable
fun EditProfileDialog(
    newName: String,
    newEmail: String,
    newPassword: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Edit Profile", style = MaterialTheme.typography.headlineMedium)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = newName,
                    onValueChange = onNameChange,
                    label = { Text("New Name") },
                    shape = RoundedCornerShape(size = 20.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = newEmail,
                    onValueChange = onEmailChange,
                    label = { Text("New Email") },
                    shape = RoundedCornerShape(size = 20.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = onPasswordChange,
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(size = 20.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save", color = MaterialTheme.colorScheme.secondary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onError)
            }
        }
    )
}





@Composable
@Preview
fun PreviewAccount(){
    val viewModel:SettingsViewModel = hiltViewModel()
    GainsAppTheme {
        AccountScreen(settingsHandler = {}, signOutHandler = {}, viewModel =viewModel , navController = rememberNavController())
    }
}



