package com.erik.jetpackpro.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBar {
    fun showSnackBar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, length).show()
    }
}