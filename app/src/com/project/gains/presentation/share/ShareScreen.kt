package com.project.gains.presentation.share

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.R
import com.project.gains.presentation.MainViewModel
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.components.SharingMediaIcon
import com.project.gains.presentation.components.SocialMediaIcon
import com.project.gains.presentation.components.getPreviousDestination
import com.project.gains.presentation.exercises.ExerciseViewModel
import com.project.gains.presentation.explore.events.SearchEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.util.getNameByRes
import com.project.gains.util.getResByName


@Composable
fun ShareScreen(
    navController: NavController,
    shareContentViewModel: ShareContentViewModel,
    workoutViewModel: WorkoutViewModel,
    shareHandler: (SearchEvent.GymPostWorkoutEvent) -> Unit,
    shareHandlerExercise: (SearchEvent.GymPostExerciseEvent) -> Unit,
    shareHandlerProgress: (SearchEvent.GymPostProgressEvent) -> Unit,
    mainViewModel: MainViewModel,
    exerciseViewModel: ExerciseViewModel,
    completionMessage: MutableState<String>
) {
    var clickedApp = remember { mutableIntStateOf(1) }
    var clickedMedia by remember { mutableStateOf(Icons.Default.Home) }
    val showDialog = remember { mutableStateOf(false) }

    val showErrorDialog = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val workout by workoutViewModel.selectedWorkout.observeAsState()
    val exercise by exerciseViewModel.selectedExercise.observeAsState()

    val sharingMedia by shareContentViewModel.linkedSharingMedia.observeAsState()
    val apps by shareContentViewModel.linkedApps.observeAsState()
    val username by mainViewModel.userProfile.observeAsState()
    val appName = remember {
        mutableStateOf("")
    }
    val prevDest = getPreviousDestination(navController = navController)
    var test = remember {
        mutableStateOf(true)
    }
    val testAppnames = remember {
        mutableStateOf(apps?.map { getNameByRes(it) } ?: listOf())
    }





    if (showDialog.value) {

        FeedbackAlertDialog(
            onDismissRequest = {

            },
            onConfirm = {
                showDialog.value=false

            },
            title = "Content Shared!",
            text = "You have correctly shared your content trough ${appName.value}",
            icon = Icons.Default.Check,
            dismiss = false
        )
    }


    if (showErrorDialog.value) {
        FeedbackAlertDialog(
            onDismissRequest = {

            },
            onConfirm = {
                showErrorDialog.value = false
            },
            title = "Error Occurred",
            text = "You have to select an app to share the content",
            icon = Icons.Default.Error,
            dismiss = false
        )
    }

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
                Text(
                    text = "Share your content!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

            }
            item { Spacer(modifier = Modifier.height(10.dp)) }


            item {
                Text(
                    text = "Share your content with your friends showing your progress, your workouts ",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(8.dp)) {
                    if (!test.value){
                        apps?.forEach { app ->
                            if (app!= R.drawable.spotify_icon) {
                                SocialMediaIcon(
                                    icon = app,
                                    onClick = {
                                        clickedApp.intValue = app
                                    },
                                    isSelected = clickedApp.intValue == app
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                            }
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
                    }else{
                        RadioButtonList(names = testAppnames.value, clickedApp =clickedApp)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
                if (apps?.isEmpty() == true ) {
                    Text(
                        text = "You have no linked apps to link an app go to settings -> sharing preferences or click the link apps button below",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.background(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.secondaryContainer).padding(16.dp)
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
                        Text(text = "Link apps")
                    }
                } else  {
                    Button(
                        onClick = {
                            if (prevDest == Route.WorkoutScreen.route){
                                workout?.let { SearchEvent.GymPostWorkoutEvent(it,clickedApp.intValue,username?.displayName ?: "user 2") }
                                    ?.let { shareHandler(it) }
                            } else if (prevDest == Route.ExerciseDetailsScreen.route){
                                exercise?.let { SearchEvent.GymPostExerciseEvent(it,clickedApp.intValue,username?.displayName ?: "user 2") }?.let { shareHandlerExercise(it) }
                            }

                            if (clickedApp.intValue == R.drawable.instagram_icon){
                                appName.value = "Instagram"
                                showDialog.value = true

                            }
                            else if (clickedApp.intValue == R.drawable.x_logo_icon){
                                appName.value = "X"
                                showDialog.value = true


                            }
                            else if (clickedApp.intValue == R.drawable.facebook_icon){
                                appName.value = "Facebook"
                                showDialog.value = true


                            }
                            else if (clickedApp.intValue == R.drawable.tiktok_logo_icon){
                                appName.value = "TikTok"
                                showDialog.value = true


                            }else if (!test.value){
                                showErrorDialog.value = true

                            }
                            else{
                                appName.value = "this social"
                                showDialog.value = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                    ) {
                        Text(text =   "Share content")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    val text = AnnotatedString.Builder().apply {
                        pushStringAnnotation(
                            tag = "LINK",
                            annotation = "destination_page"
                        )
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.tertiary,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Share via e-mail")
                        }
                        pop()
                    }.toAnnotatedString()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ClickableText(
                            text = text,
                            onClick = { offset ->
                                appName.value="Email"
                                showDialog.value = true

                            },
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun RadioButtonList(names: List<String>, clickedApp: MutableState<Int>) {
    // State to keep track of the selected name
    var selectedName by remember { mutableStateOf<String?>(null) }

    Column {
        names.forEach { name ->
            Row( verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .selectable(
                        selected = (name == selectedName),
                        onClick = {
                            selectedName = name
                            clickedApp.value = getResByName(appName = selectedName ?: "")
                        },
                        role = Role.RadioButton
                    )
            ) {
                RadioButton(
                    selected = (name == selectedName),
                    onClick = {selectedName = name
                        clickedApp.value= getResByName(appName = selectedName ?: "")
                    }
                )
                Text(
                    text = name,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
