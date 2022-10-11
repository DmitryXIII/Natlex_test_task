package com.avacodo.natlextesttask.presentation.screens.weasersearching.backgrounddrawer

import android.view.View

abstract class BaseBackgroundDrawer: BackgroundDrawer {
    abstract val colorResourceID: Int

    override fun setLayoutBackground(view: View) {
        view.setBackgroundColor(view.context.getColor(colorResourceID))
    }
}