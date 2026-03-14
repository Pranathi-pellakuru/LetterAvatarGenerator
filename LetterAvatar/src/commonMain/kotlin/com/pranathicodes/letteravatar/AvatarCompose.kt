package com.pranathicodes.letteravatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Common entry point for using AvatarCreator in Compose Multiplatform.
 */
@Composable
expect fun rememberAvatarCreator(): AvatarCreatorInterface

/**
 * Extension to convert platform bitmap to Compose-friendly ImageBitmap.
 */
expect fun PlatformBitmap.asImageBitmap(): ImageBitmap
