package com.example.task.model



data class TaskModel(
    val id: Int,
    val priority: String,
    val title: String,
    val description: String,
    val status: Boolean
)