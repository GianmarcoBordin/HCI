package com.project.gains.presentation.navgraph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.project.gains.presentation.exercises.ExerciseDetailsScreen
import com.project.gains.presentation.HomeScreen

import com.project.gains.presentation.authentication.AuthenticationViewModel
import com.project.gains.presentation.authentication.screens.ForgotPasswordScreen
import com.project.gains.presentation.authentication.screens.OTPScreen
import com.project.gains.presentation.authentication.screens.SignInScreen
import com.project.gains.presentation.authentication.screens.SignUpScreen
import com.project.gains.presentation.components.SearchViewModel
import com.project.gains.presentation.components.slideInToLeft
import com.project.gains.presentation.components.slideInToRight
import com.project.gains.presentation.components.slideOutToLeft
import com.project.gains.presentation.components.slideOutToRight
import com.project.gains.presentation.exercises.ExerciseViewModel

import com.project.gains.presentation.onboarding.OnBoardingScreen
import com.project.gains.presentation.onboarding.OnBoardingViewModel

import com.project.gains.presentation.settings.SettingsScreen
import com.project.gains.presentation.settings.SettingsViewModel
import com.project.gains.presentation.settings.ShareContentViewModel
import com.project.gains.presentation.exercises.TypedExerciseScreen
import com.project.gains.presentation.workout.WorkoutModeScreen
import com.project.gains.presentation.explore.FeedScreen
import com.project.gains.presentation.explore.FeedViewModel
import com.project.gains.presentation.explore.SearchScreen
import com.project.gains.presentation.plan.AddGeneratedPlan
import com.project.gains.presentation.plan.AddManualWorkout
import com.project.gains.presentation.plan.LastNewPlanScreen
import com.project.gains.presentation.plan.ManualWorkoutViewModel
import com.project.gains.presentation.plan.NewPlanScreen
import com.project.gains.presentation.plan.PlanScreen
import com.project.gains.presentation.plan.PlanViewModel
import com.project.gains.presentation.progress.ProgressDetailsScreen
import com.project.gains.presentation.settings.AccountScreen
import com.project.gains.presentation.settings.LinkedSocialSettingScreen
import com.project.gains.presentation.share.ShareScreen
import com.project.gains.presentation.workout.WorkoutScreen
import com.project.gains.presentation.workout.WorkoutViewModel

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    val authenticationViewModel : AuthenticationViewModel = init()
    val shareContentViewModel : ShareContentViewModel = hiltViewModel()
    val planViewModel : PlanViewModel = hiltViewModel()
    val manualWorkoutViewModel: ManualWorkoutViewModel = hiltViewModel()
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val feedViewModel : FeedViewModel = hiltViewModel()


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
        ){
        // construct a nested nav graph
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = startDestination
        ) {
            composable(
                route = Route.SignUpScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,

            ) {
                // set screen as the node state
                SignUpScreen(signInHandler = authenticationViewModel::onSignUpEvent, viewModel = authenticationViewModel, navController = navController)
            }
            composable(
                route = Route.AccountScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                val viewModel : SettingsViewModel = hiltViewModel()
                AccountScreen(
                    settingsHandler = viewModel::onUpdateEvent,
                    signOutHandler =viewModel::onSignOutEvent ,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = Route.SettingsScreen.route,
                popEnterTransition = ::slideInToRight,
            ) {
                // set screen as the node state
                SettingsScreen(navController = navController)
            }
            composable(
                route = Route.LinkedSocialSettingScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                LinkedSocialSettingScreen(
                    navController = navController,
                    linkHandler = shareContentViewModel::onLinkAppEvent,
                    saveLinkHandler = shareContentViewModel::onSaveSharingPreferencesEvent,
                    shareContentViewModel = shareContentViewModel
                )
            }
            composable(
                route = Route.WorkoutScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                WorkoutScreen(
                    navController = navController,
                    shareHandler = shareContentViewModel::onManageDialogEvent,
                    exerciseHandler = exerciseViewModel::onExerciseEvent,
                    workoutViewModel = workoutViewModel,
                    shareContentViewModel = shareContentViewModel,
                    addFavouriteWorkoutHandler = workoutViewModel::onManageWorkoutEvent,
                    removeFavouriteWorkoutHandler = workoutViewModel::onManageWorkoutEvent
                )
            }

            composable(
                route = Route.FeedScreen.route,
                popEnterTransition = ::slideInToRight,

            ) {
                // set screen as the node state
                FeedScreen(
                    navController = navController,
                    feedViewModel =feedViewModel
                )
            }
            composable(
                route = Route.SearchScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                val searchViewModel : SearchViewModel = hiltViewModel()
                // set screen as the node state
                SearchScreen(
                    searchViewModel = searchViewModel,
                    searchGymPostHandler = feedViewModel::onSearchEvent,
                    assignCategoryHandler = searchViewModel::onCategoriesEvent,
                    navController = navController
                )
            }
            composable(
                route = Route.PlanScreen.route,
                popEnterTransition = ::slideInToRight,
            ) {
                // set screen as the node state
                PlanScreen(navController = navController,
                    planViewModel = planViewModel,
                    selectPlanHandler = planViewModel::onCreatePlanEvent
                )
            }
            composable(
                route = Route.NewPlanScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight)
            {
                // set screen as the node state
                NewPlanScreen(
                    navController = navController
                )
            }
            composable(
                route = Route.LastNewPlanScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                LastNewPlanScreen(navController = navController,planViewModel::onCreatePlanEvent)
            }
            composable(
                route = Route.AddGeneratedPlanScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                AddGeneratedPlan(navController = navController,
                    planOptionsHandler = planViewModel::onCreatePlanEvent
                )
            }
            composable(
                route = Route.AddManualWorkoutScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                AddManualWorkout(navController = navController,
                    paddingValues = paddingValues,
                    manualWorkoutViewModel = manualWorkoutViewModel,
                    addNameHandler =  manualWorkoutViewModel::onManageExercisesEvent,
                    deleteExerciseHandler = manualWorkoutViewModel::onManageExercisesEvent,
                    selectExerciseHandler = exerciseViewModel::onExerciseEvent,
                    createWorkoutHandler = workoutViewModel::onManageWorkoutEvent)
            }
            composable(
                route = Route.ShareScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                ShareScreen(
                    navController =navController ,
                    shareContentViewModel = shareContentViewModel
                )
            }
            composable(
                route = Route.ProgressDetailsScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                ProgressDetailsScreen(
                    navController = navController,
                    shareContentViewModel = shareContentViewModel
                )
            }
            composable(
                route = Route.ExerciseDetailsScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                ExerciseDetailsScreen(
                    navController=navController,
                    addFavouriteExerciseHandler = exerciseViewModel::onExerciseEvent,
                    exerciseViewModel = exerciseViewModel,
                    removeFavouriteExerciseHandler = exerciseViewModel::onExerciseEvent
                )
            }
            composable(
                route = Route.WorkoutModeScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                WorkoutModeScreen(
                    navController, workoutViewModel::onMusicEvent, workoutViewModel = workoutViewModel
                )
            }
            composable(
                route = Route.TypedExerciseScreen.route,
                enterTransition = ::slideInToLeft,
                exitTransition = ::slideOutToLeft,
                popEnterTransition = ::slideInToRight,
                popExitTransition = ::slideOutToRight
            ) {
                // set screen as the node state
                TypedExerciseScreen(
                    navController = navController,
                    paddingValues = paddingValues,
                    selectExerciseHandler = exerciseViewModel::onExerciseEvent,
                    workoutViewModel = workoutViewModel,
                    exerciseViewModel = exerciseViewModel,
                    addExerciseHandler = manualWorkoutViewModel::onManageExercisesEvent,
                    removeExerciseHandler = manualWorkoutViewModel::onManageExercisesEvent
                )
            }

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
            route = Route.SignInScreen.route,
            enterTransition = ::slideInToRight,
            exitTransition = ::slideOutToLeft,
        ) {
            SignInScreen(signInHandler = authenticationViewModel::onSignInEvent, viewModel = authenticationViewModel,
                navController = navController)
        }
        composable(
            route = Route.ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen (
                onSendClicked = { navController.navigate(Route.OTPScreen.route) }
            )
        }
        composable(
            route = Route.OTPScreen.route
        ) {
            OTPScreen(
                authenticationViewModel = authenticationViewModel,
                onVerifyClicked = { navController.navigate(Route.SignInScreen.route) },
                onBackClicked = { navController.navigate(Route.ForgotPasswordScreen.route) }
            )
        }
        composable(
            route = Route.HomeScreen.route,
            popEnterTransition = ::slideInToRight,
            //popExitTransition = ::slideOutToRight

        ) {
            HomeScreen(
                navController = navController,
                workoutViewModel = workoutViewModel,
                paddingValues = paddingValues,
                exerciseViewModel = exerciseViewModel,
                selectWorkoutHandler = workoutViewModel::onManageWorkoutEvent,
                selectExerciseHandler = exerciseViewModel::onExerciseEvent
            )
        }
    }
}

@Composable
private fun init(): AuthenticationViewModel {
    return hiltViewModel()
}