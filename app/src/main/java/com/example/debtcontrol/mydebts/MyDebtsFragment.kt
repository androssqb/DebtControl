package com.example.debtcontrol.mydebts

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
import com.example.debtcontrol.databinding.FragmentMyDebtsBinding
import com.example.debtcontrol.debtlist.DebtListFragmentDirections
import com.example.debtcontrol.mydebtors.adapter.DebtAdapter
import com.example.debtcontrol.mydebtors.adapter.DebtListener
import com.example.debtcontrol.mydebts.viewmodel.MyDebtsViewModel
import com.example.debtcontrol.mydebts.viewmodel.MyDebtsViewModelFactory

class MyDebtsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: FragmentMyDebtsBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_my_debts,
                container,
                false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDatabaseDao

        val viewModelFactory = MyDebtsViewModelFactory(dataSource)
        val myDebtsViewModel =
                ViewModelProvider(this, viewModelFactory).get(MyDebtsViewModel::class.java)

        val adapter = DebtAdapter(DebtListener { debtId ->
            myDebtsViewModel.onDebtCardClicked(debtId)
        })
        binding.myDebtsList.adapter = adapter

        myDebtsViewModel.debts.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        myDebtsViewModel.navigateToDebtDetail.observe(viewLifecycleOwner, { debt ->
            debt?.let {
                findNavController().navigate(
                        DebtListFragmentDirections
                                .actionDebtListFragmentToDebtDetailFragment(debt))
                myDebtsViewModel.onDebtDetailNavigated()
            }
        })

        return binding.root
    }
}