package com.example.task.ui.task.ViewPager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.databinding.FragmentViewPagerTaskBinding
import com.example.task.viewModel.TaskViewModel
import com.example.task.repository.toTaskModel
import com.example.task.ui.task.adapter.TaskAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewPagerTaskFragment : Fragment() {

    private var _binding: FragmentViewPagerTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModel()

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialize o taskAdapter
        taskAdapter = TaskAdapter(mutableListOf(),
            onDeleteClick = { task ->
                viewModel.deleteTask(task.id) // Deletar usando o ID
                binding.editSearch.text?.clear().toString()
            },
            onEditTaskClick = { task ->
                val bundle = Bundle().apply {
                    putString("taskId", task.id) // Passando o ID da tarefa
                    putString("taskTitle", task.title)
                    putString("taskDescription", task.description)
                    putString("taskPriority", task.priority)
                }
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment, bundle)
            },
            onStatusChange = { task ->
                viewModel.toggleTaskStatus(task) // Lógica para mudar o status
                Log.i("TAG", "Status da tarefa alterado: ${task.status}")
            }
        )

        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observa a lista de tarefas incompletas
        viewModel.incompleteTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTaskList(tasks)
        }

        // Coletando o StateFlow de resultados da busca
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collect { taskEntities ->
                val taskModels = taskEntities.map { it.toTaskModel() } // Converter TaskEntity para TaskModel
                taskAdapter.updateTaskList(taskModels)
            }
        }


        // Adicionar um listener para o EditText para atualizar a busca conforme o usuário digita
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Fazer a busca a cada letra digitada
                viewModel.searchTasks(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
