package com.project.gains.presentation.workout.events

import com.project.gains.data.Workout
import com.project.gains.presentation.exercises.events.ExerciseEvent
import com.project.gains.presentation.plan.events.ManagePlanEvent

sealed class ManageWorkoutEvent {
    data class CreateWorkout(val workout : Workout) : ManageWorkoutEvent()
    data class DeleteWorkout(val workout : Workout) : ManageWorkoutEvent()
    data class SelectWorkout(val workout : Workout) : ManageWorkoutEvent()
    data object  AddWorkoutFavourite : ManageWorkoutEvent()
    data object DeleteWorkoutFavourite : ManageWorkoutEvent()
}