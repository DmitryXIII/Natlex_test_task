package com.avacodo.natlextesttask.presentation.screens.weasersearching.searchview

import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.avacodo.natlextesttask.presentation.extensions.hideKeyboard

class SearchViewInitializer : SearchViewInitialise {

    private var isKeyboardVisible = true

    override fun initSearchView(
        searchView: SearchView,
        actionOnQueryChange: (String) -> Unit,
        actionOnQuerySubmit: (String) -> Unit,
    ) {
        searchView.apply {
            maxWidth = Int.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    actionOnQuerySubmit.invoke(query)
                    hideKeyboard()
                    isKeyboardVisible = false
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    actionOnQueryChange.invoke(newText)
                    isKeyboardVisible = true
                    return true
                }
            })
        }
    }

    override fun setSavedQuery(
        searchMenuItem: MenuItem,
        searchView: SearchView,
        savedQuery: String
    ) {
        if (savedQuery.isNotEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(savedQuery, true)
            if(!isKeyboardVisible) {
                searchView.clearFocus()
            }
        }
    }
}