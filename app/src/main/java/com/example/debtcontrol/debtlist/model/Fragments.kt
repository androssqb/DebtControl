package com.example.debtcontrol.debtlist.model

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

//ViewPager2 scrollable Fragment

data class Fragments(
        val fragments: Fragment,
        @StringRes val tabTitleResourceId: Int
)