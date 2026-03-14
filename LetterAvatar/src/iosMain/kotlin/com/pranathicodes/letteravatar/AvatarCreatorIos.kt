package com.pranathicodes.letteravatar

import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGContextFillRect
import platform.CoreGraphics.CGContextRelease
import platform.CoreGraphics.CGContextSetRGBFillColor
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.kCGImageAlphaPremultipliedLast
import platform.CoreGraphics.CGContextSetShouldAntialias
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIFont
import platform.Foundation.NSAttributedString
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSParagraphStyleAttributeName
import platform.UIKit.NSMutableParagraphStyle
import platform.UIKit.NSStringDrawingUsesFontLeading
import platform.UIKit.NSStringDrawingUsesLineFragmentOrigin
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.boundingRectWithSize
import platform.UIKit.drawInRect
import platform.UIKit.UIColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents

/**
 * iOS-specific implementation of PlatformBitmap using CGImage.
 */
actual typealias PlatformBitmap = CGImageRef

/**
 * iOS-specific implementation of PlatformTypeface using UIFont.
 */
actual typealias PlatformTypeface = UIFont

/**
 * Returns the default font for iOS (system font).
 */
actual fun defaultFont(): PlatformTypeface = UIFont.systemFontOfSize(17.0)

/**
 * Helper extension to extract RGBA components from Int color (ARGB format).
 */
private fun Int.toRGBComponents(): Triple<Double, Double, Double> {
    val red = ((this shr 16) and 0xFF) / 255.0
    val green = ((this shr 8) and 0xFF) / 255.0
    val blue = (this and 0xFF) / 255.0
    return Triple(red, green, blue)
}

/**
 * Helper to get alpha component from Int color.
 */
private fun Int.toAlpha(): Double {
    return ((this shr 24) and 0xFF) / 255.0
}

/**
 * iOS implementation of AvatarCreator using CoreGraphics.
 */
actual class AvatarCreator : AvatarCreatorInterface {

    private var textSize = 25
    private var size = 180
    private var name = ' '
    private var font: PlatformTypeface = defaultFont()
    private var letterColor: Int = Colors.WHITE
    private var backgroundColor: Int = Colors.GRAY
    private var density: Double = 1.0

    /**
     * Set the display density for scaling text size.
     * Default is 1.0, on iOS you may want to use UIScreen.mainScreen.scale
     */
    fun setDensity(density: Double) = apply {
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

    @OptIn(ExperimentalForeignApi::class)
    private fun avatarImageGenerate(
        size: Int,
        name: String,
        textSize: Int,
        font: UIFont,
        letterColor: Int,
        backgroundColor: Int,
        density: Double
    ): CGImageRef {
        val label = name.uppercase().ifEmpty { "-" }.take(1)
        val scaledSize = (size * density).toInt()

        // Create bitmap context
        val colorSpace = CGColorSpaceCreateDeviceRGB()
        val context = CGBitmapContextCreate(
            null,
            scaledSize.toULong(),
            scaledSize.toULong(),
            8u, // bits per component
            0u, // bytes per row (0 = automatic)
            colorSpace,
            kCGImageAlphaPremultipliedLast
        ) ?: throw IllegalStateException("Failed to create bitmap context")

        // Enable anti-aliasing
        CGContextSetShouldAntialias(context, true)

        // Fill background
        val bgComponents = backgroundColor.toRGBComponents()
        val bgAlpha = backgroundColor.toAlpha()
        CGContextSetRGBFillColor(
            context,
            bgComponents.first,
            bgComponents.second,
            bgComponents.third,
            bgAlpha
        )
        CGContextFillRect(
            context,
            CGRectMake(0.0, 0.0, scaledSize.toDouble(), scaledSize.toDouble())
        )

        // Prepare text attributes
        val scaledFontSize = textSize * density
        val textFont = font.fontWithSize(scaledFontSize)
        val fgComponents = letterColor.toRGBComponents()
        val fgColor = UIColor.colorWithRed(
            fgComponents.first,
            fgComponents.second,
            fgComponents.third,
            letterColor.toAlpha()
        )

        // Create paragraph style for center alignment
        val paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = NSTextAlignmentCenter

        // Create attributed string
        val attributes = mapOf<Any?, Any?>(
            NSFontAttributeName to textFont,
            NSForegroundColorAttributeName to fgColor,
            NSParagraphStyleAttributeName to paragraphStyle
        )

        val attributedString = NSAttributedString.create(label, attributes)

        // Calculate text bounds
        val textSizeCG = CGSizeMake(scaledSize.toDouble(), scaledSize.toDouble())
        val options = NSStringDrawingUsesLineFragmentOrigin or NSStringDrawingUsesFontLeading
        val textRect = attributedString.boundingRectWithSize(textSizeCG, options, null)

        // Calculate centered position
        val textWidth = textRect.useContents { size.width }
        val textHeight = textRect.useContents { size.height }
        val x = (scaledSize - textWidth) / 2.0
        val y = (scaledSize - textHeight) / 2.0 - textRect.useContents { origin.y }

        // Draw text
        val drawRect = CGRectMake(x, y, textWidth, textHeight)
        attributedString.drawInRect(drawRect)

        // Create CGImage from context
        val cgImage = CGBitmapContextCreateImage(context)
            ?: throw IllegalStateException("Failed to create CGImage")

        // Clean up
        CGContextRelease(context)

        return cgImage
    }
}
