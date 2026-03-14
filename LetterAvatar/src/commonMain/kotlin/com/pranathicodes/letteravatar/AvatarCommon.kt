package com.pranathicodes.letteravatar

/**
 * Platform-agnostic representation of an image/bitmap.
 * Platform implementations will wrap their native image types.
 */
expect class PlatformBitmap

/**
 * Platform-agnostic representation of a font/typeface.
 */
expect class PlatformTypeface

/**
 * Interface for creating letter avatars across platforms.
 */
interface AvatarCreatorInterface {
    fun setTextSize(textSize: Int): AvatarCreatorInterface
    fun setAvatarSize(size: Int): AvatarCreatorInterface
    fun setLetter(letter: Char): AvatarCreatorInterface
    fun setFont(font: PlatformTypeface): AvatarCreatorInterface
    fun setLetterColor(color: Int): AvatarCreatorInterface
    fun setBackgroundColor(color: Int): AvatarCreatorInterface
    fun build(): PlatformBitmap
}

/**
 * Default font for the platform.
 */
expect fun defaultFont(): PlatformTypeface

/**
 * Color constants for common colors.
 */
object Colors {
    const val WHITE: Int = 0xFFFFFFFF.toInt()
    const val BLACK: Int = 0xFF000000.toInt()
    const val GRAY: Int = 0xFF808080.toInt()
    const val CYAN: Int = 0xFF00FFFF.toInt()
    const val MAGENTA: Int = 0xFFFF00FF.toInt()
    const val RED: Int = 0xFFFF0000.toInt()
    const val GREEN: Int = 0xFF00FF00.toInt()
    const val BLUE: Int = 0xFF0000FF.toInt()
    const val YELLOW: Int = 0xFFFFFF00.toInt()
}
