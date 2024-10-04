package com.example.task.ui.task

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        // Número de páginas
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ViewPagerTaskFragment()
            else -> CheckFragment()
        }
    }
}
