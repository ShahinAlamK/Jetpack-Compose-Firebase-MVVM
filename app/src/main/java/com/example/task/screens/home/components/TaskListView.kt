package com.example.task.screens.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.task.components.DeleteTask
import com.example.task.components.TaskCard
import com.example.task.data.view.SharedViewModel
import com.example.task.data.view.TaskViewModel
import com.example.task.routes.details

@Composable
fun TaskListView(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val newDialog = remember { mutableStateOf(false) }

    DeleteTask(isOpen = newDialog)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
    ) {
        items(viewModel.response.value.size) { index ->
            val task = viewModel.response.value
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard(
                index = (index + 1).toString(),
                title = task[index].model.title,
                content = task[index].model.content,
                date = task[index].model.create,
                click = {
                    sharedViewModel.setDetailTask(taskModel = task[index])
                    navController.navigate(details)
                },
                onLongClick = {
                    viewModel.setDeleteData(task[index].uid)
                    newDialog.value = true
                }
            )
        }
    }
}