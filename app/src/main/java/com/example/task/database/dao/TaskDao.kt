package com.example.task.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.task.database.entities.TaskEntity
import com.example.task.model.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Query("SELECT * FROM TaskEntity WHERE title LIKE '%' || :query || '%' AND status = 0")
    fun searchIncompleteTasksByTitle(query: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE title LIKE '%' || :query || '%' AND status = 1")
    fun searchcompleteTasksByTitle(query: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE status = 0")
    fun findIncompleteTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE status = 1")
    fun findcompleteTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity")
    fun findAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun findById(id: String): Flow<TaskEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)


}