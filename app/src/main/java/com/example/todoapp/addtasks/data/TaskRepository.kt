package com.example.todoapp.addtasks.data

import com.example.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map { items -> items.map { TaskModel(it.id, it.task, it.selected) } }

    fun add(taskModel: TaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.addTask(taskModel.toData())

        }
    }

    fun update(taskModel: TaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.updateTask(
               taskModel.toData()
            )
        }
    }

    fun delete(taskModel: TaskModel){
        CoroutineScope(Dispatchers.IO).launch{
            taskDao.deleteTask( taskModel.toData())
        }

    }

}

fun TaskModel.toData():TaskEntity{
   return TaskEntity(this.id,this.task,this.selected)
}