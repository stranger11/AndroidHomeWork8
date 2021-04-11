package com.example.newtasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newtasklist.databinding.ListItemBinding
import com.example.newtasklist.db.Task
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyRecyclerViewAdapter(private val click:(Task)->Unit)
    : RecyclerView.Adapter<MyViewHolder>()
{
    private val taskList = ArrayList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding : ListItemBinding =
          DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
      return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.bind(taskList[position],click)
    }

    fun setList(tasks: List<Task>) {
        taskList.clear()
        taskList.addAll(tasks)
    }
}

class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task, click:(Task)->Unit){
        binding.taskName.text = task.task
        binding.descriptionName.text = task.description
        binding.listItemLayout.setOnClickListener{
             click(task)
          }
    }

}