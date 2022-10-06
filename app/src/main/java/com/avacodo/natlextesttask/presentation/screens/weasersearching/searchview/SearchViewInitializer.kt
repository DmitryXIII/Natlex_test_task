package com.avacodo.natlextesttask.presentation.screens.weasersearching.searchview

import android.view.Menu
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView
import com.avacodo.natlextesttask.presentation.extensions.hideKeyboard

class SearchViewInitializer : SearchViewInitialise {
    override fun initSearchView(menu: Menu, @IdRes searchViewItemID: Int, action: (String) -> Unit) {
        (menu.findItem(searchViewItemID).actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    action.invoke(query)
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String) = true
            })
        }
    }
}