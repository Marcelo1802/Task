package com.example.task.model



import java.util.UUID


data class TaskModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val priority: String,
    val status: Boolean = false
)