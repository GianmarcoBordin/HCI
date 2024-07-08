package com.project.gains.presentation.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.presentation.navgraph.Route

import com.project.gains.theme.GainsAppTheme

@Composable
fun NewPlanScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(20.dp)
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    IconButton(onClick = {
                        navController.navigate(Route.HomeScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                ) {
                    Text(
                        text = "Create a new Workout/Plan",
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "In order to create your own workout, sse the workout builder or do it manually.",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Button(
                        onClick = {
                            navController.navigate(Route.AddGeneratedPlan.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                    ) {
                        Text(text = "USE PLAN BUILDER")
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {
                            navController.navigate(Route.AddManualWorkout.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                    ) {
                        Text(text = "ADD MANUAL WORKOUT")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewPlanPagePreview() {
    val navController = rememberNavController()
    GainsAppTheme {
        NewPlanScreen(
            navController = navController
        )
    }
}