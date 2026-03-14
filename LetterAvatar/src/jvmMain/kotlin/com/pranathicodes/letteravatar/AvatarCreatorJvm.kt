package com.pranathicodes.letteravatar

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
 * JVM-specific implementation of PlatformBitmap using BufferedImage.
 */
actual typealias PlatformBitmap = BufferedImage

/**
 * JVM-specific implementation of PlatformTypeface using Font.
 */
actual typealias PlatformTypeface = Font

/**
 * Returns the default font for JVM (SansSerif).
 */
actual fun defaultFont(): PlatformTypeface = Font("SansSerif", Font.PLAIN, 12)

/**
 * JVM implementation of AvatarCreator using Java2D/AWT.
 */
actual class AvatarCreator : AvatarCreatorInterface {

    private var textSize = 25
    private var size = 180
    private var name = ' '
    private var font: PlatformTypeface = defaultFont()
    private var letterColor: Int = Colors.WHITE
    private var backgroundColor: Int = Colors.GRAY
    private var density: Float = 1.0f

    /**
     * Set the display density for scaling text size.
     * Default is 1.0f for desktop JVM environments.
     */
    fun setDensity(density: Float) = apply {
        this.density = density
    }

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
            backgroundColor,
            density
        )
    }

    private fun avatarImageGenerate(
        size: Int,
        name: String,
        textSize: Int,
        font: Font,
        letterColor: Int,
        backgroundColor: Int,
        density: Float
    ): BufferedImage {
        val label = name.uppercase().ifEmpty { "-" }

        // Create buffered image with transparency
        val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val g2d = image.createGraphics()

        // Enable anti-aliasing for smooth text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)

        // Fill background
        g2d.color = Color(backgroundColor, true)
        g2d.fillRect(0, 0, size, size)

        // Setup font
        val scaledTextSize = (textSize * density).toInt()
        g2d.font = font.deriveFont(scaledTextSize.toFloat())

        // Calculate text bounds
        val fm = g2d.fontMetrics
        val textWidth = fm.stringWidth(label.substring(0, 1.coerceAtMost(label.length)))
        val textHeight = fm.height

        // Calculate center position
        val x = (size - textWidth) / 2f
        val y = (size - textHeight) / 2f + fm.ascent

        // Draw text
        g2d.color = Color(letterColor, true)
        g2d.drawString(label.substring(0, 1.coerceAtMost(label.length)), x, y)

        // Dispose graphics context
        g2d.dispose()

        return image
    }
}
