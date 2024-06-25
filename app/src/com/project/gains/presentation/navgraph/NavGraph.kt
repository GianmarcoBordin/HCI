package com.project.gains.presentation.navgraph

import ExerciseTypeScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.project.gains.GeneralViewModel
import com.project.gains.presentation.ExerciseDetailsScreen
import com.project.gains.presentation.GainsHomeScreen

import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.screens.SignInScreen
import com.project.gains.presentation.authentication.screens.SignUpScreen

import com.project.gains.presentation.onboarding.OnBoardingScreen
import com.project.gains.presentation.onboarding.OnBoardingViewModel

import com.project.gains.presentation.settings.SettingsScreen
import com.project.gains.presentation.settings.SettingsViewModel
import com.project.gains.presentation.MainViewModel
import com.project.gains.presentation.TypedExerciseScreen
import com.project.gains.presentation.explore.FeedScreen
import com.project.gains.presentation.plan.NewPlanScreen
import com.project.gains.presentation.plan.PlanScreen
import com.project.gains.presentation.progress.ProgressDetailsScreen
import com.project.gains.presentation.progress.ProgressScreen
import com.project.gains.presentation.settings.SettingScreen
import com.project.gains.presentation.share.ShareScreen
import com.project.gains.presentation.workout.SessionScreen
import com.project.gains.presentation.workout.WorkoutScreen


@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    val authenticationViewModel : AuthenticationViewModel = init()
    val generalViewModel : GeneralViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination){
        // construct a nested nav graph
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = startDestination
        ) {
            composable(
                route = Route.SignUpScreen.route
            ) {
                // set screen as the node state
                SignUpScreen(signInHandler = authenticationViewModel::onSignUpEvent, viewModel = authenticationViewModel, navController = navController)
            }
            composable(
                route = Route.SettingsScreen.route
            ) {
                // set screen as the node state
                val viewModel : SettingsViewModel = hiltViewModel()
                SettingsScreen(settingsHandler = viewModel::onUpdateEvent, signOutHandler = viewModel::onSignOutEvent, viewModel = viewModel, navController = navController)
            }
            composable(
                route = Route.SettingScreen.route
            ) {
                // set screen as the node state
                SettingScreen(navController = navController, linkHandler = generalViewModel::onLinkAppEvent, saveLinkHandler = generalViewModel::onSaveSharingPreferencesEvent,
                    generalViewModel = generalViewModel
                )
            }
            composable(
                route = Route.WorkoutScreen.route
            ) {
                // set screen as the node state
                WorkoutScreen(navController = navController, deleteHandler = generalViewModel::onDeleteEvent, selectHandler = generalViewModel::onSelectEvent,
                    generalViewModel = generalViewModel
                )
            }
            composable(
                route = Route.SessionScreen.route
            ) {
                // set screen as the node state
                SessionScreen(navController
                )
            }
            composable(
                route = Route.FeedScreen.route
            ) {
                // set screen as the node state
                FeedScreen(
                    navController = navController,
                    shareHandler = generalViewModel::onShareContentEvent,
                    generalViewModel = generalViewModel

                )
            }
            composable(
                route = Route.PlanScreen.route
            ) {
                // set screen as the node state
                PlanScreen(navController = navController, deleteHandler = generalViewModel::onDeleteEvent, selectHandler = generalViewModel::onSelectEvent,
                    generalViewModel = generalViewModel
                )
            }

            composable(
                route = Route.NewPlanScreen.route
            ) {
                // set screen as the node state
                NewPlanScreen(navController =navController, createHandler = generalViewModel::onCreateEvent )
            }
            composable(
                route = Route.ProgressScreen.route
            ) {
                // set screen as the node state
                ProgressScreen(navController =navController, selectHandler = generalViewModel::onSelectEvent,                    generalViewModel = generalViewModel
                )
            }
            composable(
                route = Route.ProgressDetailsScreen.route
            ) {
                // set screen as the node state
                ProgressDetailsScreen(navController = navController, shareHandler = generalViewModel::onShareContentEvent, generalViewModel)
            }
            composable(
                route = Route.ExerciseDetailsScreen.route
            ) {
                // set screen as the node state
                ExerciseDetailsScreen(navController = navController,generalViewModel)
            }
            composable(
                route = Route.ExerciseTypeScreen.route
            ) {
                // set screen as the node state
                ExerciseTypeScreen(navController,generalViewModel::onSelectEvent)
            }
            composable(
                route = Route.TypedExerciseScreen.route
            ) {
                // set screen as the node state
                TypedExerciseScreen(navController = navController, selectHandler = generalViewModel::onSelectEvent, deleteHandler = generalViewModel::onDeleteEvent,
                    createHandler = generalViewModel::onCreateEvent, generalViewModel = generalViewModel)            }
            composable(
                route = Route.ShareScreen.route
            ) {
                // set screen as the node state
                ShareScreen(
                    navController = navController,
                    selectHandler = generalViewModel::onSelectEvent,
                    shareHandler = generalViewModel::onShareContentEvent,
                    generalViewModel = generalViewModel
                )
            }




            // more nodes...
        }
        // a node of the graph
        composable(
            route = Route.OnBoardingScreen.route
        ) {
            // set screen as the node state
            val viewModel : OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(
                event = viewModel::onBoardingEvent,navController
            )
        }
        composable(
            route = Route.SignInScreen.route
        ) {
            SignInScreen(signInHandler = authenticationViewModel::onSignInEvent, viewModel = authenticationViewModel,
                navController = navController)
        }
        composable(
            route = Route.HomeScreen.route
        ) {
            // set screen as the node state
            val  viewModel : MainViewModel = hiltViewModel()
            GainsHomeScreen(
                navController = navController,
                viewModel = viewModel,
                generalViewModel = generalViewModel,
                selectHandler=generalViewModel::onSelectEvent
            )
        }
    }
}

@Composable
private fun init(): AuthenticationViewModel {
    return hiltViewModel()
}