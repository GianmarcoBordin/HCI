package com.project.gains.presentation.settings


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.gains.presentation.Dimension.ButtonCornerShape
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.SharingPreferencesButton
import com.project.gains.presentation.navgraph.Route

import com.project.gains.presentation.settings.events.SignOutEvent
import com.project.gains.presentation.settings.events.UpdateEvent
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.Constants.SIGN_OUT_SUCCESS
import com.project.gains.util.Constants.UPDATE_SUCCESS


@Composable
fun SettingsScreen(
    settingsHandler:(UpdateEvent.Update)-> Unit,
    signOutHandler: (SignOutEvent.SignOut) -> Unit,
    viewModel: SettingsViewModel,
    navController:NavController
) {
    // observable state
    val userProfile by viewModel.userProfile.collectAsState()
    val data by viewModel.data.observeAsState()
    // field of interest
    var newName by remember { mutableStateOf(userProfile?.displayName ?: "New Name") }
    var newEmail by remember { mutableStateOf(userProfile?.email ?: "New Name") }
    var newPassword by remember { mutableStateOf("New Password") }

    GainsAppTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(
                onClick = {navController.popBackStack()}
            )
           Text(
                text = "Your Account",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 0.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            SharingPreferencesButton(navController = navController)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Name field
            OutlinedTextField(
                value = newName,
                onValueChange = { newValue ->
                    newName = newValue },
                label = { Text( "New Name", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,) },
                shape = RoundedCornerShape(size = 20.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Email field
            OutlinedTextField(
                value = newEmail,
                onValueChange = { newValue ->
                    newEmail  = newValue },
                label = { Text( "New Email", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,) },
                shape = RoundedCornerShape(size = 20.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Password field
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newValue ->
                    newPassword = newValue },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(size = 20.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer), // Set the text color to white

                label = { Text("New Password", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary, // Set the contour color when focused
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary // Set the contour color when not focused
                )
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                // logout button
                Button(
                    onClick = {
                        signOutHandler(SignOutEvent.SignOut)
                    },
                    shape = RoundedCornerShape(size = ButtonCornerShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(text = "Logout")
                }
                // Save button
                Button(
                    onClick = {
                        settingsHandler(UpdateEvent.Update(newName ,newEmail, newPassword))
                    },
                    shape = RoundedCornerShape(size = ButtonCornerShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Orange
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Text color
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(text = "Save")
                }
            }
            // Observe changes in data
            if (data?.isNotEmpty() == true) {
                // Display data

                if (data.equals(UPDATE_SUCCESS)){
                    Text(
                        text = data!!.toString(),
                        color = Color.Green
                    )
                }
                else if (!data.equals(SIGN_OUT_SUCCESS) &&  !data.equals(UPDATE_SUCCESS)){
                    Text(
                        text = data!!.toString(),
                        color = MaterialTheme.colorScheme.onError
                    )
                }


                // Change page if all ok
                if (viewModel.navigateToAnotherScreen.value == true) {
                    val route:String = if (data.equals(SIGN_OUT_SUCCESS)){
                        Route.SignInScreen.route
                    }else{
                        Route.HomeScreen.route
                    }
                    // navigate
                    navController.navigate(route)
                    viewModel.onNavigationComplete()
                }

            }
        }
    }
}








