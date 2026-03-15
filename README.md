# Letter Avatar Generator

A **Kotlin Multiplatform** library that helps in creating letter avatars as `ImageBitmap` objects for profile placeholders. Built with **Compose Multiplatform**, it provides a consistent, high-performance rendering engine that works identically across **Android**, **iOS**, and other Compose targets.

The library handles text measuring, centering, and scaling automatically using a shared implementation in `commonMain`, ensuring your avatars look the same on every device.

<img height="300" src="pictures/Screenshot_20240901_225018.png" width="120"/>
<img height="300" src="pictures/Screenshot_20240901_230104.png" width="120"/>

## Features

- **Pure Compose Rendering**: Uses `CanvasDrawScope` and `TextMeasurer` for platform-agnostic drawing.
- **ImageBitmap Output**: Returns a standard Compose `ImageBitmap` directly.
- **Resource Integration**: Easily use custom fonts from `composeResources` via `FontFamily`.
- **Consistent Scaling**: Text size is defined as a percentage of the avatar size.

## Installation

The library is available on **Maven Central**.

### Gradle (Kotlin DSL)

Add the dependency to your module's `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("io.github.pranathi-pellakuru:Letter-Avatar-Generator:1.2.0")
}
```

## Usage in Compose Multiplatform

The library provides a simple API to build avatars directly in your Composable functions.

```kotlin
import androidx.compose.ui.graphics.Color
import com.pranathicodes.letteravatar.rememberAvatarCreator

@Composable
fun MyAvatar() {
    // 1. Get the shared creator (automatically uses local Density and TextMeasurer)
    val avatarCreator = rememberAvatarCreator()
    
    // 2. Build the avatar (returns ImageBitmap)
    val avatarBitmap = remember(avatarCreator) {
        avatarCreator
            .setLetter('P')
            .setAvatarSize(180) // Size in dp-equivalent pixels
            .setTextSize(45)    // Text height as 45% of avatar size
            .setLetterColor(Color.White)
            .setBackgroundColor(Color(0xFFFF5722))
            .build()
    }

    // 3. Display using standard Compose Image
    Image(
        bitmap = avatarBitmap,
        contentDescription = "User Avatar"
    )
}
```

## Customization

### Fonts

You can use custom fonts from your resources seamlessly:

```kotlin
val myFontFamily = FontFamily(Font(Res.font.my_custom_font))

avatarCreator.setFontFamily(myFontFamily)
```

### Random Colors

The library includes a `RandomColors` helper to manage color palettes using Compose `Color`:

```kotlin
val randomColors = RandomColors().apply {
    setColorPairs(listOf(Color.White to Color.Blue, Color.Yellow to Color.Black))
}

// Pick a random pair
val pair = randomColors.getColorPair()

avatarCreator
    .setLetterColor(pair.first)
    .setBackgroundColor(pair.second)
```

## API Reference

### AvatarCreator

| Method | Description |
|--------|-------------|
| `setLetter(letter: Char)` | Sets the letter to display |
| `setTextSize(percentage: Int)` | Sets text height as a % of avatar size (0-100, default: 40) |
| `setAvatarSize(size: Int)` | Sets output image size in pixels (default: 180) |
| `setLetterColor(color: Color)` | Sets the letter color |
| `setBackgroundColor(color: Color)` | Sets the background color |
| `setFontFamily(family: FontFamily)`| Sets a custom `FontFamily` |
| `build()` | Returns `ImageBitmap` |

### Compose Extensions

- `rememberAvatarCreator()`: Composable function that provides a pre-configured creator using the current `LocalDensity` and `TextMeasurer`.

## License

This project is licensed under the MIT License.
