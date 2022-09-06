package com.shop_user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.shop_user.R
import com.shop_user.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        setupWithNavController(binding.bottomNav, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id)
            {
                R.id.homeFragment, R.id.profileFragment -> binding.bottomNav.visibility = View.VISIBLE
                else -> binding.bottomNav.visibility = View.GONE
            }

        }
    }
}