package com.owellox.android.bitmap

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.owellox.bitmap.BitmapFactoryUtils
import com.owellox.bitmap.cropCenterSquare
import com.owellox.bitmap.decodeBitmap
import kotlinx.android.synthetic.main.view_bitmap.view.*

class ViewBitmapFragment : AppFragment() {
    companion object {
        const val DISPATCH_CROP = 100
    }

    private val args by navArgs<ViewBitmapFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_bitmap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val uri = args.imageUri
        val descriptor = requireContext().contentResolver.openFileDescriptor(uri, "r") ?: return
        view.image_cropped.post {
            val bitmap = BitmapFactory.Options().run {
                inJustDecodeBounds = true
                descriptor.fileDescriptor.decodeBitmap(options = this)
                inSampleSize = BitmapFactoryUtils.computeInSampleSize(
                    this,
                    view.image_cropped.width,
                    view.image_cropped.height
                )
                inJustDecodeBounds = false
                descriptor.fileDescriptor.decodeBitmap(options = this)
            }
            view.image_cropped.setImageBitmap(bitmap)
        }
    }

    override fun onActionDispatched(action: Int) {
        when (action) {
            DISPATCH_CROP -> {
                val uri = args.imageUri
                val descriptor =
                    requireContext().contentResolver.openFileDescriptor(uri, "r") ?: return
                view?.let {
                    it.image_cropped.post {
                        val bitmap = BitmapFactory.Options().run {
                            inJustDecodeBounds = true
                            descriptor.fileDescriptor.decodeBitmap(options = this)
                            inSampleSize = BitmapFactoryUtils.computeInSampleSize(
                                this,
                                it.image_cropped.width,
                                it.image_cropped.height
                            )
                            inJustDecodeBounds = false
                            descriptor.fileDescriptor.decodeBitmap(options = this)
                                ?.cropCenterSquare()
                        }
                        it.image_cropped.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}