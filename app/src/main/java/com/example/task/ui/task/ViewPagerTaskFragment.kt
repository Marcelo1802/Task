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


class ViewPagerTaskFragment : Fragment() {

    private var _binding: FragmentViewPagerTaskBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels() // Acessa o ViewModel compartilhado

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

        setupRecyclerView()

        // Observa mudanças na lista de tarefas
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTaskList(tasks)
        }
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = taskAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}