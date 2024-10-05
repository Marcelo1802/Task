package com.example.task.ui.task.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task.ui.task.ViewPager.ViewPagerCheckFragment
import com.example.task.ui.task.ViewPager.ViewPagerTaskFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        // Número de páginas
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ViewPagerTaskFragment()
            else -> ViewPagerCheckFragment()
        }
    }
}
