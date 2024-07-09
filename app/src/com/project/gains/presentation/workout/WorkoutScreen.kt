package com.project.gains.presentation.workout


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.settings.ShareContentViewModel

import com.project.gains.presentation.components.AddExerciseItem



import com.project.gains.presentation.components.FeedbackAlertDialog


import com.project.gains.presentation.exercises.events.ExerciseEvent

import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.settings.events.ManageDialogEvent
import com.project.gains.theme.GainsAppTheme

@Composable
fun WorkoutScreen(
    navController: NavController,
    shareHandler: (ManageDialogEvent) -> Unit,
    exerciseHandler: (ExerciseEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    shareContentViewModel: ShareContentViewModel

) {
    val linkedApps by shareContentViewModel.linkedApps.observeAsState()
    val showDialogShared by shareContentViewModel.showDialogShared.observeAsState()

    // Sample list of exercises
    val workout by workoutViewModel.selectedWorkout.observeAsState()

    val showPopup2 = remember { mutableStateOf(false) }


    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center

            ) {
                workout?.exercises?.forEach { exercise ->
                    item {
                        AddExerciseItem(
                            exercise = exercise, {

                                exerciseHandler(ExerciseEvent.SelectExercise(exercise))
                                navController.navigate(Route.ExerciseDetailsScreen.route)
                            },
                            onItemClick2 = {},
                            isSelected = true,
                            isToAdd = false,
                            modifier = Modifier
                        )
                    }
                }

            }

            if (showDialogShared==true) {

                FeedbackAlertDialog(
                    title = "You have successfully Shared your content!",
                    onDismissRequest = {
                    },
                    onConfirm = {
                        shareHandler(ManageDialogEvent.SelectShowDialogShared(false))
                    },
                    show = showPopup2
                )
            }
        }



    }

}


@Preview(showBackground = true)
@Composable
fun WorkoutScreenPreview() {

    val navController = rememberNavController()
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val shareContentViewModel : ShareContentViewModel = hiltViewModel()
    WorkoutScreen(
        navController = navController,
        shareHandler = {},
        exerciseHandler = {},
        workoutViewModel = workoutViewModel,
        shareContentViewModel = shareContentViewModel
    )
}



