package com.example.task.ui.task.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.TaskModel
import kotlinx.coroutines.time.delay

class TaskAdapter(
    private var taskList: MutableList<TaskModel>,
    private val onDeleteClick: (TaskModel) -> Unit,
    private val onEditTaskClick: (TaskModel) -> Unit,
    private val onStatusChange: (TaskModel) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Função para mapear prioridades para cores
    private fun getPriorityColor(priority: String): Int {
        return when (priority) {
            "Média" -> Color.YELLOW
            "Alta" -> Color.RED
            else -> Color.GREEN
        }
    }

    // Função para adicionar quebras de linha a cada 20 caracteres
    private fun addLineBreaks(text: String, maxCharsPerLine: Int): String {
        val sb = StringBuilder(text.length)
        var count = 0
        for (char in text) {
            if (count >= maxCharsPerLine && char == ' ') {
                sb.append('\n')  // Adicionar quebra de linha
                count = 0        // Reiniciar a contagem
            } else {
                sb.append(char)
                count++
            }
        }
        return sb.toString()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.descricao)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete)
        val priority: View = itemView.findViewById(R.id.view_priority)
        val imgPriority: ImageView = itemView.findViewById(R.id.img_priority)
        val editTask: ImageView = itemView.findViewById(R.id.edit)
        val checkbox: ImageView = itemView.findViewById(R.id.checkBox)

        fun bind(task: TaskModel) {
            // Limita o título a 20 caracteres
            txtTitle.text = if (task.title.length > 20) task.title.take(20) + "..." else task.title

            // Adiciona quebra de linha a cada 20 caracteres na descrição
            description.text = addLineBreaks(task.description, 40)

            // Alterar a cor do TextView de prioridade
            priority.setBackgroundColor(getPriorityColor(task.priority))
            imgPriority.setColorFilter(getPriorityColor(task.priority))

            // Atualiza o estado do checkbox
            checkbox.setImageResource(if (task.status) R.drawable.check_svgrepo_com else R.drawable.square_svgrepo_com)

            fun status() {
                if (task.status) {
                    editTask.visibility = View.GONE
                } else {
                    editTask.visibility = View.VISIBLE
                }
            }

            status()

            deleteButton.setOnClickListener {
                onDeleteClick(task)
            }

            editTask.setOnClickListener {
                onEditTaskClick(task)
            }

            checkbox.setOnClickListener {
                // Muda o status da tarefa
                onStatusChange(task)

                if (!task.status) {
                    checkbox.setImageResource(R.drawable.check_svgrepo_com)
                } else {
                    checkbox.setImageResource(R.drawable.square_svgrepo_com)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = taskList.size

    fun updateTaskList(newTasks: List<TaskModel>) {
        taskList.clear()
        taskList.addAll(newTasks)
        notifyDataSetChanged()
    }
}

