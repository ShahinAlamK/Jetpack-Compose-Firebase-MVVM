package com.example.task.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.task.data.AppResponse
import com.example.task.data.view.TaskViewModel
import kotlinx.coroutines.launch

@Composable
fun DeleteTask(isOpen: MutableState<Boolean>, viewModel: TaskViewModel = hiltViewModel()) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    if (isLoading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }
    if (isOpen.value) {
        AlertDialog(
            shape = MaterialTheme.shapes.small,
            onDismissRequest = { },
            dismissButton = {
                TextButton(onClick = { isOpen.value = false }) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            viewModel.deleteTask(viewModel.getDeleteId.value).collect { response ->
                                when (response) {
                                    is AppResponse.Loading -> {
                                        isLoading = true
                                    }

                                    is AppResponse.Success -> {
                                        isOpen.value = false
                                        isLoading = false
                                        Toast.makeText(
                                            context,
                                            "Successfully Deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        viewModel.fetchTask()
                                    }

                                    is AppResponse.Failure -> {
                                        isLoading = false
                                        Toast.makeText(
                                            context,
                                            "Sorry Something went wrong",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }) {
                    Text(text = "Confirm", color = MaterialTheme.colorScheme.error)
                }
            },
            title = { Text(text = "Attention") },
            text = {
                Text(text = "Are you sure? Permanently deleted task")
            }
        )
    }
}