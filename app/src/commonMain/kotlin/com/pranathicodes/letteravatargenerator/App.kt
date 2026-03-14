package com.pranathicodes.letteravatargenerator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pranathicodes.letteravatar.Colors
import com.pranathicodes.letteravatar.RandomColors
import com.pranathicodes.letteravatar.rememberAvatarCreator
import com.pranathicodes.letteravatar.asImageBitmap

@Composable
fun App() {
    // The library now provides the platform-specific creator automatically
    val avatarCreator = rememberAvatarCreator()
    
    MaterialTheme {
        val randomColors = remember { RandomColors() }
        randomColors.setLetterColors(listOf(Colors.WHITE, Colors.MAGENTA, Colors.GRAY))
        randomColors.setBackgroundColors(listOf(Colors.CYAN, Colors.RED, Colors.BLACK))
        
        val avatarBitmap = remember(avatarCreator) {
            avatarCreator
                .setLetter('P')
                .setLetterColor(randomColors.getLetterColor())
                .setBackgroundColor(randomColors.getBackgroundColor())
                .setAvatarSize(300)
                .build()
                .asImageBitmap()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Use the standard Compose Image component
            Image(
                bitmap = avatarBitmap,
                contentDescription = "Avatar"
            )
        }
    }
}
