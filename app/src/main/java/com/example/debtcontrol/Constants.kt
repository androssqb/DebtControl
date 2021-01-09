package com.example.debtcontrol

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes


object Constants {
    private const val BRIGHT_ORANGE: String = "#FFE1B942"
    private const val SOFT_YELLOW: String = "#FFF9D96A"
    private const val STRONG_ORANGE: String = "#FFCA991A"

    private lateinit var toast: Toast

    const val POLICY: String = "https://debt-control.flycricket.io/privacy.html"
    const val ABOUT: String = "https://debt-control.flycricket.io/terms.html"

    fun setGradient(textSize: Float): LinearGradient {

        return LinearGradient(0f, 0f, 0f,
                textSize,
                intArrayOf(Color.parseColor(BRIGHT_ORANGE),
                        Color.parseColor(SOFT_YELLOW),
                        Color.parseColor(STRONG_ORANGE)),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP)
    }

    fun showToast(context: Context, @StringRes stringId: Int) {
        if (this::toast.isInitialized) {
            toast.cancel()
        }
        toast = Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT)
        toast.show()
    }

    fun closeKeyboard(view: View) {
        val inputMethodManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}