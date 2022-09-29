package com.avacodo.natlextesttask.presentation.searchview

import android.view.Menu

interface SearchViewInitialise {
    fun initSearchView(menu: Menu, searchViewItemID: Int, action: (String) -> Unit)
}