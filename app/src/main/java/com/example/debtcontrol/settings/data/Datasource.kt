package com.example.debtcontrol.settings.data

import com.example.debtcontrol.R
import com.example.debtcontrol.settings.model.Settings

class Datasource {

    fun loadSettings(): List<Settings> {
        return listOf(
                Settings(R.drawable.ic_policy, R.string.policy),
                Settings(R.drawable.ic_about, R.string.about),
                Settings(R.drawable.ic_rate, R.string.rate),
                Settings(R.drawable.ic_share, R.string.share)
        )
    }
}