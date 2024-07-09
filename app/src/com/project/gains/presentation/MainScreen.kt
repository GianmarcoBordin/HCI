package com.project.gains.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview


import com.project.gains.presentation.components.DynamicBottomBar
import com.project.gains.presentation.components.DynamicTopBar
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.exercises.ExerciseViewModel

import com.project.gains.presentation.navgraph.NavGraph
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GOrange
import com.project.gains.theme.GainsAppTheme


@Composable
fun MainScreen(startDestination: String, searchViewModel: SearchViewModel,exerciseViewModel: ExerciseViewModel,workoutViewModel: WorkoutViewModel) {
    val navController = rememberNavController()

    val searchWidgetState by searchViewModel.searchWidgetState
    val searchTextState by searchViewModel.searchTextState


    GainsAppTheme {
        Scaffold(
            topBar = { DynamicTopBar(navController = navController, addFavouriteExerciseHandler =exerciseViewModel::onExerciseEvent, addFavouriteWorkoutHandler = workoutViewModel::onManageWorkoutEvent ) },
            bottomBar = {
                    DynamicBottomBar(navController = navController)
              },
        ) {
                paddingValues ->
            NavGraph(
                startDestination = startDestination,
                navController = navController,
                exerciseViewModel=exerciseViewModel,
                workoutViewModel=workoutViewModel,
                paddingValues = paddingValues)
        }
    }


}