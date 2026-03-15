package com.pranathicodes.letteravatar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberAvatarCreator(): AvatarCreatorInterface {
    val context = LocalContext.current
    return remember(context) { AvatarCreator(context) }
}

actual fun PlatformBitmap.asImageBitmap(): ImageBitmap = this.asImageBitmap()
