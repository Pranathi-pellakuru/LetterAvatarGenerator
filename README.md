# Letter Avatar Generator
An android library that helps in creating a letter avatar as bitmap to use as a placeholder for profile.
Includes customisation like setting colors to Background and letter , you can also set your custom color pairs to choose randomly

<img height="300" src="pictures/Screenshot_20240305_233943.png" width="120"/>

## Installation
To include LetterAvatarGenerator dependency you need to add JitPack as repository in `settings.gradle.kts`

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
       google()
       mavenCentral()
       maven {
             setUrl("https://jitpack.io")
       }
    }
}
```

Add the following dependency to your gradle file.
```gradle
implementation 'com.github.Pranathi-pellakuru:LetterAvatarGenerator:VERSION'
```

## How to Use

Avatar creator class

```kotlin
val image = AvatarCreator(this).setLetter('U')
    .setTextSize(25)
    .setAvatarSize(180)
    .setLetterColor(Color.GRAY)
    .setBackgroundColor(Color.BLACK)
    .build()
```

To customize colors 

**If you have color pairs**

```kotlin
val randomColors = RandomColors()
randomColors.setColorPairs(listOf(Pair(Color.WHITE,Color.CYAN), Pair(Color.MAGENTA,Color.RED), Pair(Color.GRAY,Color.BLACK)))
val colorPair = randomColors.getColorPair()
val image = AvatarCreator(this).setLetter('P')
     .setLetterColor(colorPair.first)
     .setBackgroundColor(colorPair.second)
     .build()
```

**different set of colors for background and letter**
```kotlin
val randomColors = RandomColors()
randomColors.setLetterColors(listOf(Color.WHITE,Color.MAGENTA,Color.GRAY))
randomColors.setBackgroundColors(listOf(Color.CYAN,Color.RED,Color.BLACK))
val image1 = AvatarCreator(this).setLetter('P')
    .setLetterColor(randomColors.getLetterColor())
    .setBackgroundColor(randomColors.getBackgroundColor())
    .build()

```

## Displaying the generated bitmap

**Jetpack compose**
```kotlin
Image(
    bitmap = AvatarCreator(this).setLetter('U')
        .setLetterColor(Color.GRAY)
        .setBackgroundColor(Color.BLACK)
        .build()
        .asImageBitmap(),
    contentDescription = "",
    modifier = Modifier.size(200.dp),
    contentScale = ContentScale.FillWidth
)
```
**ImageView**
```Kotlin
imageView.setImageDrawable(
    AvatarCreator(this).setLetter('U')
        .setLetterColor(Color.GRAY)
        .setBackgroundColor(Color.BLACK)
        .build()
        )
```

## Reference 
https://github.com/AmosKorir/AvatarImageGenerator
