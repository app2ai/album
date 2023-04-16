package com.example.dynaimage

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.dynaimage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
        //used nav controller for fragment navigation
        // val navHostFragment =
        //     supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // val myNavController = navHostFragment.navController
        //
        // myNavController.addOnDestinationChangedListener { cntr, dest, args ->
        //     when (dest.id) {
        //         R.id.albumListFragment -> {
        //             myNavController.popBackStack(R.id.albumListFragment, true)
        //         }
        //     }
        // }
    }

    private fun isTablet(): Boolean {
        return ((this.resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }
}