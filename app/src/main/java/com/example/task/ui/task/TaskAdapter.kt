package com.example.task.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.TaskModel


class TaskAdapter(private var taskList: MutableList<TaskModel>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.descricao)
        val priority: ImageView = itemView.findViewById(R.id.img_priority)
        val viewPriority: View = itemView.findViewById(R.id.view_priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.txtTitle.text = task.title
        holder.description.text = task.description

        val priorityColor = when (task.priority.lowercase()) {
            "baixa" -> R.color.green
            "média" -> R.color.yellow
            else -> R.color.red
        }
        holder.priority.setColorFilter(holder.itemView.context.getColor(priorityColor), android.graphics.PorterDuff.Mode.SRC_IN)

        // Aplica a cor à `priority` e `viewPriority`
        holder.priority.setColorFilter(
            holder.itemView.context.getColor(priorityColor),
            android.graphics.PorterDuff.Mode.SRC_IN
        )

        // Mudando a cor da view `viewPriority` conforme a prioridade
        holder.viewPriority.setBackgroundColor(holder.itemView.context.getColor(priorityColor))

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    // Função para atualizar a lista de tarefas
    fun updateTaskList(newTaskList: List<TaskModel>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
}