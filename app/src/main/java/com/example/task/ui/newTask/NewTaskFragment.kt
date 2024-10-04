package com.example.task.ui.newTask

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.task.R
import com.example.task.model.TaskModel
import com.example.task.model.TaskViewModel

class NewTaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.priority_spinner)
        val priorities = arrayOf("Baixa", "Média", "Alta")

        // Criando o ArrayAdapter com o layout customizado
        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, priorities) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view as TextView

                // Aplicar cores customizadas para cada nível de prioridade
                when (position) {
                    0 -> textView.setTextColor(Color.GREEN)   // Baixa
                    1 -> textView.setTextColor(Color.YELLOW)  // Média
                    2 -> textView.setTextColor(Color.RED)     // Alta
                }

                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view as TextView

                // Aplicar cores customizadas para o dropdown
                when (position) {
                    0 -> textView.setTextColor(Color.GREEN)   // Baixa
                    1 -> textView.setTextColor(Color.YELLOW)  // Média
                    2 -> textView.setTextColor(Color.RED)     // Alta
                }

                return view
            }
        }

        spinner.adapter = spinnerAdapter

        val titleEditText: EditText = view.findViewById(R.id.valor_inicialEditText)
        val descriptionEditText: EditText = view.findViewById(R.id.description)
        val saveButton: Button = view.findViewById(R.id.button)

        saveButton.setOnClickListener {
            val priority = spinner.selectedItem.toString()
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            val newTask = TaskModel(
                id = 0,
                priority = priority,
                title = title,
                description = description,
                status = false
            )

            taskViewModel.addTask(newTask)
        }
    }
}
