package com.pranathicodes.letteravatar

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSString
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSMutableParagraphStyle
import platform.UIKit.NSParagraphStyleAttributeName
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIRectFill
import platform.UIKit.drawInRect

/**
 * iOS-specific implementation of PlatformBitmap wrapping CGImage.
 */
@OptIn(ExperimentalForeignApi::class)
actual class PlatformBitmap(val cgImage: CGImageRef)

private fun Int.toRGBComponents(): Triple<Double, Double, Double> {
    val red = ((this shr 16) and 0xFF) / 255.0
    val green = ((this shr 8) and 0xFF) / 255.0
    val blue = (this and 0xFF) / 255.0
    return Triple(red, green, blue)
}

private fun Int.toAlpha(): Double = ((this shr 24) and 0xFF) / 255.0

actual class AvatarCreator : AvatarCreatorBase() {

    protected var density: Double = 1.0

    fun setDensity(density: Double) = apply {
        this.density = density
    }

    override fun imageDensity(): Double = density

    @OptIn(ExperimentalForeignApi::class)
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
        val size = imageSize.toDouble()

        // Use UIKit image context which handles coordinate system and scaling correctly.
        UIGraphicsBeginImageContextWithOptions(CGSizeMake(size, size), false, density)

        // Draw background
        val bgComponents = backgroundColor.toRGBComponents()
        val bgColor = UIColor.colorWithRed(
            bgComponents.first,
            bgComponents.second,
            bgComponents.third,
            backgroundColor.toAlpha()
        )
        bgColor.setFill()
        UIRectFill(CGRectMake(0.0, 0.0, size, size))

        // Calculate text size as a percentage of the image size
        val scaledTextSize = size * (textSize / 100.0)

        // Resolve UIFont from FontFamily
        val resolvedFont = if (fontFamily != null) {
            val resolver = androidx.compose.ui.text.font.createFontFamilyResolver()
            val resolved = resolver.resolve(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
            val uiFont = resolved.value as? UIFont
            uiFont?.fontWithSize(scaledTextSize) ?: UIFont.systemFontOfSize(scaledTextSize)
        } else {
            UIFont.systemFontOfSize(scaledTextSize)
        }

        val fgComponents = letterColor.toRGBComponents()
        val fgColor = UIColor.colorWithRed(
            fgComponents.first,
            fgComponents.second,
            fgComponents.third,
            letterColor.toAlpha()
        )

        val paragraphStyle = NSMutableParagraphStyle().apply {
            setAlignment(NSTextAlignmentCenter)
        }

        val attributes = mapOf<Any?, Any?>(
            NSFontAttributeName to resolvedFont,
            NSForegroundColorAttributeName to fgColor,
            NSParagraphStyleAttributeName to paragraphStyle
        )

        val estimatedTextHeight = resolvedFont.lineHeight
        val y = (size - estimatedTextHeight) / 2.0

        (label as NSString).drawInRect(
            CGRectMake(0.0, y, size, estimatedTextHeight),
            attributes
        )

        val uiImage = UIGraphicsGetImageFromCurrentImageContext()
            ?: error("Failed to get image from context")
        UIGraphicsEndImageContext()

        return PlatformBitmap(uiImage.CGImage!!)
    }
}
