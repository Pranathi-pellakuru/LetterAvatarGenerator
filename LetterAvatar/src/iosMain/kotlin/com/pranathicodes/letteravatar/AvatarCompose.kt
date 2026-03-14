package com.pranathicodes.letteravatar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import platform.CoreGraphics.CGDataProviderCopyData
import platform.CoreGraphics.CGImageGetDataProvider
import platform.Foundation.NSData
import platform.posix.memcpy
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned

@Composable
actual fun rememberAvatarCreator(): AvatarCreator {
    return remember { AvatarCreator() }
}

actual fun PlatformBitmap.asImageBitmap(): ImageBitmap {
    // Convert CGImage to Skia Image
    val dataProvider = CGImageGetDataProvider(this)
    val data = CGDataProviderCopyData(dataProvider)
    val nsData = data as? NSData ?: throw IllegalStateException("Failed to get image data")
    val bytes = ByteArray(nsData.length.toInt())
    bytes.usePinned { pinned ->
        memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
    }
    return Image.makeFromEncoded(bytes).toComposeImageBitmap()
}
