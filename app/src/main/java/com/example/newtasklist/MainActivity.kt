package com.example.newtasklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newtasklist.databinding.ActivityMainBinding
import com.example.newtasklist.db.Task
import com.example.newtasklist.db.TaskDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel by viewModel<TaskViewModel>()
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.myViewModel = taskViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        taskViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter { selectedItem: Task -> listItemClicked(selectedItem) }
        binding.subscriberRecyclerView.adapter = adapter
        displayTaskList()
    }

    private fun displayTaskList() {
        taskViewModel.tasks.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(task: Task) {
        taskViewModel.initUpdateAndDelete(task)
    }
}