package com.example.task.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.task.components.DeleteTask
import com.example.task.components.TaskCard
import com.example.task.components.TaskDialogBox
import com.example.task.data.models.TaskModel
import com.example.task.data.view.TaskViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class,)
@Composable
fun HomeScreen(viewModel: TaskViewModel = hiltViewModel()) {
    val newDialog = remember { mutableStateOf(false) }
    TaskDialogBox(openDialog = newDialog)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = { Text(text = "Task Schedule")},
                navigationIcon = {
                    IconButton(
                        onClick = { }) {
                        Icon(imageVector = Icons.Outlined.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { newDialog.value=true}) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        }
    ) {paddingValue->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValue),
            contentAlignment = Alignment.Center
        ) {
            if(viewModel.error.value.isNotEmpty()){
                  Text(text = viewModel.error.value, textAlign = TextAlign.Center)
              }else if (viewModel.isLoading.value){
                  CircularProgressIndicator()
              }else if (viewModel.response.value.isEmpty()){
                  Text(text = "Empty Task Toady")
              } else if (viewModel.response.value.isNotEmpty()){
                  TaskListView()
              }
          }

        }

    }


@Composable
fun TaskListView(viewModel: TaskViewModel = hiltViewModel()) {
    val newDialog = remember { mutableStateOf(false) }

    DeleteTask(isOpen = newDialog)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
    ) {
        items(viewModel.response.value.size){index->
            val task = viewModel.response.value
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard(
                index = (index+1).toString(),
                title = task[index].model.title,
                content = task[index].model.content,
                date = task[index].model.create,
                isCheck = task[index].model.isComplete,
                onChangeValue = {},
                click = {
                    viewModel.setDeleteData(task[index].uid)
                    newDialog.value = true
                }
            )
        }
    }}