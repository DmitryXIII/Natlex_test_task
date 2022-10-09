package com.avacodo.natlextesttask.presentation.screens.weasersearching.searchview

import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.avacodo.natlextesttask.presentation.extensions.hideKeyboard

class SearchViewInitializer : SearchViewInitialise {
    override fun initSearchView(
        searchView: SearchView,
        actionOnQueryChange: (String) -> Unit,
        actionOnQuerySubmit: (String) -> Unit,
    ) {
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    actionOnQuerySubmit.invoke(query)
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    actionOnQueryChange.invoke(newText)
                    return true
                }
            })
        }
    }

    override fun setSavedQuery(
        searchMenuItem: MenuItem,
        searchView: SearchView,
        savedQuery: String,
    ) {
        if (savedQuery.isNotEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(savedQuery, true)
        }
    }
}