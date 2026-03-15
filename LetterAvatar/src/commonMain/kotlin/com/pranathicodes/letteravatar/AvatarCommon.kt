package com.pranathicodes.letteravatar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp

/**
 * Interface for creating letter avatars across platforms.
 */
interface AvatarCreatorInterface {
    /**
     * Sets the text size as a percentage of the avatar size (0-100).
     * Default is 40.
     */
    fun setTextSize(textSize: Int): AvatarCreatorInterface
    fun setAvatarSize(size: Int): AvatarCreatorInterface
    fun setLetter(letter: Char): AvatarCreatorInterface
    
    /**
     * Set font family from Compose Resources.
     */
    fun setFontFamily(fontFamily: FontFamily): AvatarCreatorInterface
    
    fun setLetterColor(color: Color): AvatarCreatorInterface
    fun setBackgroundColor(color: Color): AvatarCreatorInterface
    fun build(): ImageBitmap
}

/**
 * Common implementation using Compose Canvas and DrawScope.
 */
class AvatarCreator(
    private val textMeasurer: TextMeasurer,
    private val density: Density
) : AvatarCreatorInterface {
    private var textSize = 40
    private var size = 180
    private var name = ' '
    private var fontFamily: FontFamily? = null
    private var letterColor: Color = Color.White
    private var backgroundColor: Color = Color.Gray

    override fun setTextSize(textSize: Int): AvatarCreatorInterface {
        this.textSize = textSize
        return this
    }

    override fun setAvatarSize(size: Int): AvatarCreatorInterface {
        this.size = size
        return this
    }

    override fun setLetter(letter: Char): AvatarCreatorInterface {
        this.name = letter
        return this
    }

    override fun setFontFamily(fontFamily: FontFamily): AvatarCreatorInterface {
        this.fontFamily = fontFamily
        return this
    }

    override fun setLetterColor(color: Color): AvatarCreatorInterface {
        this.letterColor = color
        return this
    }

    override fun setBackgroundColor(color: Color): AvatarCreatorInterface {
        this.backgroundColor = color
        return this
    }

    override fun build(): ImageBitmap {
        val label = name.uppercase().ifEmpty { "-" }.take(1)
        val scaledSize = (size * density.density).toInt()
        val imageBitmap = ImageBitmap(scaledSize, scaledSize)
        val canvas = Canvas(imageBitmap)
        
        val drawScope = CanvasDrawScope()
        val scaledTextSize = (size * (textSize / 100.0)).sp

        drawScope.draw(
            density = density,
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = Size(scaledSize.toFloat(), scaledSize.toFloat())
        ) {
            // Draw background
            drawRect(color = backgroundColor)

            // Measure and draw text
            val textLayoutResult: TextLayoutResult = textMeasurer.measure(
                text = AnnotatedString(label),
                style = TextStyle(
                    color = letterColor,
                    fontSize = scaledTextSize,
                    fontFamily = fontFamily
                ),
                constraints = Constraints(
                    maxWidth = scaledSize,
                    maxHeight = scaledSize
                )
            )

            val xPos = (scaledSize - textLayoutResult.size.width) / 2f
            val yPos = (scaledSize - textLayoutResult.size.height) / 2f

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(xPos, yPos)
            )
        }

        return imageBitmap
    }
}
