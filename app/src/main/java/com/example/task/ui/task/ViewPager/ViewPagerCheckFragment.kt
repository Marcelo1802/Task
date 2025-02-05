package com.example.task.ui.task.ViewPager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.databinding.FragmentViewPagerCheckBinding
import com.example.task.model.TaskModel
import com.example.task.repository.toTaskModel
import com.example.task.viewModel.TaskViewModel
import com.example.task.ui.task.adapter.TaskAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewPagerCheckFragment : Fragment() {

    private var _binding: FragmentViewPagerCheckBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModel()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCompleteTasks()
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            taskList = mutableListOf(),
            onDeleteClick = { task ->
                clearSearchInput()
                viewModel.deleteTask(task.id)
            },
            onEditTaskClick = { task ->
                navigateToEditTask(task)
            },
            onStatusChange = { task ->
                viewModel.toggleTaskStatus(task)

            }
        )

        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToEditTask(task: TaskModel) {
        val bundle = Bundle().apply {
            putString("taskId", task.id)
            putString("taskTitle", task.title)
            putString("taskDescription", task.description)
            putString("taskPriority", task.priority)
        }
        findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment, bundle)
    }

    private fun clearSearchInput() {
        binding.editSearchComplete.text?.clear()
    }

    private fun observeCompleteTasks() {
        viewModel.completeTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTaskList(tasks)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResultsComplete.collect { taskEntities ->
                val taskModels = taskEntities.map { it.toTaskModel() }
                taskAdapter.updateTaskList(taskModels)
            }
        }
    }

    private fun setupSearchListener() {
        binding.editSearchComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchCompleteTasks(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
