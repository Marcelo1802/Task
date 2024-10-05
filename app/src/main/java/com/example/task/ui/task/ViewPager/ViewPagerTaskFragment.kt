package com.example.task.ui.task.ViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.databinding.FragmentViewPagerTaskBinding
import com.example.task.model.TaskViewModel
import com.example.task.ui.editTask.EditTaskFragment
import com.example.task.ui.task.adapter.TaskAdapter
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
            },
            onEditTaskClick = { task ->
                val bundle = Bundle().apply {
                    putString("taskId", task.id) // Passando o ID da tarefa
                    putString("taskTitle", task.title)
                    putString("taskDescription", task.description)
                    putString("taskPriority", task.priority)
                }
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment, bundle)
            }
        )

        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observa a lista de tarefas
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTaskList(tasks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
