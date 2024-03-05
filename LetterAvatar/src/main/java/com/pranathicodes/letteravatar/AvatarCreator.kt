package com.pranathicodes.letteravatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.text.TextPaint

class AvatarCreator(private val context: Context) {

    private var textSize = 25
    private var size = 180
    private var name = ' '
    private var font = Typeface.SANS_SERIF
    private var letterColor = Color.WHITE
    private var backgroundColor = Color.GRAY


    fun setTextSize(textSize: Int) = apply {
        this.textSize = textSize
    }

    fun setAvatarSize(int: Int) = apply {
        this.size = int
    }

    fun setLetter(label: Char) = apply {
        this.name = label
    }

    fun setFont(fontFamily: Typeface) = apply {
        this.font = fontFamily
    }

    fun setLetterColor(color: Int) = apply {
        this.letterColor = color
    }

    fun setBackgroundColor(color: Int) = apply {
        this.backgroundColor = color
    }

    fun build(): Bitmap {
        return avatarImageGenerate(
            size,
            name.toString(),
            textSize,
            font,
            letterColor,
            backgroundColor
        )
    }

    private fun avatarImageGenerate(
        size: Int,
        name: String,
        textSize: Int,
        typeface: Typeface,
        letterColor: Int,
        backgroundColor: Int
    ): Bitmap {
        val painter = Paint()
        val label = name.uppercase().ifEmpty { "-" }
        val textPaint = textPainter(letterColor, typeface, textSize)
        painter.color = backgroundColor

        val areaRect = Rect(0, 0, size, size)
        val bitmap = Bitmap.createBitmap(size, size, ARGB_8888)
        val canvas = Canvas(bitmap)

        val bounds = RectF(areaRect)
        bounds.right = textPaint.measureText(label, 0, 1)
        bounds.bottom = textPaint.descent() - textPaint.ascent()

        bounds.left += (areaRect.width() - bounds.right) / 2.0f
        bounds.top += (areaRect.height() - bounds.bottom) / 2.0f

        canvas.drawRect(areaRect, painter)
        canvas.drawText(label, bounds.left, bounds.top - textPaint.ascent(), textPaint)
        return bitmap

    }

    private fun textPainter(color: Int, typeface: Typeface, textSize: Int): TextPaint {
        val textPaint = TextPaint()
        textPaint.textSize = textSize * context.resources.displayMetrics.density
        textPaint.color = color
        textPaint.typeface = typeface
        return textPaint
    }

}

