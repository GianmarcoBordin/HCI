package com.project.gains.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.R

import com.project.gains.presentation.components.BackButton

import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.LogoUser
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.SocialMediaIcon

import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.LinkAppEvent
import com.project.gains.presentation.events.ManageDataStoreEvent
import com.project.gains.presentation.navgraph.Route



import com.project.gains.theme.GainsAppTheme


@SuppressLint("MutableCollectionMutableState")
@Composable
fun LinkedSocialSettingScreen(
    navController: NavController,
    linkHandler: (LinkAppEvent) -> Unit,
    saveLinkHandler: (ManageDataStoreEvent) -> Unit,
    shareContentViewModel: ShareContentViewModel
) {
    val linkedApps by shareContentViewModel.linkedApps.observeAsState()
    val clickedApps = remember { mutableStateOf(mutableListOf<Int>()) }
    val notification = remember {
        mutableStateOf(false)
    }

    val showDialog = remember { mutableStateOf(false) }
    val icons = listOf(
        R.drawable.instagram_icon,
        R.drawable.facebook_icon,
        R.drawable.x_logo_icon,
        R.drawable.drive_google_icon,
        R.drawable.spotify_icon,
        R.drawable.tiktok_logo_icon
    )


    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    icons.forEach { icon ->
                        SocialMediaRow(
                            icon = icon,
                            isLinked = linkedApps?.contains(icon) == true,
                            linkHandler = linkHandler,
                            clickedApps = clickedApps
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick =
                            {
                                showDialog.value = true
                                saveLinkHandler(
                                    ManageDataStoreEvent.Save(
                                        linkedApps ?: mutableListOf()
                                    )
                                )
                            },
                            modifier = Modifier.size(60.dp),
                            colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save Icon",
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(10.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    } }
                item {
                    if (showDialog.value) {
                        FeedbackAlertDialog(
                            title =  "You have successfully updated your preferences!",
                            onDismissRequest = { showDialog.value = false },
                            onConfirm = {
                                showDialog.value = false
                            },
                            show = showDialog

                        )
                    }
                }
            }

        }
    }
}

@Composable
fun SocialMediaRow(
    icon: Int,
    isLinked: Boolean,
    linkHandler: (LinkAppEvent) -> Unit,
    clickedApps: MutableState<MutableList<Int>>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SocialMediaIcon(icon = icon, onClick = {  }, isSelected = isLinked)
        Spacer(modifier = Modifier.width(50.dp))
        if (isLinked || clickedApps.value.contains(icon)) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Linked Icon",
                modifier = Modifier.padding(20.dp)
            )
        } else if (!clickedApps.value.contains(icon)){
            IconButton(
                onClick =
                {
                    clickedApps.value = clickedApps.value.toMutableList().apply { add(icon) }
                    linkHandler(LinkAppEvent.LinkApp(icon)) },
                modifier = Modifier.size(60.dp),
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    val navController = rememberNavController()
    val shareContentViewModel : ShareContentViewModel = hiltViewModel()
    LinkedSocialSettingScreen(
        navController = navController,
        saveLinkHandler = {  },
        linkHandler = {},
        shareContentViewModel = shareContentViewModel
    )
}
