package com.avacodo.natlextesttask.presentation.screens.weasersearching.searchview

import android.view.Menu

interface SearchViewInitialise {
    fun initSearchView(menu: Menu, searchViewItemID: Int, action: (String) -> Unit)
}