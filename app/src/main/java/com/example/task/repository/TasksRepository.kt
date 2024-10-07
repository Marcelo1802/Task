package com.example.task.repository


import com.example.task.database.dao.TaskDao
import com.example.task.database.entities.TaskEntity
import com.example.task.model.TaskModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TasksRepository(
    private val dao: TaskDao
) {

    val incompleteTasks get() = dao.findIncompleteTasks()
    val completeTasks get() = dao.findcompleteTasks()


    val tasks get() = dao.findAll()

    suspend fun save(task: TaskModel) = withContext(IO) {
        dao.save(task.toTaskEntity())
    }

    fun searchIncompleteTasksByTitle(query: String): Flow<List<TaskEntity>> {
        return dao.searchIncompleteTasksByTitle(query)
    }
    fun searchcompleteTasksByTitle(query: String): Flow<List<TaskEntity>> {
        return dao.searchcompleteTasksByTitle(query)
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

    suspend fun editTask(task: TaskModel) = withContext(IO) {
        dao.save(task.toTaskEntity())
    }

    suspend fun updateTask(task: TaskModel) {
        dao.update(task.toTaskEntity())
    }


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