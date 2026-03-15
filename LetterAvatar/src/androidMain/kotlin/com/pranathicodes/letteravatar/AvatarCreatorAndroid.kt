package com.pranathicodes.letteravatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * Android-specific implementation of PlatformBitmap.
 */
actual typealias PlatformBitmap = Bitmap

/**
 * Android implementation of AvatarCreator.
 */
actual class AvatarCreator(private val context: Context) : AvatarCreatorBase() {

    override fun avatarImageGenerate(
        imageSize: Int,
        name: String,
        textSize: Int,
        fontFamily: FontFamily?,
        letterColor: Int,
        backgroundColor: Int,
        density: Double,
    ): PlatformBitmap {
        val label = name.uppercase().ifEmpty { "-" }.take(1)

        val scaledSize = (imageSize * density).toInt()
        val scaledTextSize = (scaledSize * (textSize / 100.0)).toFloat()

        val bitmap = Bitmap.createBitmap(scaledSize, scaledSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw background
        val bgPaint = Paint().apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, scaledSize.toFloat(), scaledSize.toFloat(), bgPaint)

        // Resolve Typeface from FontFamily
        val typeface = if (fontFamily != null) {
            val resolver = androidx.compose.ui.text.font.createFontFamilyResolver(context)
            val resolved = resolver.resolve(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
            resolved.value as Typeface
        } else {
            Typeface.SANS_SERIF
        }

        // Draw text
        val textPaint = TextPaint().apply {
            color = letterColor
            this.typeface = typeface
            this.textSize = scaledTextSize
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }

        val xPos = scaledSize / 2f
        val yPos = (scaledSize / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f)

        canvas.drawText(label, xPos, yPos, textPaint)

        return bitmap
    }
}
