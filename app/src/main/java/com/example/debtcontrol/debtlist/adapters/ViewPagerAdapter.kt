package com.example.debtcontrol.debtlist.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.debtcontrol.debtlist.model.Fragments

//Viewpager2 adapter implementation

class ViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: List<Fragments>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val myFragments: List<Fragments> = fragments

    override fun createFragment(position: Int): Fragment {
        return myFragments[position].fragments
    }

    override fun getItemCount(): Int {
        return myFragments.size
    }
}