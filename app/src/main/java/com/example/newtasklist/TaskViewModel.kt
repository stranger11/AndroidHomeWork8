package com.example.newtasklist

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtasklist.db.Task
import com.example.newtasklist.db.TaskRepository
import kotlinx.coroutines.launch


class TaskViewModel(private val repository: TaskRepository) : ViewModel(), Observable {

    val tasks = repository.tasks
    private var isUpdateOrDelete = false
    private lateinit var taskToUpdateOrDelete: Task

    @Bindable
    val inputTask = MutableLiveData<String>()

    @Bindable
    val inputDescription = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (inputTask.value == null) {
            statusMessage.value = Event("Please enter task name")
        } else if (inputDescription.value == null) {
            statusMessage.value = Event("Please enter description")
        } else {
            if (isUpdateOrDelete) {
                taskToUpdateOrDelete.task = inputTask.value!!
                taskToUpdateOrDelete.description = inputDescription.value!!
                update(taskToUpdateOrDelete)
            } else {
                val task = inputTask.value!!
                val desc = inputDescription.value!!
                insert(Task(0, task, desc))
                inputTask.value = null
                inputDescription.value = null
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(taskToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(task: Task) = viewModelScope.launch {
        val newRowId = repository.insert(task)
        if (newRowId > -1) {
            statusMessage.value = Event("Task Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun update(task: Task) = viewModelScope.launch {
        val noOfRows = repository.update(task)
        if (noOfRows > 0) {
            inputTask.value = null
            inputDescription.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Task Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun delete(task: Task) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(task)

        if (noOfRowsDeleted > 0) {
            inputTask.value = null
            inputDescription.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Task Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Tasks Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun initUpdateAndDelete(task: Task) {
        inputTask.value = task.task
        inputDescription.value = task.description
        isUpdateOrDelete = true
        taskToUpdateOrDelete = task
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}