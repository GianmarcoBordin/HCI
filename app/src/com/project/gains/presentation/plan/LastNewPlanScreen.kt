package com.project.gains.presentation.plan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.gains.data.ExerciseType
import com.project.gains.data.Option
import com.project.gains.data.TrainingMetricType
import com.project.gains.data.generateOptions
import com.project.gains.presentation.Dimension
import com.project.gains.presentation.components.FeedbackAlertDialog
import com.project.gains.presentation.navgraph.Route
import com.project.gains.presentation.plan.events.ManagePlanEvent

@Composable
fun LastNewPlanScreen(navController: NavController, createPlanHandler: (ManagePlanEvent.CreatePlan) -> Unit) {
    val selectedExerciseTypes =
        remember { mutableStateListOf<ExerciseType>() } // List to store selected options
    val selectedMetrics =
        remember { mutableStateListOf<TrainingMetricType>() } // List to store selected options
    val selectedMusic = remember { mutableStateOf(false) } // List to store selected options
    val selectedBackup = remember { mutableStateOf(false) } // List to store selected options
    val allOptions = remember { generateOptions() } // List to store selected options
    val options = remember { mutableStateListOf<Option>() } // List to store selected options
    val showDialog = remember { mutableStateOf(false) }

    // Function to handle checkbox state change
    fun onOptionSelected(option: Option, isChecked: Boolean) {
        if (isChecked) {
            options.add(option)
        } else {
            options.remove(option)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
            )
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
        ) {

            item {
                Text(
                    text = "Additional preferences",
                    style = MaterialTheme.typography.headlineMedium

                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(
                        10.dp
                    )
                )
            }
            item {
                Text(
                    text = "Choose if you want to have music while training",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        ) // Optional background for emphasis
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            ), shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[0],
                    onOptionSelected = { isChecked ->
                        selectedMusic.value = true
                        onOptionSelected(
                            allOptions[0],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose if you want to have backup on your workout",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            ), shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[1],
                    onOptionSelected = { isChecked ->
                        selectedBackup.value = true
                        onOptionSelected(
                            allOptions[1],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose the metrics to track in your progress",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            ), shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[2],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.BPM)
                        onOptionSelected(
                            allOptions[2],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[3],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.KCAL)

                        onOptionSelected(
                            allOptions[3],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[4],
                    onOptionSelected = { isChecked ->
                        selectedMetrics.add(TrainingMetricType.FRQNCY)
                        onOptionSelected(
                            allOptions[4],
                            isChecked
                        )
                    }
                )
            }
            item {
                Text(
                    text = "Choose the muscle groups to include in your plan",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Make it bigger and bold
                    color = MaterialTheme.colorScheme.onSurface, // Use a color that stands out
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add padding for better spacing
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            ), shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp) // Inner padding for the text itself
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[5],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.CHEST)
                        onOptionSelected(
                            allOptions[5],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[6],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.BACK)
                        onOptionSelected(
                            allOptions[6],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[7],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.SHOULDERS)
                        onOptionSelected(
                            allOptions[7],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[8],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.ARMS)
                        onOptionSelected(
                            allOptions[8],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[9],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.LEGS)
                        onOptionSelected(
                            allOptions[9],
                            isChecked
                        )
                    }
                )
            }
            item {
                OptionCheckbox(
                    option = allOptions[10],
                    onOptionSelected = { isChecked ->
                        selectedExerciseTypes.add(ExerciseType.CORE)
                        onOptionSelected(
                            allOptions[10],
                            isChecked
                        )
                    }
                )
            }


            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimension.MediumPadding2)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }

            }
            item {
                if (showDialog.value) {
                    FeedbackAlertDialog(
                        title = "You have successfully generated your plan!",
                        onDismissRequest = {
                            showDialog.value = false
                            navController.navigate(Route.PlanScreen.route)
                        },
                        onConfirm = {
                            showDialog.value = false
                            navController.navigate(Route.PlanScreen.route)
                        },
                        show = showDialog

                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxHeight(0.125f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ){
            Button(
                modifier = Modifier.fillMaxWidth(0.8f)
                    .align(Alignment.Center)
                    .height(60.dp),
                onClick = {
                    createPlanHandler(
                        ManagePlanEvent.CreatePlan(
                            selectedMetrics,
                            selectedExerciseTypes,
                            selectedMusic.value,
                            selectedBackup.value
                        )
                    )
                    showDialog.value=true
                },
            ) {
                Text(
                    text = "Generate",
                )
            }
        }


    }

}

@Composable
fun OptionCheckbox(
    option: Option,
    onOptionSelected: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.name,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    onOptionSelected(it)
                },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkedColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LastNewPlanScreenPreview() {
    val navController = rememberNavController()
    LastNewPlanScreen(navController = navController, createPlanHandler = {})
}



