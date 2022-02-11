package ru.netology.nmedia.utils

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    fun reductionInNumbers(count: Int): String {
        val formatCount = when {
            count in 1000..9999 -> {
                String.format("%.1fK", count / 1000.0)
            }
            count in 10000..999999 -> {
                String.format("%dK", count / 1000)
            }
            count > 1000000 -> {
                String.format("%.1fM", count / 1000000.0)
            }

            else -> {
                count.toString()
            }
        }
        return formatCount
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}