package com.example.task.ui.editTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.task.R
import com.example.task.databinding.FragmentEditTaskBinding
import com.example.task.model.TaskModel
import com.example.task.viewModel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorities = arrayOf("Baixa", "Média", "Alta")
        var taskId = ""

        // Configurar o adapter do Spinner
        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, priorities) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view as TextView
                textView.setTextColor(getPriorityColor(priorities[position]))
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view as TextView
                textView.setTextColor(getPriorityColor(priorities[position]))
                return view
            }
        }

        // Obtenha os dados do Bundle
        arguments?.let { bundle ->

            val taskTitle = bundle.getString("taskTitle") ?: ""
            val taskDescription = bundle.getString("taskDescription") ?: ""
            val taskPriority = bundle.getString("taskPriority") ?: ""
            taskId= bundle.getString("taskId") ?: ""

            // Preencher os campos com os dados da tarefa
            binding.editTextTitle.setText(taskTitle)
            binding.editTextDescription.setText(taskDescription)

            // Determinar a posição da prioridade e selecionar o item correspondente no Spinner
            val priorityPosition = priorities.indexOf(taskPriority)
            if (priorityPosition >= 0) {
                binding.editspinnerPriority.setSelection(priorityPosition)
            }
        }

        // Ação para salvar as alterações
        binding.btnEdit.setOnClickListener {
            val updatedTask = TaskModel(
                id = taskId,
                title = binding.editTextTitle.text.toString(),
                description = binding.editTextDescription.text.toString(),
                priority = binding.editspinnerPriority.selectedItem.toString()
            )

            // Chama o método do ViewModel para salvar a tarefa editada
            viewModel.editTask(updatedTask)

            // Voltar à tela anterior
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.editspinnerPriority.adapter = spinnerAdapter
    }

    private fun getPriorityColor(priority: String): Int {
        return when (priority) {
            "Alta" -> resources.getColor(R.color.red, null)
            "Média" -> resources.getColor(R.color.yellow, null)
            "Baixa" -> resources.getColor(R.color.green, null)
            else -> resources.getColor(R.color.black, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

