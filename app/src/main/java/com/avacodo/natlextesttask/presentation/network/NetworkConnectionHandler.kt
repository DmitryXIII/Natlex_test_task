package com.avacodo.natlextesttask.presentation.network

import android.app.Activity
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.extensions.showAlertDialogWithoutNegativeButton

class NetworkConnectionHandler : ConnectionHandler {

    override fun handleInternetConnectionStatus(
        activity: Activity,
        status: NetworkConnectionStatus,
    ) {
        if (status != NetworkConnectionStatus.AVAILABLE) {
            showNoConnectionAlert(activity)
        }
    }

    override fun showNoConnectionAlert(activity: Activity) {
        activity.showAlertDialogWithoutNegativeButton(
            R.string.alert_title_if_internet_is_off,
            R.string.alert_message_if_internet_is_off,
        ) { dialog ->
            dialog.dismiss()
        }
    }
}