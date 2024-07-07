package com.project.gains.presentation.plan.events

import com.project.gains.data.Exercise

sealed class ManageExercises {
    data class AddExercise(val exercise: Exercise) : ManageExercises()
    data class DeleteExercise(val exercise: Exercise) : ManageExercises()
}