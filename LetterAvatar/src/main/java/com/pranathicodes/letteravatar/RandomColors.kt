package com.pranathicodes.letteravatar

class RandomColors {
    private var colorPairs: List<Pair<Int,Int>> = listOf()
    private var letterColors : List<Int> = listOf()
    private var backgroundColors : List<Int> = listOf()

    fun getColorPair(): Pair<Int,Int> {
        return colorPairs.random()
    }

    fun setColorPairs(colors : List<Pair<Int,Int>>){
        colorPairs = colors
    }

    fun setLetterColors(colors : List<Int>){
        letterColors = colors
    }

    fun setBackgroundColors(colors : List<Int>){
        backgroundColors = colors
    }

    fun getLetterColor():Int{
        return letterColors.random()
    }

    fun getBackgroundColor():Int {
        return backgroundColors.random()
    }

}

