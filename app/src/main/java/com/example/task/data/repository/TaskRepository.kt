package com.example.task.data.repository

import android.util.Log
import com.example.task.data.AppResponse
import com.example.task.data.models.TaskModel
import com.example.task.data.services.TaskService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject

class TaskRepository @Inject constructor(private val db:FirebaseFirestore):TaskService {

    override fun createTask(taskModel: TaskModel.Model): Flow<AppResponse<String>> = callbackFlow {
        try {
            trySend(AppResponse.Loading)
            db.collection("task").add(taskModel).addOnSuccessListener {
                trySend(AppResponse.Success("task Successful insert ${it.id}"))
            }.addOnFailureListener {
                trySend(AppResponse.Failure(it))
            }
        }catch (e:Exception){
            trySend(AppResponse.Failure(e))
        }
        awaitClose { close() }

    }

    override fun fetchAllTask(): Flow<AppResponse<List<TaskModel>>> = callbackFlow{
        try {
            trySend(AppResponse.Loading)
            db.collection("task").get().addOnSuccessListener {
                val task= it.map { snapshot ->
                    TaskModel(
                        uid = snapshot.id,
                        model = TaskModel.Model(
                            title = snapshot["title"] as String,
                            content = snapshot["content"] as String,
                            isComplete = snapshot["complete"] as Boolean,
                            create = snapshot["create"] as String,
                        )
                    )
                }
                trySend(AppResponse.Success(task))
            }.addOnFailureListener {
                trySend(AppResponse.Failure(it))
            }
        }catch (e:Exception){
            trySend(AppResponse.Failure(e))
        }
        awaitClose { close() }

    }

    override fun deleteTask(id: String): Flow<AppResponse<String>> = callbackFlow{
        try {
            trySend(AppResponse.Loading)
            db.collection("task").document(id).delete()
                .addOnSuccessListener {
                    trySend(AppResponse.Success("Successful deleted"))
                }.addOnFailureListener {
                    trySend(AppResponse.Failure(it))
                }
        }catch (e:Exception){
            trySend(AppResponse.Failure(e))
        }
        awaitClose{close()}
    }

    override fun updateTask(taskModel: TaskModel): Flow<AppResponse<String>> = callbackFlow{
        try {
            trySend(AppResponse.Loading)

            val hasMap = HashMap<String,Any>()
            hasMap["title"] = taskModel.model.title
            hasMap["content"] = taskModel.model.content
            hasMap["isComplete"] = taskModel.model.isComplete

            db.collection("task").document(taskModel.uid).update(hasMap)
                .addOnSuccessListener {
                    trySend(AppResponse.Success("Successful Update"))
                }.addOnFailureListener {
                    trySend(AppResponse.Failure(it))
                }
        }catch (e:Exception){
            trySend(AppResponse.Failure(e))
        }
        awaitClose{close()}
    }


}

