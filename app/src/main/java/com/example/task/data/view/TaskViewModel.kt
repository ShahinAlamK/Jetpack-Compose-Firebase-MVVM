package com.example.task.data.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.AppResponse
import com.example.task.data.models.TaskModel
import com.example.task.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository):ViewModel() {

    val response: MutableState<List<TaskModel>> = mutableStateOf(emptyList())
    val error: MutableState<String> = mutableStateOf("")

    private var _isLoading = mutableStateOf(false)
    val isLoading:State<Boolean> = _isLoading

    fun createTask(taskModel: TaskModel.Model)= taskRepository.createTask(taskModel)

    init {fetchTask()}
    fun fetchTask() = viewModelScope.launch {
        taskRepository.fetchAllTask().collect{datas->
            when(datas){
                is AppResponse.Loading->{_isLoading.value = true}
                is AppResponse.Failure->{error.value = datas.msg.message!!.toString()}
                is AppResponse.Success->{
                    response.value = datas.data
                    _isLoading.value = false
                }
            }
        }
    }

    val getDeleteId:MutableState<String> = mutableStateOf("")
    fun setDeleteData(uid: String){ getDeleteId.value= uid }
    fun deleteTask(id:String) = taskRepository.deleteTask(id)

    fun updateTask(taskModel: TaskModel) = taskRepository.updateTask(taskModel = taskModel)

}