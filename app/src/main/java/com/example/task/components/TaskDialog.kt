package com.example.task.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.task.data.AppResponse
import com.example.task.data.models.TaskModel
import com.example.task.data.view.TaskViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun TaskDialogBox(openDialog: MutableState<Boolean>,viewModel: TaskViewModel= hiltViewModel()) {

    var title by remember{ mutableStateOf("") }
    var des by remember{ mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val pattern = SimpleDateFormat("dd/MM/yyyy")
    val currentDateAndTime = pattern.format(Date())

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (openDialog.value){
        AlertDialog(
            shape = MaterialTheme.shapes.small,
            onDismissRequest = {openDialog.value=false},
            confirmButton = {},
            title = { Text(text = "New Task")},
            text = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Enter Title",
                        value = title,
                        onValueChange = { title = it },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Content",
                        value = des,
                        onValueChange = { des = it },
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    if (isLoading) CircularProgressIndicator() else
                    Button(
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        onClick = {
                          scope.launch {
                              if (title.isEmpty() && des.isEmpty()){
                                  Toast.makeText(context, "Please input contents", Toast.LENGTH_SHORT).show()
                              }else{
                                  val model = TaskModel.Model(
                                      title = title,
                                      content = des,
                                      isComplete = false,
                                      create = currentDateAndTime
                                  )
                                  viewModel.createTask(taskModel = model).collect{
                                      when(it){
                                          is AppResponse.Success->{
                                              title=""
                                              des = ""
                                              isLoading=false
                                              openDialog.value=false
                                              Toast.makeText(context, "Successfully Insert" , Toast.LENGTH_SHORT).show()
                                              viewModel.fetchTask()
                                          }
                                          is AppResponse.Failure->{
                                              isLoading=false
                                              Toast.makeText(context, "Failed ${it.msg}" , Toast.LENGTH_SHORT).show()
                                          }
                                          is AppResponse.Loading->{
                                              isLoading=true
                                          }
                                      }
                                  }
                              }
                          }
                        }) {
                        Text(text = "Save")
                    }
                }
            }
        )
    }
}

