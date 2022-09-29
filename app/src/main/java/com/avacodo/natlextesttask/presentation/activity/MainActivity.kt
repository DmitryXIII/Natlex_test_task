package com.avacodo.natlextesttask.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingFragment

class MainActivity : AppCompatActivity(), NavigationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_navigation_container, WeatherSearchingFragment())
                .commit()
        }
    }

    override fun navigateToTemperatureGraphScreen() {
        //TODO("навигация на экран с графиком температур")
    }
}