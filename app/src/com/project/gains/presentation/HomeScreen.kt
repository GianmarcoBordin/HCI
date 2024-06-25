package com.project.gains.presentation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape

//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Event

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.project.gains.GeneralViewModel
import com.project.gains.R
import com.project.gains.data.Exercise
import com.project.gains.data.ExerciseType
import com.project.gains.data.TrainingType

import com.project.gains.presentation.components.BottomNavigationBar
import com.project.gains.presentation.components.ExerciseGif
import com.project.gains.presentation.components.GeneralCard
import com.project.gains.presentation.components.TopBar
import com.project.gains.presentation.events.SelectEvent

import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme


@Composable
fun GainsHomeScreen(
    navController: NavController,
    viewModel: MainViewModel,
    generalViewModel: GeneralViewModel,
    selectHandler: (SelectEvent) -> Unit,
) {
    val openPopup = remember { mutableStateOf(false) }
    val workouts by generalViewModel.workouts.observeAsState()
    val plans by generalViewModel.plans.observeAsState()
    val plots by generalViewModel.plots.observeAsState()

    CustomBackHandler(
        onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            ?: return,
        enabled = true
    ) {
        openPopup.value = true
    }


   GainsAppTheme {

       Scaffold(
           topBar = {
               TopBar(navController = navController, message = "Gains")
           },
           bottomBar = { BottomNavigationBar(navController = navController) }
       ) { paddingValues ->
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(paddingValues)
           ) {
               LazyColumn(
                   modifier = Modifier
                       .fillMaxSize(),
                   verticalArrangement = Arrangement.Center

               ) {
                   workouts?.forEach{ workout ->
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = workout.name){
                               navController.navigate(Route.ExerciseTypeScreen.route)
                           }
                       }
                   }
                   plans?.forEach{ plan ->
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = plan.name){
                               selectHandler(SelectEvent.SelectPlan(plan))
                               navController.navigate(Route.PlanScreen.route)
                           }
                       }
                   }
                   plots?.forEach{ plot ->
                       item {
                           GeneralCard(imageResId = R.drawable.pexels1, title = "plot"){
                               selectHandler(SelectEvent.SelectPlotPreview(plot.preview))
                               navController.navigate(Route.ProgressDetailsScreen.route)
                           }
                       }
                   }
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

    androidx.activity.compose.LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(backCallback)

    DisposableEffect(onBackPressedDispatcher) {
        backCallback.isEnabled = enabled
        onDispose {
            backCallback.remove()
        }
    }
}





