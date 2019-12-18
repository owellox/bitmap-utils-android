package com.owellox.bitmap

import android.graphics.Bitmap
import androidx.core.graphics.scale
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