package com.project.gains.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
import com.project.gains.data.Exercise
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.SearchAppBar
import com.project.gains.presentation.exercises.ExerciseViewModel
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManageExercises
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme

@Composable
fun HomeSearchScreen(
    navController: NavController,
    workoutViewModel: WorkoutViewModel,
    selectExerciseHandler: (ExerciseEvent.SelectExercise) -> Unit,
) {
    val allExercises by workoutViewModel.exercises.observeAsState()

    val searchQuery = remember { mutableStateOf("") }
    val searchedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    val isSearchQueryEmpty = remember { mutableStateOf(searchQuery.value.isBlank()) }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                item {
                    SearchAppBar(
                        value = searchQuery.value,
                        placeholder = "Look for exercises",
                        onValueChange = { query ->
                            searchQuery.value = query
                            isSearchQueryEmpty.value = query.isBlank()
                            searchedExercises.value = if (query.isNotBlank()) {
                                allExercises?.filter {
                                    it.name.contains(query, ignoreCase = true)
                                } ?: listOf()
                            } else {
                                listOf()
                            }

                        },
                        onCloseClicked = {},
                        onSearchClicked = {},
                        // this is empty because it is used for a different purpose
                        onClick = {},
                        enabled = true
                    )
                }

                items(searchedExercises.value) { exercise ->
                    AddExerciseItem(
                        exercise = exercise,
                        onItemClick = { exerciseToAdd ->
                            searchedExercises.value =
                                searchedExercises.value.toMutableList().apply {
                                    add(exerciseToAdd)
                                }
                            selectExerciseHandler(ExerciseEvent.SelectExercise(exercise))
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
    }
}