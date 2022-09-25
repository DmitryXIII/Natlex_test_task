package com.avacodo.natlextesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avacodo.natlextesttask.R

class MainActivity : AppCompatActivity(), NavigationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToTemperatureGraphScreen() {
        //TODO("навигация на экран с графиком температур")
    }
}