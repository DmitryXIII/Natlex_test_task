package com.avacodo.natlextesttask.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.avacodo.natlextesttask.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun View.hideKeyboard(): Boolean {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.showAlertDialogWithoutNegativeButton(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positiveButton: Int = R.string.default_alert_positive_button,
    onPositiveAction: (dialog: DialogInterface) -> Unit,
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(getString(title))
        .setMessage(getString(message))
        .setPositiveButton(getString(positiveButton)) { dialog, _ ->
            onPositiveAction.invoke(dialog)
        }
        .show()
}

fun Activity.showAlertDialogWithNegativeButton(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positiveButton: Int = R.string.default_alert_positive_button,
    @StringRes negativeButton: Int = R.string.default_alert_negative_button,
    onPositiveAction: (dialog: DialogInterface) -> Unit,
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(getString(title))
        .setMessage(getString(message))
        .setPositiveButton(getString(positiveButton)) { dialog, _ ->
            onPositiveAction.invoke(dialog)
        }
        .setNegativeButton(getString(negativeButton)) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}



