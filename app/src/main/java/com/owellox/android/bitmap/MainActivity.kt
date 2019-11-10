package com.owellox.android.bitmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    companion object {
        @Suppress("unused")
        private const val TAG = "MainActivity"
    }

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.home -> {
                button_main.setOnClickListener {
                    getActiveChildFragment()?.onActionDispatched(HomeFragment.DISPATCH_OPEN_GALLERY)
                }
            }
            R.id.view_bitmap -> {
                button_main.setOnClickListener {
                    getActiveChildFragment()?.onActionDispatched(ViewBitmapFragment.DISPATCH_CROP)
                }
            }
            else -> button_main.setOnClickListener(null)
        }
        onUpdateFABIcon(destination)
    }

    private fun onUpdateFABIcon(destination: NavDestination) {
        button_main.hide()
        when (destination.id) {
            R.id.home -> {
                button_main.setImageResource(R.drawable.ic_add_24px)
            }
            R.id.view_bitmap -> {
                button_main.setImageResource(R.drawable.ic_crop_square_24px)
            }
        }
        button_main.show()
    }

    private fun getActiveChildFragment(): AppFragment? {
        return try {
            supportFragmentManager.fragments.first()
                .childFragmentManager.fragments.last() as AppFragment
        } catch (e: NoSuchElementException) {
            null
        }
    }
}