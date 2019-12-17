package com.owellox.bitmap

import android.graphics.BitmapFactory

class BitmapFactoryUtils private constructor() {
    companion object {
        /**
         * Determines sampling rate that can be used for image sub-sampling to save memory. The returned
         * value is optimized for the width & height to avoid degrading actual image quality.
         *
         * @param options [BitmapFactory.Options] object that contains the decoded image bounds.
         * @param reqWidth Requested width for sub-sampling.
         * @param reqHeight Requested height for sub-sampling.
         */
        @Deprecated("Use Kotlin extension View.getInSampleSize() instead.")
        fun computeInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int, reqHeight: Int
        ): Int {
            val (height: Int, width: Int) = options.run { outHeight to outWidth }
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }
}