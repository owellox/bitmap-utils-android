package com.owellox.android.bitmap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HomeFragment : AppFragment() {
    companion object {
        private const val RC_PICK_IMAGE = 10
        const val DISPATCH_OPEN_GALLERY = 20
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.also {
                val action = HomeFragmentDirections.actionHomeToViewBitmap(it)
                navController.navigate(action)
            }
        }
    }

    override fun onActionDispatched(action: Int) {
        when (action) {
            DISPATCH_OPEN_GALLERY -> {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                }
                startActivityForResult(intent, RC_PICK_IMAGE)
            }
        }
    }
}