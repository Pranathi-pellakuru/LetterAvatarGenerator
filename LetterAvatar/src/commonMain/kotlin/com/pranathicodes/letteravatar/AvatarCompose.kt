package com.pranathicodes.letteravatar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer

/**
 * Common entry point for using AvatarCreator in Compose Multiplatform.
 */
@Composable
fun rememberAvatarCreator(): AvatarCreatorInterface {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    return remember(textMeasurer, density) { AvatarCreator(textMeasurer, density) }
}
