package com.project.gains.presentation.exercises

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.project.gains.data.Exercise
import com.project.gains.presentation.components.AddExerciseItem
import com.project.gains.presentation.components.BackButton
import com.project.gains.presentation.components.NotificationCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypedExerciseScreen(
    navController: NavController,
    selectExerciseHandler: (ExerciseEvent) -> Unit,
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel
    ) {
    val isToAdd by exerciseViewModel.isToAdd.observeAsState()
    val previousPage by exerciseViewModel.previousPage.observeAsState()
    val allExercises by workoutViewModel.exercises.observeAsState()


    val searchQuery = remember { mutableStateOf("") }
    val searchedExercises = remember { mutableStateOf(listOf<Exercise>()) }
    val isSearchQueryEmpty = remember { mutableStateOf(searchQuery.value.isBlank()) }
    val localKeyboardController = LocalSoftwareKeyboardController.current
    val notification = remember {
        mutableStateOf(false)
    }

    GainsAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    message = "Exercises",
                    button = {}
                ) {
                    BackButton {
                        navController.popBackStack()
                    }
                }
                if (notification.value){
                    NotificationCard(message ="Notification", onClose = {notification.value=false})
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    item {
                        // Remember if the search query is empty
                        TextField(
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.DarkGray,
                                cursorColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                                focusedLabelColor = MaterialTheme.colorScheme.surface,
                                unfocusedLabelColor = MaterialTheme.colorScheme.surface
                            ),
                            value = searchQuery.value,
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
                            label = {
                                if (isSearchQueryEmpty.value) {
                                    Text("Search Exercise", color = MaterialTheme.colorScheme.surface)
                                }
                            },
                            leadingIcon = {
                                IconButton(
                                    onClick = {
                                        searchedExercises.value = allExercises?.filter {
                                            it.name.contains(searchQuery.value, ignoreCase = true)
                                        } ?: listOf()
                                        localKeyboardController?.hide()
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search Icon",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    searchedExercises.value = allExercises?.filter {
                                        it.name.contains(searchQuery.value, ignoreCase = true)
                                    } ?: listOf()
                                    localKeyboardController?.hide()
                                }
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }

                    items(searchedExercises.value) { exercise ->
                        AddExerciseItem(
                            exercise = exercise,
                            onItemClick = { exerciseToAdd ->
                                searchedExercises.value = searchedExercises.value.toMutableList().apply {
                                    add(exerciseToAdd)
                                }
                                selectExerciseHandler(ExerciseEvent.SelectExercise(exercise))
                                navController.navigate(Route.ExerciseDetailsScreen.route)
                            },
                            onItemClick2 = {
                                selectExerciseHandler(ExerciseEvent.SelectExerciseToAdd(exercise))
                                // TODO review according to add plan and manual workout
                                /*selectHandler(SelectEvent.SelectPlanPopup(true))
                                selectHandler(SelectEvent.SelectClicked(true))
                                selectHandler(SelectEvent.SelectShowPopup3(false))
                                selectHandler(SelectEvent.SelectShowPopup4(true))*/
                                if (previousPage=="Home"){
                                    navController.navigate(Route.HomeScreen.route)
                                } else{
                                    navController.navigate(Route.PlanScreen.route)

                                }
                            },
                            isSelected = true,
                            isToAdd = isToAdd ?: false,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

/*AddExerciseItem(exercise = exercise, onItemClick = {onItemClick()}, onItemClick2 = {
    selectedExercises.add(exercise)
    onItemClick2()},isSelected = false,isToAdd = true)*/


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    GainsAppTheme {
        TypedExerciseScreen(
            navController = rememberNavController(),
            selectExerciseHandler = {},
            workoutViewModel = workoutViewModel,
            exerciseViewModel = exerciseViewModel
        )
    }
}