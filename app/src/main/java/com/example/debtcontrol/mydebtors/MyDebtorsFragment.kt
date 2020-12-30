package com.example.debtcontrol.mydebtors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.debtcontrol.R
import com.example.debtcontrol.database.DebtDatabase
import com.example.debtcontrol.databinding.FragmentMyDebtorsBinding
import com.example.debtcontrol.debtlist.DebtListFragmentDirections
import com.example.debtcontrol.mydebtors.adapter.DebtAdapter
import com.example.debtcontrol.mydebtors.adapter.DebtListener
import com.example.debtcontrol.mydebtors.viewmodel.MyDebtorsViewModel
import com.example.debtcontrol.mydebtors.viewmodel.MyDebtorsViewModelFactory

class MyDebtorsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val binding: FragmentMyDebtorsBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_my_debtors,
                container,
                false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDatabaseDao

        val viewModelFactory = MyDebtorsViewModelFactory(dataSource)
        val myDebtorsViewModel =
                ViewModelProvider(this, viewModelFactory).get(MyDebtorsViewModel::class.java)

        val adapter = DebtAdapter(DebtListener { debtId ->
            myDebtorsViewModel.onDebtCardClicked(debtId)
        })
        binding.myDebtorsList.adapter = adapter

        myDebtorsViewModel.debts.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        myDebtorsViewModel.navigateToDebtDetail.observe(viewLifecycleOwner, { debt ->
            debt?.let {
                findNavController().navigate(
                        DebtListFragmentDirections
                                .actionDebtListFragmentToDebtDetailFragment(debt))
                myDebtorsViewModel.onDebtDetailNavigated()
            }
        })

        return binding.root
    }
}