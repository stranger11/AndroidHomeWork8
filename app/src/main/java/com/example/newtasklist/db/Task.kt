package com.example.newtasklist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_data_table")
data class Task (

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "task_name")
        val id : Int,

        @ColumnInfo(name = "task_id")
        var task : String,

        @ColumnInfo(name = "description_email")
        var description : String,

)