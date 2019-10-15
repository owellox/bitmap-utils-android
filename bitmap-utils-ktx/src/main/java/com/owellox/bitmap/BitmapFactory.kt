@file:Suppress("unused")

package com.owellox.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.io.FileDescriptor
import java.io.InputStream

/**
 * Decode a bitmap from the specified byte array.
 *
 * @param offset Offset into image data for where the decoder should begin parsing.
 * @param length The number of bytes, beginning at [offset], to parse.
 * @param options Options that control down-sampling and whether the image should be completely
 * decoded, or just its size returned.
 *
 * @return The decoded bitmap, or null if the image data could not be decoded, or if [options]
 * is supplied and requests only the size to be returned.
 *
 * @throws IllegalArgumentException if [BitmapFactory.Options.inPreferredConfig] is
 * [Bitmap.Config.HARDWARE] and [BitmapFactory.Options.inMutable] is set, if the specified
 * color space is not ICC parametric curve.
 */
fun ByteArray.decodeBitmap(
    offset: Int,
    length: Int,
    options: BitmapFactory.Options? = null
): Bitmap? {
    return BitmapFactory.decodeByteArray(this, offset, length, options)
}

/**
 * Decode a bitmap from the [FileDescriptor]. The position within the descriptor will not be
 * changed when this returns, so the [FileDescriptor] can be used again as-is.
 *
 * @param outPadding If not null, return the padding rect for the bitmap if it exists,
 * otherwise set padding to [-1, -1, -1, -1]. If no bitmap is returned, then padding is
 * unchanged.
 * @param options Options that control down-sampling and whether the image should be completely
 * decoded, or just its size returned.
 *
 * @return The decoded bitmap, or null if the image data could not be decoded, or if [options]
 * is supplied and requests only the size to be returned.
 *
 * @throws IllegalArgumentException if [BitmapFactory.Options.inPreferredConfig] is
 * [Bitmap.Config.HARDWARE] and [BitmapFactory.Options.inMutable] is set, if the specified
 * color space is not ICC parametric curve.
 */
fun FileDescriptor.decodeBitmap(
    outPadding: Rect? = null,
    options: BitmapFactory.Options? = null
): Bitmap? {
    return BitmapFactory.decodeFileDescriptor(this, outPadding, options)
}

/**
 * Decode an [InputStream] into a [Bitmap]. Returns null if the [InputStream] cannot be used to
 * decode a [Bitmap]. The [InputStream]'s position will be wherever it was after the encoded
 * data was read.
 *
 * @param outPadding If not null, return the padding rect for the bitmap if it exists,
 * otherwise set padding to [-1, -1, -1, -1]. If no bitmap is returned, then padding is
 * unchanged.
 * @param options Options that control down-sampling and whether the image should be completely
 * decoded, or just its size returned.
 *
 * @return The decoded bitmap, or null if the image data could not be decoded, or if [options]
 * is supplied and requests only the size to be returned.
 *
 * @throws IllegalArgumentException if [BitmapFactory.Options.inPreferredConfig] is
 * [Bitmap.Config.HARDWARE] and [BitmapFactory.Options.inMutable] is set, if the specified
 * color space is not ICC parametric curve.
 */
fun InputStream.decodeBitmap(
    outPadding: Rect? = null,
    options: BitmapFactory.Options? = null
): Bitmap? {
    return BitmapFactory.decodeStream(this, outPadding, options)
}

/**
 * Synonym for opening the given resource and calling [InputStream.decodeBitmap].
 *
 * @param resId The resource id of the image data.
 * @param options Options that control down-sampling and whether the image should be completely
 * decoded, or just its size returned.
 *
 * @return The decoded bitmap, or null if the image data could not be decoded, or if [options]
 * is supplied and requests only the size to be returned.
 *
 * @throws IllegalArgumentException if [BitmapFactory.Options.inPreferredConfig] is
 * [Bitmap.Config.HARDWARE] and [BitmapFactory.Options.inMutable] is set, if the specified
 * color space is not ICC parametric curve.
 */
fun Resources.decodeBitmap(resId: Int, options: BitmapFactory.Options? = null): Bitmap? {
    return BitmapFactory.decodeResource(this, resId, options)
}