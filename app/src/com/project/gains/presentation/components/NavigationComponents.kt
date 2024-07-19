package com.project.gains.presentation.components

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.gains.R
import com.project.gains.data.Plan
import com.project.gains.data.bottomNavItems
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent


data class MenuItem(
    val text: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController, scrollBehavior: BottomAppBarScrollBehavior? = null) {

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    BottomAppBar(
        scrollBehavior = scrollBehavior
    ) {
        val currentRoute = currentRoute(navController)
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                alwaysShowLabel = true,
                onClick = {
                    if(item.badgeCount != null) {
                        item.badgeCount = null
                    }

                    selectedItemIndex = index
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        /* popUpTo(navController.graph.startDestinationId) {
                             saveState = true
                         }*/
                        launchSingleTop = true
                        restoreState = true
                    } },
                label = {
                    Text(text = item.title)
                },
                icon = { BadgedBox(
                    badge = {
                        if(item.badgeCount != null) {
                            Badge {
                                Text(text = item.badgeCount.toString())
                            }
                        } else if(item.hasNews) {
                            Badge()
                        }
                    })
                {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
                }
            )
        }
    }
}

@Composable
fun WorkoutBottomBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(64.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                navController.navigate(Route.WorkoutModeScreen.route)
            },
            modifier = Modifier.size(50.dp),
            colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(message: String, button: @Composable () -> Unit, button1: @Composable () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = message,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        },
        navigationIcon = { button1() },
        actions = { button() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(message: String, navigationIcon: @Composable () -> Unit, dropDownMenu: @Composable () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        },
        navigationIcon = { navigationIcon() },
        actions = { dropDownMenu() },
    )
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun DynamicTopBar(
    navController: NavController
) {
    val currentRoute = currentRoute(navController = navController)
    val showNotification = remember { mutableStateOf(false) }
    val notificationMessage = remember { mutableStateOf("") }

    when (currentRoute) {

        Route.HomeScreen.route -> {
        }

        Route.SettingsScreen.route -> {
            TopBar(
                message = " General Settings",
                button= {
                },

            ) {}
        }

        Route.LinkedSocialSettingScreen.route -> {
            TopBar(
                message = "Sharing Preferences",
                button= {
                    LogoUser(
                        modifier = Modifier.size(60.dp), R.drawable.pexels5
                    ) { navController.navigate(Route.AccountScreen.route) }
                }
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.AccountScreen.route -> {
            TopBar(
                message = "Account Settings",
                button = {
                }
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.WorkoutScreen.route -> {
        }

        Route.WorkoutModeScreen.route -> {
        }

        Route.TypedExerciseScreen.route -> {
            TopBar(
                message = "Exercises",
                button = {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.HomeSearchScreen.route -> {
            TopBar(
                message = "Exercises",
                button = {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.FeedScreen.route -> {
        }

        Route.NewPlanScreen.route -> {
            TopBar(
                message = "",
                button = {}
            ) {
                IconButton(onClick = {
                    navController.popBackStack()

                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }

        Route.AddGeneratedPlanScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.AddManualWorkoutScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                BackButton {
                    navController.currentBackStackEntry
                    navController.popBackStack()

                }
            }
        }

        Route.LastNewPlanScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                BackButton {
                    navController.popBackStack()
                }
            }
        }

        Route.ShareScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }


        Route.ExerciseDetailsScreen.route -> {
        }

        Route.ForgotPasswordScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                IconButton(onClick = {
                    navController.navigate(Route.SignInScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }

        Route.OTPScreen.route -> {
            TopBar(
                message = "",
                button= {}
            ) {
                IconButton(onClick = {
                    navController.navigate(Route.SignInScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        }
    }
    if (showNotification.value) {
        NotificationCard(
            message = notificationMessage.value,
            onClose = {showNotification.value=false}
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicBottomBar(navController: NavController, scrollBehavior: BottomAppBarScrollBehavior? = null) {
    val currentRoute = currentRoute(navController = navController)

    // Show the bottom bar only for specific routes
    when (currentRoute) {
        Route.WorkoutScreen.route -> {
            WorkoutBottomBar(navController)
        }

        // Empty because new plan has no bottom bar
        Route.NewPlanScreen.route -> {}
        Route.AddManualWorkoutScreen.route -> {}
        Route.AddGeneratedPlanScreen.route -> {}
        Route.LastNewPlanScreen.route -> {}
        Route.WorkoutModeScreen.route -> {}
        Route.ShareScreen.route -> {}
        Route.PostScreen.route -> {}
        Route.ExerciseDetailsScreen.route -> {}
        Route.TypedExerciseScreen.route -> {}
        Route.AccountScreen.route -> {}
        Route.LinkedSocialSettingScreen.route -> {}
        Route.SignInScreen.route -> {}
        Route.SignUpScreen.route -> {}
        Route.OnBoardingScreen.route -> {}
        Route.ForgotPasswordScreen.route -> {}
        Route.OTPScreen.route -> {}
        Route.ChangePasswordScreen.route -> {}
        Route.HomeSearchScreen.route -> {}

        else -> {
            BottomNavigationBar(navController = navController, scrollBehavior = scrollBehavior)
        }
    }
}

@Composable
fun getPreviousDestination(navController: NavController): String? {
    val previousBackStackEntry = navController.previousBackStackEntry

    return previousBackStackEntry?.destination?.route
}


@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(45.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Share Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp),
        )
    }
}

@Composable
fun MyDropdownMenu(menuItems: List<MenuItem>) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.text) },
                onClick = {
                    item.onClick()
                    expanded = false
                },
                leadingIcon = { Icon(item.icon, contentDescription = null) }
            )
        }
    }
}

@Preview
@Composable
fun MyDropPrev(){

    val planScreenMenuItems = listOf(
        MenuItem(
            text = "Progress",
            icon = Icons.Filled.BarChart,
            onClick = { }
        ),
        MenuItem(
            text = "Settings",
            icon = Icons.Outlined.Settings,
            onClick = { /* Handle settings! */ } // TODO find other
        )

    )

    TopBar(message = "top bar", button1 = {},button = { MyDropdownMenu(planScreenMenuItems) })

}