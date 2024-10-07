package com.example.task.model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.task.model.TaskModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class TaskViewModel(private val repository: TasksRepository) : ViewModel() {

    // Exposição das tarefas como LiveData
    val tasks: LiveData<List<TaskModel>> = repository.tasks
        .map { entities -> entities.map { it.toTaskModel() } }
        .asLiveData()


    val incompleteTasks: LiveData<List<TaskModel>> = repository.incompleteTasks
        .map { entities -> entities.map { it.toTaskModel() } }
        .asLiveData()

    val completeTasks: LiveData<List<TaskModel>> = repository.completeTasks
        .map { entities -> entities.map { it.toTaskModel() } }
        .asLiveData()



    // Função para salvar uma nova tarefa
    fun saveTask(task: TaskModel) {
        viewModelScope.launch {
            Log.i("TAG", "bind: ${task.status}")
            repository.save(task)
        }
    }

    // Função para deletar uma tarefa pelo ID
    fun deleteTask(id: String) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    // Função para alternar o status da tarefa (concluído ou não)
    fun toggleTaskStatus(task: TaskModel) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(5000)
            repository.toggleIsDone(task)
        }
    }

    // Função para buscar uma tarefa pelo ID
    fun findTaskById(id: String): LiveData<TaskModel?> {
        return repository.findById(id)
            .map { it?.toTaskModel() }
            .asLiveData()
    }

    fun editTask(updatedTask: TaskModel) {
        viewModelScope.launch {
            repository.updateTask(updatedTask)
        }
    }


}
