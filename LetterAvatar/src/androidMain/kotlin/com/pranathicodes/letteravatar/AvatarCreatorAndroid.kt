package com.pranathicodes.letteravatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.text.TextPaint

/**
 * Android-specific implementation of PlatformBitmap.
 */
actual typealias PlatformBitmap = Bitmap

/**
 * Android-specific implementation of PlatformTypeface.
 */
actual typealias PlatformTypeface = Typeface

/**
 * Returns the default font for Android.
 */
actual fun defaultFont(): PlatformTypeface = Typeface.SANS_SERIF

/**
 * Android implementation of AvatarCreator.
 */
actual class AvatarCreator(private val context: Context) : AvatarCreatorInterface {

    private var textSize = 25
    private var size = 180
    private var name = ' '
    private var font: PlatformTypeface = defaultFont()
    private var letterColor: Int = Colors.WHITE
    private var backgroundColor: Int = Colors.GRAY

    override fun setTextSize(textSize: Int) = apply {
        this.textSize = textSize
    }

    override fun setAvatarSize(size: Int) = apply {
        this.size = size
    }

    override fun setLetter(letter: Char) = apply {
        this.name = letter
    }

    override fun setFont(font: PlatformTypeface) = apply {
        this.font = font
    }

    override fun setLetterColor(color: Int) = apply {
        this.letterColor = color
    }

    override fun setBackgroundColor(color: Int) = apply {
        this.backgroundColor = color
    }

    override fun build(): PlatformBitmap {
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
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
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
