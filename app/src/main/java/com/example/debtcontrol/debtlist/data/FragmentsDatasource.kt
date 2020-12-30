package com.example.debtcontrol.debtlist.data

import com.example.debtcontrol.R
import com.example.debtcontrol.debtlist.model.Fragments
import com.example.debtcontrol.mydebtors.MyDebtorsFragment
import com.example.debtcontrol.mydebts.MyDebtsFragment

//ViewPager2 scrollable Fragments list

class FragmentsDatasource {

    fun loadFragments(): List<Fragments> {
        return listOf(
                Fragments(MyDebtorsFragment(), R.string.first_tab_text),
                Fragments(MyDebtsFragment(), R.string.second_tab_text)
        )
    }
}