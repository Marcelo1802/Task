package com.example.task.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.TaskModel

class TaskViewModel : ViewModel() {

    // Lista de tarefas com LiveData para observar mudanças
    private val _tasks = MutableLiveData<MutableList<TaskModel>>(mutableListOf())
    val tasks: LiveData<MutableList<TaskModel>> get() = _tasks

    // Função para adicionar nova tarefa à lista
    fun addTask(task: TaskModel) {
        _tasks.value?.add(task)
        _tasks.value = _tasks.value // Atualiza o LiveData
    }
}
