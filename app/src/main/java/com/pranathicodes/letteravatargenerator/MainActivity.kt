package com.pranathicodes.letteravatargenerator

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pranathicodes.letteravatar.AvatarCreator
import com.pranathicodes.letteravatar.RandomColors
import com.pranathicodes.letteravatargenerator.ui.theme.LetterAvatarGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val randomColors = RandomColors()
        randomColors.setLetterColors(listOf(Color.WHITE, Color.MAGENTA, Color.GRAY))
        randomColors.setBackgroundColors(listOf(Color.CYAN, Color.RED, Color.BLACK))
        val image = AvatarCreator(this)
            .setLetter('P')
            .setLetterColor(randomColors.getLetterColor())
            .setFont(this.resources.getFont(R.font.micro_extend_flf_bold))
            .setBackgroundColor(randomColors.getBackgroundColor())
            .build()

        setContent {
            LetterAvatarGeneratorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.FillWidth
                    )

                }
            }
        }
    }
}

