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
import com.example.task.databinding.ItemTaskBinding
import com.example.task.model.TaskModel
import kotlinx.coroutines.time.delay

class TaskAdapter(
    private var taskList: MutableList<TaskModel>,
    private val onDeleteClick: (TaskModel) -> Unit,
    private val onEditTaskClick: (TaskModel) -> Unit,
    private val onStatusChange: (TaskModel) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskModel) {
            with(binding) {
                // Limita o título a 20 caracteres
                title.text = if (task.title.length > 20) task.title.take(20) + "..." else task.title

                // Adiciona quebra de linha a cada 20 caracteres na descrição
                descricao.text = addLineBreaks(task.description, 40)

                // Alterar a cor da view de prioridade
                val priorityColor = getPriorityColor(task.priority)
                viewPriority.setBackgroundColor(priorityColor)
                imgPriority.setColorFilter(priorityColor)

                // Atualiza o estado do checkbox
                checkBox.setImageResource(if (task.status) R.drawable.check_svgrepo_com else R.drawable.square_svgrepo_com)

                // Atualiza a visibilidade do botão de editar com base no status da tarefa
                updateTaskStatus(task, editTask)


                setupClickListeners(task)
            }
        }

        private fun setupClickListeners(task: TaskModel) {
            with(binding){

                deleteButton.setOnClickListener {
                    onDeleteClick(task)
                }

                editTask.setOnClickListener {
                    onEditTaskClick(task)
                }

                checkBox.setOnClickListener {
                    // Muda o status da tarefa
                    onStatusChange(task)

                    // Atualiza o estado do checkbox
                    checkBox.setImageResource(if (!task.status) {
                        R.drawable.check_svgrepo_com
                    } else {
                        R.drawable.square_svgrepo_com
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
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

    // Função atualizar status
    private fun updateTaskStatus(task: TaskModel, editTask: View) {
        if (task.status) {
            editTask.visibility = View.GONE
        } else {
            editTask.visibility = View.VISIBLE
        }
    }
}


