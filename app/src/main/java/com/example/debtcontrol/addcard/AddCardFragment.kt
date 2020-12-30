package com.example.debtcontrol.addcard

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.debtcontrol.Constants
import com.example.debtcontrol.R
import com.example.debtcontrol.addcard.viewmodel.AddCardViewModel
import com.example.debtcontrol.addcard.viewmodel.AddCardViewModelFactory
import com.example.debtcontrol.database.DebtDatabase
import com.example.debtcontrol.database.DebtHistoryDatabase
import com.example.debtcontrol.databinding.FragmentAddCardBinding
import java.text.SimpleDateFormat
import java.util.*

class AddCardFragment : Fragment() {

    private lateinit var binding: FragmentAddCardBinding

    private lateinit var addCardViewModel: AddCardViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_card,
                container,
                false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = DebtDatabase.getInstance(application).debtDatabaseDao
        val dataSourceHistory = DebtHistoryDatabase.getInstance(application).debtHistoryDatabaseDao

        val viewModelFactory = AddCardViewModelFactory(dataSource, dataSourceHistory, application)
        addCardViewModel =
                ViewModelProvider(this, viewModelFactory)
                        .get(AddCardViewModel::class.java)

        binding.addCardViewModel = addCardViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        addCardViewModel.currencyList.observe(viewLifecycleOwner, {
            binding.spinner.setAdapter(ArrayAdapter(requireContext(), R.layout.item_currency, it))
        })
        binding.spinner.setText(resources.getStringArray(R.array.currency)[0])
        binding.spinner.paint.shader = Constants.setGradient(binding.spinner.textSize)
        binding.spinner.setOnClickListener {
            closeKeyboard(it)
        }

        binding.switchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            closeKeyboard(buttonView)
            if (isChecked) {
                binding.giveTVSecond.visibility = TextView.INVISIBLE
                binding.getTV.visibility = TextView.INVISIBLE
                binding.getTVSecond.visibility = TextView.VISIBLE
                binding.giveTV.visibility = TextView.VISIBLE
                addCardViewModel.onSwitchCheckedChange(true)
            } else {
                binding.giveTVSecond.visibility = TextView.VISIBLE
                binding.getTV.visibility = TextView.VISIBLE
                binding.getTVSecond.visibility = TextView.INVISIBLE
                binding.giveTV.visibility = TextView.INVISIBLE
                addCardViewModel.onSwitchCheckedChange(false)
            }
        }


        binding.nameInput.doOnTextChanged { text, _, _, _ ->
            addCardViewModel.onNameChanged(text)
        }

        binding.sumInput.doOnTextChanged { text, _, _, _ ->
            addCardViewModel.onSumChanged(text)
        }

        binding.commentInput.doOnTextChanged { text, _, _, _ ->
            addCardViewModel.onCommentChanged(text)
        }

        binding.spinner.doOnTextChanged { text, _, _, _ ->
            addCardViewModel.onCurrencyChanged(text)
        }

        binding.constLayout.setOnClickListener {
            closeKeyboard(it)
        }

        addCardViewModel.isDatePickerDialogShowing.observe(viewLifecycleOwner, {
            if (it) {
                showDatePikerDialog()
            }
        })

        binding.date.paint.shader = Constants.setGradient(binding.date.textSize)
        binding.giveTVSecond.paint.shader = Constants.setGradient(binding.giveTVSecond.textSize)
        binding.getTVSecond.paint.shader = Constants.setGradient(binding.getTVSecond.textSize)

        setHasOptionsMenu(true)
        initToolbar()
        onClose()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.debtListFragment -> {
                if (
                        binding.nameInput.text.isEmpty() ||
                        binding.sumInput.text.isEmpty() ||
                        binding.commentInput.text.isEmpty() ||
                        dateChangingCheck()
                ) {
                    Constants.showToast(requireContext(), R.string.toast)
                } else {
                    addCardViewModel.onSaveClick()
                    NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                }
                closeKeyboard(requireView())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = null
    }

    private fun onClose() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
            closeKeyboard(it)
        }

    }

    private fun dateChangingCheck(): Boolean {
        val futureDate = binding.date.text
        val currentDate =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(System.currentTimeMillis())
        if (currentDate == futureDate) {
            return true
        }
        return false
    }

    private fun closeKeyboard(view: View) {
        val inputMethodManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
            addCardViewModel.getRepaymentDate(cal.timeInMillis)
            addCardViewModel.datePikerDialogShowed()
        }, yr, month, day)

        display.setOnCancelListener {
            addCardViewModel.datePikerDialogShowed()
        }
        display.datePicker.minDate = System.currentTimeMillis()
        display.show()
    }
}