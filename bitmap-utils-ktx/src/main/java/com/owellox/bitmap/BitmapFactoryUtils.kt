/*
 * Copyright 2019 Owellox
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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