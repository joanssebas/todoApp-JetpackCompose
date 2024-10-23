package com.example.todoapp.addtasks.domain

import com.example.todoapp.addtasks.data.TaskRepository
import com.example.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(taskModel: TaskModel){
        taskRepository.delete(taskModel)
    }

}