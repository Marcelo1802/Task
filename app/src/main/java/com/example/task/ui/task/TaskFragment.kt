package com.example.task.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentTaskBinding
import com.example.task.ui.task.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurando o ViewPager2 com o adapter
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter


        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "TASK"
                else -> "CHECK"
            }
        }.attach()

        clic()

    }


    private fun clic(){
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
