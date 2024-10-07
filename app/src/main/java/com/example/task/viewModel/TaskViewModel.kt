package com.example.task.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.task.database.entities.TaskEntity
import com.example.task.model.TaskModel
import com.example.task.repository.TasksRepository
import com.example.task.repository.toTaskModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TasksRepository) : ViewModel() {


    // Exposição das tarefas como LiveData
    val tasks: LiveData<List<TaskModel>> = repository.tasks
        .map { entities -> entities.map { it.toTaskModel() } }
        .asLiveData()


    private val _searchResults = MutableStateFlow<List<TaskEntity>>(emptyList())
    val searchResults: StateFlow<List<TaskEntity>> = _searchResults

    private val _searchResultsComplete = MutableStateFlow<List<TaskEntity>>(emptyList())
    val searchResultsComplete: StateFlow<List<TaskEntity>> = _searchResultsComplete

    fun searchTasks(query: String) {
        viewModelScope.launch {
            // Busca por tarefas incompletas com base no título
            repository.searchIncompleteTasksByTitle(query).collect { tasks ->
                _searchResults.value = tasks
            }
        }
    }

    fun searchCompleteTasks(query: String) {
        viewModelScope.launch {
            // Busca por tarefas completas com base no título
            repository.searchcompleteTasksByTitle(query).collect { tasks ->
                _searchResultsComplete.value = tasks
            }
        }
    }




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
            kotlinx.coroutines.delay(1000)
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
