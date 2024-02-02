package com.example.task.data.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.task.data.models.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel

class SharedViewModel : ViewModel() {

    var detailsTask: MutableState<TaskModel?> = mutableStateOf(null)

    fun setDetailTask(taskModel: TaskModel) {
        detailsTask.value = taskModel
    }
}