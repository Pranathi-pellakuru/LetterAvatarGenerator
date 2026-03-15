package com.pranathicodes.letteravatar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.readBytes
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

@Composable
actual fun rememberAvatarCreator(): AvatarCreatorInterface {
    return remember { AvatarCreator() }
}

@OptIn(ExperimentalForeignApi::class)
actual fun PlatformBitmap.asImageBitmap(): ImageBitmap {
    val uiImage = UIImage.imageWithCGImage(cgImage)
    val pngData = UIImagePNGRepresentation(uiImage)
        ?: throw IllegalStateException("Failed to encode CGImage as PNG")

    val length = pngData.length.toInt()
    val bytePtr = pngData.bytes?.reinterpret<ByteVar>()
        ?: throw IllegalStateException("Failed to access PNG bytes")

    val encodedBytes = bytePtr.readBytes(length)
    return Image.makeFromEncoded(encodedBytes).toComposeImageBitmap()
}
