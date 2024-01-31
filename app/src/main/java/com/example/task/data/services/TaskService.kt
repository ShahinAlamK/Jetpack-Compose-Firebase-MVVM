package com.example.task.data.services

import com.example.task.data.AppResponse
import com.example.task.data.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskService {

    fun createTask(taskModel: TaskModel.Model):Flow<AppResponse<String>>

    fun fetchAllTask():Flow<AppResponse<List<TaskModel>>>

    fun deleteTask(id:String): Flow<AppResponse<String>>

    fun updateTask(taskModel: TaskModel):Flow<AppResponse<String>>
}