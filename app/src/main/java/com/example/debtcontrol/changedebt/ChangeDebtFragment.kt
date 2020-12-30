package com.example.debtcontrol.changedebt

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.debtcontrol.Constants
import com.example.debtcontrol.R
import com.example.debtcontrol.changedebt.viewmodel.ChangeDebtViewModel
import com.example.debtcontrol.changedebt.viewmodel.ChangeDebtViewModelFactory
import com.example.debtcontrol.database.DebtDatabase
import com.example.debtcontrol.database.DebtHistoryDatabase
import com.example.debtcontrol.databinding.FragmentChangeDebtBinding
import java.util.*


class ChangeDebtFragment : Fragment() {

    private lateinit var binding: FragmentChangeDebtBinding
    private lateinit var changeDebtViewModel: ChangeDebtViewModel
    private lateinit var toast: Toast

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_change_debt, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
            closeKeyboard(it)
        }

        val application = requireNotNull(this.activity).application
        val arguments = ChangeDebtFragmentArgs.fromBundle(requireArguments())

        val dataSource = DebtDatabase.getInstance(application).debtDatabaseDao
        val dataSourceHistory = DebtHistoryDatabase.getInstance(application).debtHistoryDatabaseDao

        val viewModelFactory = ChangeDebtViewModelFactory(arguments.debtKey, arguments.action, dataSource, dataSourceHistory)
        changeDebtViewModel = ViewModelProvider(this, viewModelFactory)
                .get(ChangeDebtViewModel::class.java)

        binding.changeDebtViewModel = changeDebtViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        binding.constLayout.setOnClickListener {
            closeKeyboard(it)
        }

        changeDebtViewModel.isDatePickerDialogShowing.observe(viewLifecycleOwner, {
            if (it) {
                showDatePikerDialog()
            }
        })

        changeDebtViewModel.isDebtChanging.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(
                        ChangeDebtFragmentDirections.actionChangeDebtFragmentToDebtDetailFragment(arguments.debtKey))

                changeDebtViewModel.navigationDone()
            }
        })

        changeDebtViewModel.took.observe(viewLifecycleOwner, {
            if (it) {
                binding.toolbar.title = getString(R.string.increase_in_debt)
            } else {
                binding.toolbar.title = getString(R.string.debt_reduction)
            }
        })

        binding.sumInput.doOnTextChanged { text, _, _, _ ->
            changeDebtViewModel.onSumChanged(text)
        }


        binding.commentInput.doOnTextChanged { text, _, _, _ ->
            changeDebtViewModel.onCommentChanged(text)
        }

        binding.sum.doOnTextChanged { text, _, _, _ ->
            changeDebtViewModel.getOldSum(text)
        }

        changeDebtViewModel.isSumNotCorrect.observe(viewLifecycleOwner, {
            if (it) {
                Constants.showToast(requireContext(), R.string.error_text)
                changeDebtViewModel.sumCorrect()
            }
        })

        //Set text gradient color
        binding.sum.paint.shader = Constants.setGradient(binding.sum.textSize)
        binding.date.paint.shader = Constants.setGradient(binding.date.textSize)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.debt_change_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.debtDetailFragment -> {
                if (
                        binding.sumInput.text.isEmpty() ||
                        binding.commentInput.text.isEmpty()
                ) {
                    Constants.showToast(requireContext(), R.string.toast_change)
                } else {
                    if (changeDebtViewModel.sumCheck()) {
                        changeDebtViewModel.onSaveClick()
                        changeDebtViewModel.navigateToDebtDetails()
                    }
                }
                closeKeyboard(requireView())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun closeKeyboard(view: View) {
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showDatePikerDialog() {
        val cal = Calendar.getInstance()
        val yr = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val display = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            changeDebtViewModel.getDate(cal.timeInMillis)
            changeDebtViewModel.datePikerDialogShowed()
        }, yr, month, day)

        display.setOnCancelListener {
            changeDebtViewModel.datePikerDialogShowed()
        }
        display.datePicker.minDate = changeDebtViewModel.getDebt().value!!.debtCollectionDate
        display.show()
    }

    private fun showToast(@StringRes stringId: Int) {
        if (this::toast.isInitialized) {
            toast.cancel()
        }
        toast = Toast.makeText(requireContext(), getString(stringId), Toast.LENGTH_LONG)
        toast.show()
    }
}