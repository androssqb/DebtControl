package com.example.debtcontrol.settings.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Settings(
    @DrawableRes val iconResourceId: Int,
    @StringRes val descResourceId: Int
)