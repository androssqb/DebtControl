package com.example.debtcontrol.debtlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.debtcontrol.R
import com.example.debtcontrol.databinding.FragmentDebtListBinding
import com.example.debtcontrol.debtlist.adapters.ViewPagerAdapter
import com.example.debtcontrol.debtlist.data.FragmentsDatasource
import com.example.debtcontrol.debtlist.viewmodel.DebtListViewModel
import com.example.debtcontrol.debtlist.viewmodel.DebtListViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DebtListFragment : Fragment() {

    private lateinit var binding: FragmentDebtListBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_debt_list,
                container,
                false
        )

        //Connecting toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        //Option menu ON
        setHasOptionsMenu(true)

        val viewModelFactory = DebtListViewModelFactory()
        val debtListViewModel =
                ViewModelProvider(this, viewModelFactory)
                        .get(DebtListViewModel::class.java)

        binding.debtListViewModel = debtListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        debtListViewModel.navigateToAddCardFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(
                        DebtListFragmentDirections.actionDebtListFragmentToAddCardFragment(binding.viewPager.currentItem)
                )
                debtListViewModel.navigationDone()
            }
        })


        //UI customization
        initViewPager2WithFragments()
        setFAB()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    //ViewPager2 implementation
    private fun initViewPager2WithFragments() {
        val myFragmentsDataset = FragmentsDatasource().loadFragments()
        binding.viewPager.adapter =
                ViewPagerAdapter(childFragmentManager, lifecycle, myFragmentsDataset)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(myFragmentsDataset[position].tabTitleResourceId)
        }.attach()
    }

    //ViewPager implementation
//    private fun setUpTabs() {
//        //Set our adapter
//        val adapter = PagerAdapter(childFragmentManager)
//        adapter.addFragment(MyDebtorsFragment(), getString(R.string.first_tab_text))
//        adapter.addFragment(MyDebtsFragment(), getString(R.string.second_tab_text))
//        binding.viewPager.adapter = adapter
//        //Connects our tabLayout with viewPager
//        binding.tabs.setupWithViewPager(binding.viewPager)
//    }

    private fun setFAB() {
        binding.fab.backgroundTintList = null
        binding.fab.setBackgroundResource(R.drawable.fab_ripple)
        binding.fab.setIconResource(R.drawable.ic_plus_full_black)
    }
}