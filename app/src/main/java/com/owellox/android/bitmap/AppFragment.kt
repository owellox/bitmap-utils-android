package com.owellox.android.bitmap

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class AppFragment : Fragment() {
    protected val navController by lazy { findNavController() }
    open fun onActionDispatched(action: Int) {}
}