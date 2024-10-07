package com.example.task.ui.newTask

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.model.TaskModel
import com.example.task.viewModel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class NewTaskFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.priority_spinner)
        val titleEditText: EditText = view.findViewById(R.id.valor_inicialEditText)
        val descriptionEditText: EditText = view.findViewById(R.id.description)

        // Definir um adapter de Spinner que muda a cor com base na prioridade
        val priorities = arrayOf("Baixa", "Média", "Alta")

        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, priorities) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view as TextView

                // Aplicar cor baseada na prioridade
                textView.setTextColor(getPriorityColor(priorities[position]))

                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view as TextView

                // Aplicar cor no dropdown também
                textView.setTextColor(getPriorityColor(priorities[position]))

                return view
            }
        }

        spinner.adapter = spinnerAdapter

        val addButton: View = view.findViewById(R.id.button)
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val priority = spinner.selectedItem.toString()

            // Criação do TaskModel com a prioridade selecionada
            val newTask = TaskModel(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                priority = priority,
                status = false
            )

            // Salvar a tarefa no banco de dados via ViewModel
            viewModel.saveTask(newTask)
            findNavController().navigate(R.id.action_newTaskFragment_to_taskFragment)
        }
    }

    // Função que retorna a cor com base na prioridade
    private fun getPriorityColor(priority: String): Int {
        return when (priority) {
            "Baixa" -> Color.GREEN
            "Média" -> Color.YELLOW
            "Alta" -> Color.RED
            else -> Color.BLACK // Cor padrão
        }
    }
}

