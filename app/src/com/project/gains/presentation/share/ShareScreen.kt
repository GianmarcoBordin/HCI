package com.project.gains.presentation.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.SharingMediaIcon
import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.settings.ShareContentViewModel


@Composable
fun ShareScreen(
    navController: NavController,
    shareContentViewModel: ShareContentViewModel
) {
    var clickedApp by remember { mutableIntStateOf(1) }
    var clickedMedia by remember { mutableStateOf(Icons.Default.Home) }
    val showDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }


    val sharingMedia by shareContentViewModel.linkedSharingMedia.observeAsState()
    val apps by shareContentViewModel.linkedApps.observeAsState()


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(20.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 290.dp),
                        horizontalArrangement = Arrangement.Center) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Close , contentDescription = "Close Icon")
                        }
                    }
                }

                item {
                    Text(
                        text = "Share Your Content!",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                item {
                    Text(
                        text = "Share Your Content with your friends showing your progress, your workouts and your plans",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                item { Spacer(modifier = Modifier.height(10.dp)) }

                item {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
                        apps?.forEach{ app ->
                            SocialMediaIcon(
                                icon = app,
                                onClick = {
                                    clickedApp = app
                                },
                                isSelected = clickedApp == app
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                        sharingMedia?.forEach { media ->
                            SharingMediaIcon(
                                icon = media,
                                onClick = {
                                    clickedMedia = media
                                },
                                isSelected = clickedMedia == media
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }

                item {
                    if (apps?.isEmpty() == true) {
                        Text(
                            text = "You have no linked apps to link an app go to settings -> sharing preferences or click the link apps button below",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                navController.navigate(Route.LinkedSocialSettingScreen.route)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                        ) {
                            Text(text = "LINK APPS")
                        }

                    } else if (apps?.isEmpty() == false) {
                        Button(
                            onClick = {
                                showDialog.value=true

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                        ) {
                            Text(text = "SHARE CONTENT")
                        }
                    } else {

                    }
                }
                item {
                    if (showDialog.value) {
                        FeedbackAlertDialog(
                            title =  "You have successfully shared your content!",
                            onDismissRequest = { showDialog.value = false
                                navController.navigate(Route.HomeScreen.route)

                            },
                            onConfirm = {
                                showDialog.value = false
                                navController.navigate(Route.HomeScreen.route)
                            },
                            show = showDialog

                        )
                    }
                    if (showErrorDialog.value) {
                        FeedbackAlertDialog(
                            title = "An error has occurred,check your connection and retry later!",
                            onDismissRequest = {
                                showErrorDialog.value = false

                            },
                            onConfirm = {
                                showErrorDialog.value = false

                            },
                            show = showErrorDialog

                        )
                    }
                }
            }
        }
    }