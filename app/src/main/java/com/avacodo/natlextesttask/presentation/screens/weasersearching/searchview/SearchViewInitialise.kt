package com.avacodo.natlextesttask.presentation.screens.weasersearching.searchview

import android.view.MenuItem
import androidx.appcompat.widget.SearchView

interface SearchViewInitialise {
    fun initSearchView(
        searchView: SearchView,
        actionOnQueryChange: (String) -> Unit,
        actionOnQuerySubmit: (String) -> Unit,
    )

    fun setSavedQuery(searchMenuItem: MenuItem, searchView: SearchView, savedQuery: String)
}