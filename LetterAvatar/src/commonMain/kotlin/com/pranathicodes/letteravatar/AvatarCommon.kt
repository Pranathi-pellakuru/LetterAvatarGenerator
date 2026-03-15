package com.pranathicodes.letteravatar

import androidx.compose.ui.text.font.FontFamily

/**
 * Platform-agnostic representation of an image/bitmap.
 * Platform implementations will wrap their native image types.
 */
expect class PlatformBitmap

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
    
    fun setLetterColor(color: Int): AvatarCreatorInterface
    fun setBackgroundColor(color: Int): AvatarCreatorInterface
    fun build(): PlatformBitmap
}

/**
 * Shared implementation that avoids duplicated state and builder methods.
 */
abstract class AvatarCreatorBase : AvatarCreatorInterface {
    protected var textSize = 40
    protected var size = 180
    protected var name = ' '
    protected var fontFamily: FontFamily? = null
    protected var letterColor: Int = Colors.WHITE
    protected var backgroundColor: Int = Colors.GRAY

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

    override fun setLetterColor(color: Int): AvatarCreatorInterface {
        this.letterColor = color
        return this
    }

    override fun setBackgroundColor(color: Int): AvatarCreatorInterface {
        this.backgroundColor = color
        return this
    }

    override fun build(): PlatformBitmap {
        return avatarImageGenerate(
            imageSize = size,
            name = name.toString(),
            textSize = textSize,
            fontFamily = fontFamily,
            letterColor = letterColor,
            backgroundColor = backgroundColor,
            density = imageDensity()
        )
    }

    protected open fun imageDensity(): Double = 1.0

    protected abstract fun avatarImageGenerate(
        imageSize: Int,
        name: String,
        textSize: Int,
        fontFamily: FontFamily?,
        letterColor: Int,
        backgroundColor: Int,
        density: Double
    ): PlatformBitmap
}

/**
 * Platform-specific avatar creator implementation.
 */
expect class AvatarCreator : AvatarCreatorBase

/**
 * Color constants for common colors.
 */
object Colors {
    const val WHITE: Int = 0xFFFFFFFF.toInt()
    const val GRAY: Int = 0xFF808080.toInt()
}
