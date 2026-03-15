package com.pranathicodes.letteravatar

import androidx.compose.ui.graphics.Color

/**
 * Cross-platform class for managing random colors.
 * Works with Compose Color values.
 */
class RandomColors {
    private var colorPairs: List<Pair<Color, Color>> = listOf()
    private var letterColors: List<Color> = listOf()
    private var backgroundColors: List<Color> = listOf()

    fun getColorPair(): Pair<Color, Color> {
        return colorPairs.random()
    }

    fun setColorPairs(colors: List<Pair<Color, Color>>) {
        colorPairs = colors
    }

    fun setLetterColors(colors: List<Color>) {
        letterColors = colors
    }

    fun setBackgroundColors(colors: List<Color>) {
        backgroundColors = colors
    }

    fun getLetterColor(): Color {
        return letterColors.random()
    }

    fun getBackgroundColor(): Color {
        return backgroundColors.random()
    }
}
