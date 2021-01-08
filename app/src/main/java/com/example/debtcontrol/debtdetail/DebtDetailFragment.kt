package com.example.debtcontrol.debtdetail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.debtcontrol.Constants
import com.example.debtcontrol.R
import com.example.debtcontrol.database.DebtDatabase
import com.example.debtcontrol.database.DebtHistoryDatabase
import com.example.debtcontrol.databinding.FragmentDebtDetailBinding
import com.example.debtcontrol.debtdetail.adapter.DebtHistoryAdapter
import com.example.debtcontrol.debtdetail.viewmodel.DebtDetailViewModel
import com.example.debtcontrol.debtdetail.viewmodel.DebtDetailViewModelFactory

class DebtDetailFragment : Fragment() {

    private lateinit var binding: FragmentDebtDetailBinding
    private lateinit var debtDetailViewModel: DebtDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_debt_detail,
                container,
                false
        )

        val application = requireNotNull(this.activity).application
        val arguments = DebtDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = DebtDatabase.getInstance(application).debtDatabaseDao
        val dataSourceHistory = DebtHistoryDatabase.getInstance(application).debtHistoryDatabaseDao
        val viewModelFactory = DebtDetailViewModelFactory(arguments.debtKey, dataSource, dataSourceHistory)

        debtDetailViewModel =
                ViewModelProvider(this, viewModelFactory)
                        .get(DebtDetailViewModel::class.java)

        binding.debtDetailViewModel = debtDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        debtDetailViewModel.dialogIsShowing.observe(viewLifecycleOwner, {
            if (it) {
                showDialog()
            }
        })
        setHasOptionsMenu(true)

        val adapter = DebtHistoryAdapter()
        binding.debtHistory.adapter = adapter

        debtDetailViewModel.history.observe(viewLifecycleOwner, {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        binding.fabPlus.setOnClickListener {
            debtDetailViewModel.getAction(true)
            debtDetailViewModel.startNavigation()
        }

        binding.fabMinus.setOnClickListener {
            debtDetailViewModel.getAction(false)
            debtDetailViewModel.startNavigation()
        }

        debtDetailViewModel.navigateToChangeDebtFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(
                        DebtDetailFragmentDirections.actionDebtDetailFragmentToChangeDebtFragment(arguments.debtKey, debtDetailViewModel.action)
                )
                debtDetailViewModel.navigationDone()
            }
        })

        //Set text gradient color
        binding.sum.paint.shader = Constants.setGradient(binding.sum.textSize)
        binding.collectionDate.paint.shader = Constants.setGradient(binding.collectionDate.textSize)
        binding.repaymentDate.paint.shader = Constants.setGradient(binding.repaymentDate.textSize)

        setFABs()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.debt_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.debtListFragment -> {
                debtDetailViewModel.startDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.dialog_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_yes)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                    debtDetailViewModel.deleteDebt()
                    findNavController().popBackStack()
                    debtDetailViewModel.dialogShowed()
                }
                .setNegativeButton(getString(R.string.dialog_no)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                    debtDetailViewModel.dialogShowed()
                }
                .show()
    }

    private fun setFABs() {
        binding.fabMinus.backgroundTintList = null
        binding.fabMinus.setBackgroundResource(R.drawable.fab_minus_ripple)

        binding.fabPlus.backgroundTintList = null
        binding.fabPlus.setBackgroundResource(R.drawable.fab_plus_ripple)
    }

    override fun onResume() {
        binding.toolbar.title = debtDetailViewModel.getDebt().value?.name //?:"Unknown name"
        super.onResume()
    }
}