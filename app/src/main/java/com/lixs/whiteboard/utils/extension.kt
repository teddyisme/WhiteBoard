package com.lixs.whiteboard.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View


fun View.convertBitmap(): Bitmap {
    val w = width
    val h = height
    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val c = Canvas(bmp)

    c.drawColor(Color.WHITE)

    layout(0, 0, w, h)
    draw(c)
    return bmp
}
