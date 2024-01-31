package com.example.task.data.models

data class TaskModel(
    val uid:String = "",
    val model:Model
){
    data class Model(
        val title:String = "",
        val content:String = "",
        var isComplete:Boolean = false,
        val create:String = "",
    )
}
