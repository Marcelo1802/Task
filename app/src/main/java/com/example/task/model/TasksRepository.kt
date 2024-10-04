package com.example.task.model


import com.example.task.database.dao.TaskDao
import com.example.task.database.entities.TaskEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TasksRepository(
    private val dao: TaskDao
) {

    val tasks get() = dao.findAll()

    suspend fun save(task: TaskModel) = withContext(IO) {
        dao.save(task.toTaskEntity())
    }

    suspend fun toggleIsDone(task: TaskModel) = withContext(IO) {
        val entity = task.copy(status = !task.status)
            .toTaskEntity()
        dao.save(entity)
    }

    suspend fun delete(id: String) {
        dao.delete(
            TaskEntity(id = id, title = "", description = "", priority = "")
        )
    }

    fun findById(id: String) = dao.findById(id)

}

fun TaskModel.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority,
    status = this.status
)

fun TaskEntity.toTaskModel() = TaskModel(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority,
    status = this.status
)