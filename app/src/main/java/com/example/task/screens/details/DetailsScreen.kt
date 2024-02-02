package com.example.task.screens.details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.task.components.CustomTextField
import com.example.task.data.AppResponse
import com.example.task.data.models.TaskModel
import com.example.task.data.view.SharedViewModel
import com.example.task.data.view.TaskViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val model = sharedViewModel.detailsTask

    var isUpdate by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(model.value?.model?.title) }
    var content by remember { mutableStateOf(model.value?.model?.content) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (isUpdate) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            value = title!!,
            onValueChange = { title = it },
            placeholder = { Text(text = "Enter title") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        TextField(
            modifier = Modifier.weight(1f),
            value = content!!,
            onValueChange = { content = it },
            placeholder = { Text(text = "Enter Content") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {
                scope.launch {
                    val task = TaskModel(
                        uid = model.value?.uid!!,
                        model = TaskModel.Model(
                            title = title!!,
                            content = content!!,
                        )
                    )
                    viewModel.updateTask(taskModel = task).collect { update ->
                        when (update) {
                            is AppResponse.Success -> {
                                isUpdate = false
                                Toast.makeText(context, "Successfully update", Toast.LENGTH_SHORT)
                                    .show()
                                viewModel.fetchTask()
                                //navController.popBackStack()
                            }

                            is AppResponse.Loading -> {
                                isUpdate = true
                            }

                            is AppResponse.Failure -> {
                                Toast.makeText(context, "Failed update", Toast.LENGTH_SHORT).show()
                                isUpdate = false
                            }

                        }
                    }
                }
            }) {
            Text(text = "Update")
        }
    }
}
