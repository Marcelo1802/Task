package com.example.task.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.FragmentViewPagerTaskBinding
import com.example.task.model.TaskModel
import com.example.task.model.TaskViewModel
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
        taskAdapter = TaskAdapter(mutableListOf()) { task ->
            // Callback para deletar a tarefa
            // Você pode chamar o ViewModel ou realizar outra ação aqui
            viewModel.deleteTask(task.id)
        }

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