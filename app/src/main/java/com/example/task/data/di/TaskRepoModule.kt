package com.example.task.data.di

import com.example.task.data.repository.TaskRepository
import com.example.task.data.services.TaskService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TaskRepoModule {

    @Binds
    abstract fun providerRepo(taskRepository: TaskRepository) : TaskService
}