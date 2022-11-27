package com.movingroot.storyfromnowhere.data.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class Photo {
    var base64Str = ""

    var bytes: ByteArray
        get() = Base64.decode(base64Str, Base64.DEFAULT)
        set(value) {
            base64Str = Base64.encodeToString(value, Base64.DEFAULT)
        }

    fun resizeBitmap(): Bitmap {
        val bmp = getBitmap()
        val bmpSize = bmp.toByteArray().size / KILOS_FOR_MEGA
        if (bmpSize > BMP_SIZE_LIMIT) {
            val resizingRatio = BMP_SIZE_LIMIT.toFloat() / bmpSize.toFloat()
            val width = bmp.width * resizingRatio
            val height = bmp.height * resizingRatio
            return resizeBitmap(width.toInt(), height.toInt())
        }
        return bmp
    }

    private fun resizeBitmap(width: Int, height: Int): Bitmap {
        val bmp = getBitmap()
        return Bitmap.createScaledBitmap(bmp, width, height, false)
    }

    private fun getBitmap(): Bitmap {
        val bytesToDecode = bytes
        return BitmapFactory.decodeByteArray(bytesToDecode, 0, bytesToDecode.size)
    }

    private fun Bitmap.toByteArray(): ByteArray {
        ByteArrayOutputStream()
            .apply {
                compress(Bitmap.CompressFormat.PNG, 100, this)
                return toByteArray()
            }
    }

    companion object {
        private const val BMP_SIZE_LIMIT = 1200     //kb
        private const val KILOS_FOR_MEGA = 1024
    }
}
