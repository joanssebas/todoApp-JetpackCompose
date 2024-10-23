package com.example.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.addtasks.domain.AddTaskUseCase
import com.example.todoapp.addtasks.domain.DeleteTaskUseCase
import com.example.todoapp.addtasks.domain.GetTasksUseCase
import com.example.todoapp.addtasks.domain.UpdateTaskUseCase
import com.example.todoapp.addtasks.ui.TasksUiState.Success
import com.example.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
):ViewModel() {

    val uiState:StateFlow<TasksUiState> = getTasksUseCase().map ( ::Success  )
        .catch { TasksUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),TasksUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

//    private val _tasks = mutableStateListOf<TaskModel>()
//val task:List<TaskModel> = _tasks



    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTasksCreated(task: String) {

        _showDialog.value = false


        viewModelScope.launch {
            addTaskUseCase(TaskModel(task=task))
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true    }

    fun onCheckboxSelected(taskModel: TaskModel) {

viewModelScope.launch {
    updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
}

    }

    fun onItemRemove(taskModel: TaskModel) {
        //Borrar item
/*val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove((task))*/
viewModelScope.launch{
    deleteTaskUseCase(taskModel)
}
    }


}