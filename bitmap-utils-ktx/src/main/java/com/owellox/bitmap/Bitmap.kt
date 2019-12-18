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

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileDescriptor
import java.io.InputStream
import kotlin.math.min

/**
 * Crops and returns exactly the center of the Bitmap.
 *
 * @param sideLength Optional. The target length of all sides of the square.
 * @param filter Whether or not bilinear filtering should be used when scaling the bitmap. If this
 * is true then bilinear filtering will be used when scaling which has better image quality at the
 * cost of worse performance. If this is false then nearest-neighbor scaling is used instead which
 * will have worse image quality but is faster. Recommended default is to set filter to 'true' as
 * the cost of bilinear filtering is typically minimal and the improved image quality is
 * significant.
 * @return The new cropped, square Bitmap.
 */
fun Bitmap.cropCenterSquare(sideLength: Int? = null, filter: Boolean = true): Bitmap {
    val minLength = min(width, height)
    val left = ((width / 2f) - (minLength / 2f)).toInt()
    val top = ((height / 2f) - (minLength / 2f)).toInt()
    var bitmap = Bitmap.createBitmap(this, left, top, minLength, minLength)
    sideLength?.also {
        bitmap = Bitmap.createScaledBitmap(bitmap, sideLength, sideLength, filter)
        bitmap = bitmap.scale(sideLength, sideLength, filter)
    }
    return bitmap
}

/**
 * Fixes the orientation of this Bitmap by reading EXIF values from the [file]. If it is
 * found to not requiring any reorientation, then the source Bitmap is returned.
 *
 * @param file The source file of the Bitmap.
 * @return The reoriented Bitmap, or the source Bitmap if there's no reorientation required.
 */
fun Bitmap.fixOrientation(file: File): Bitmap {
    val exif = ExifInterface(file)
    val swapDimension =
        when (exif.rotationDegrees) {
            0 -> return this
            90 -> true
            180 -> false
            270 -> true
            else -> return this
        }
    val matrix = Matrix()
    matrix.postRotate(exif.rotationDegrees.toFloat())
    return Bitmap.createBitmap(
        this,
        0, 0,
        if (!swapDimension) width else height,
        if (!swapDimension) height else width,
        matrix, true
    )
}

/**
 * Fixes the orientation of this Bitmap by reading EXIF values from the [fileDescriptor]. If it is
 * found to not requiring any reorientation, then the source Bitmap is returned.
 *
 * @param fileDescriptor The source file-descriptor of the Bitmap.
 * @return The reoriented Bitmap, or the source Bitmap if there's no reorientation required.
 */
fun Bitmap.fixOrientation(fileDescriptor: FileDescriptor): Bitmap {
    val exif = ExifInterface(fileDescriptor)
    val swapDimension =
        when (exif.rotationDegrees) {
            0 -> return this
            90 -> true
            180 -> false
            270 -> true
            else -> return this
        }
    val matrix = Matrix()
    matrix.postRotate(exif.rotationDegrees.toFloat())
    return Bitmap.createBitmap(
        this,
        0, 0,
        if (!swapDimension) width else height,
        if (!swapDimension) height else width,
        matrix, true
    )
}

/**
 * Fixes the orientation of this Bitmap by reading EXIF values from the [inputStream]. If it is
 * found to not requiring any reorientation, then the source Bitmap is returned.
 *
 * @param inputStream The source input-stream of the Bitmap.
 * @return The reoriented Bitmap, or the source Bitmap if there's no reorientation required.
 */
fun Bitmap.fixOrientation(inputStream: InputStream): Bitmap {
    val exif = ExifInterface(inputStream)
    val swapDimension =
        when (exif.rotationDegrees) {
            0 -> return this
            90 -> true
            180 -> false
            270 -> true
            else -> return this
        }
    val matrix = Matrix()
    matrix.postRotate(exif.rotationDegrees.toFloat())
    return Bitmap.createBitmap(
        this,
        0, 0,
        if (!swapDimension) width else height,
        if (!swapDimension) height else width,
        matrix, true
    )
}