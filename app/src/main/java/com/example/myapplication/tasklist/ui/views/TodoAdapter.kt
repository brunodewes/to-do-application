package com.example.myapplication.tasklist.ui.views

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.tasklist.ui.data.TaskListItemUiState
import com.example.myapplication.tasklist.ui.data.TaskListUiState
import com.example.myapplication.databinding.ItemTodoBinding
import com.example.myapplication.tasklist.ui.viewModels.TaskListViewModel

class TodoAdapter(viewModel: TaskListViewModel) : RecyclerView.Adapter<TodoAdapter.TaskViewHolder>(){

    private var taskListUiState = viewModel.setUpdatedList()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun updateTaskList(newTaskList: TaskListUiState) {
        taskListUiState = newTaskList
//        println(taskListUiState)
        notifyDataSetChanged()
    }

    fun getTaskIdAtPosition(position: Int): String{
        return taskListUiState.tasks[position].id
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class TaskViewHolder(private val binding: ItemTodoBinding, listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(task: TaskListItemUiState) {

            binding.tvTaskTitle.text = task.title
            binding.cbDone.isChecked = task.isChecked

            toggleStrikeThrough(binding.tvTaskTitle, task.isChecked)

            binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(binding.tvTaskTitle, isChecked)
                task.isChecked = isChecked
            }
        }

        private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
            if(isChecked) {
                tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currTask = taskListUiState.tasks[position]
        holder.bind(currTask)
    }

    override fun getItemCount(): Int {
        return taskListUiState.tasks.size
    }
}