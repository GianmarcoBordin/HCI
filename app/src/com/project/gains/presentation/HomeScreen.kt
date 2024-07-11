package com.project.gains.presentation

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.gains.data.Exercise
import com.project.gains.presentation.exercises.ExerciseViewModel
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.workout.WorkoutViewModel
import com.project.gains.theme.GainsAppTheme
import com.project.gains.util.currentWeekday

@Composable
fun HomeScreen(
    navController: NavController,
    workoutViewModel: WorkoutViewModel,
    paddingValues: PaddingValues,
    exerciseViewModel: ExerciseViewModel
) {

    val openPopup = remember { mutableStateOf(false) }
    val favouriteExercises by exerciseViewModel.favouriteExercises.observeAsState()

    val workouts by workoutViewModel.workouts.observeAsState()

    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            ?: return,
        enabled = true
    ) {
        openPopup.value = true
    }

    GainsAppTheme {
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top

            ) {

                val weekday = currentWeekday()

                workouts?.forEach { workout ->
                    if (workout.workoutDay.ordinal == weekday) {
                        item {
                            HorizontalScrollScreen(navController, "daily workout", workout.exercises)
                        }
                    }
                }

                item {
                    HorizontalScrollScreen(navController, "favourite exercises", favouriteExercises!!.toList())
                }
            }
        }
    }
}

@Composable
fun CustomBackHandler(
    onBackPressedDispatcher: OnBackPressedDispatcher,
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(backCallback)

    DisposableEffect(onBackPressedDispatcher) {
        backCallback.isEnabled = enabled
        onDispose {
            backCallback.remove()
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HorizontalScrollScreen(navController: NavController, title: String, items: List<Exercise>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(180.dp)
    ) {
        // BowWithConstraints will provide the maxWidth used below
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (items.isEmpty()) {
                    Text(
                        text = "No $title",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 10.dp)
                    )
                } else {
                    Text(
                        text = "Your $title",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                        color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 10.dp)
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = rememberLazyListState()
                    ) {
                        itemsIndexed(items) {  _, item ->
                            item.gifResId?.let {
                                Card(imageResId = it, title = item.name) {
                                    navController.navigate(Route.ExerciseDetailsScreen.route)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Card(imageResId: Int, title: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clickable {
                onItemClick()
            }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 20.dp)
                .clip(RoundedCornerShape(16.dp)) // Clip to the rounded corners
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}