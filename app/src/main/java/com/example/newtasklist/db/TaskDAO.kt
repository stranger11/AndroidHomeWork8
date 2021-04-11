package com.example.newtasklist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDAO {

    @Insert
    suspend fun insertTask(task: Task) : Long

    @Update
    suspend fun updateTask(task: Task) : Int

    @Delete
    suspend fun deleteTask(task: Task) : Int

    @Query("DELETE FROM task_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM task_data_table")
    fun readAllData(): LiveData<List<Task>>
}