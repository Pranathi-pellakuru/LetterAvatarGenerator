# Letter Avatar Generator

A **Kotlin Multiplatform** library that helps in creating letter avatars as bitmap images to use as profile placeholders. Supports **Android**, **JVM (Desktop)**, and **iOS** platforms.

Includes customization options like setting colors for background and letter. You can also set custom color pairs to choose randomly.

<img height="300" src="pictures/Screenshot_20240901_225018.png" width="120"/>
<img height="300" src="pictures/Screenshot_20240901_230104.png" width="120"/>

## Supported Platforms

- **Android** - Uses `Bitmap`, `Canvas`, `Paint`
- **JVM/Desktop** - Uses Java2D `BufferedImage`
- **iOS** (iosX64, iosArm64, iosSimulatorArm64) - Uses CoreGraphics `CGImage`

## Installation

### Gradle (Kotlin DSL)

Add the dependency to your module's `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.Pranathi-pellakuru:LetterAvatarGenerator:VERSION")
}
```

### Multiplatform Projects

For Kotlin Multiplatform projects, the library will automatically provide the correct implementation for each target platform.

## Platform-Specific Usage

### Android

```kotlin
// Requires Context
val image = AvatarCreator(context).setLetter('U')
    .setTextSize(25)
    .setAvatarSize(180)
    .setLetterColor(Colors.GRAY)
    .setBackgroundColor(Colors.BLACK)
    .build()

// Returns: android.graphics.Bitmap
```

#### Displaying in Jetpack Compose
```kotlin
Image(
    bitmap = AvatarCreator(context).setLetter('U')
        .setLetterColor(Colors.GRAY)
        .setBackgroundColor(Colors.BLACK)
        .build()
        .asImageBitmap(),
    contentDescription = "",
    modifier = Modifier.size(200.dp),
    contentScale = ContentScale.FillWidth
)
```

#### Displaying in ImageView
```kotlin
imageView.setImageDrawable(
    AvatarCreator(context).setLetter('U')
        .setLetterColor(Colors.GRAY)
        .setBackgroundColor(Colors.BLACK)
        .build()
)
```

### JVM/Desktop

```kotlin
// No Context required
val image = AvatarCreator().setLetter('U')
    .setTextSize(25)
    .setAvatarSize(180)
    .setLetterColor(Colors.GRAY)
    .setBackgroundColor(Colors.BLACK)
    .build()

// Returns: java.awt.image.BufferedImage
```

**Note:** On JVM, you can optionally set display density for scaling:
```kotlin
val image = AvatarCreator()
    .setDensity(2.0f)  // Set display density
    .setLetter('U')
    .build()
```

### iOS

```kotlin
// No Context required, but you may want to set density for retina displays
val image = AvatarCreator()
    .setDensity(UIScreen.mainScreen.scale)
    .setLetter('U')
    .setTextSize(25)
    .setAvatarSize(180)
    .setLetterColor(Colors.GRAY)
    .setBackgroundColor(Colors.BLACK)
    .build()

// Returns: platform.CoreGraphics.CGImageRef
```

**Note:** On iOS, use the `setDensity()` function to support retina displays properly.

## Customization

### Colors

**Using predefined color constants:**
```kotlin
Colors.WHITE
Colors.BLACK
Colors.GRAY
Colors.CYAN
Colors.MAGENTA
Colors.RED
Colors.GREEN
Colors.BLUE
Colors.YELLOW
```

**Using custom colors (ARGB format):**
```kotlin
val customColor = 0xFFFF5722.toInt()  // Orange
```

### Custom Color Pairs

```kotlin
val randomColors = RandomColors()
randomColors.setColorPairs(
    listOf(
        Pair(Colors.WHITE, Colors.CYAN),
        Pair(Colors.MAGENTA, Colors.RED),
        Pair(Colors.GRAY, Colors.BLACK)
    )
)
val colorPair = randomColors.getColorPair()
val image = AvatarCreator(/* context if Android */).setLetter('P')
    .setLetterColor(colorPair.first)
    .setBackgroundColor(colorPair.second)
    .build()
```

### Separate Colors for Letter and Background

```kotlin
val randomColors = RandomColors()
randomColors.setLetterColors(listOf(Colors.WHITE, Colors.MAGENTA, Colors.GRAY))
randomColors.setBackgroundColors(listOf(Colors.CYAN, Colors.RED, Colors.BLACK))

val image = AvatarCreator(/* context if Android */).setLetter('P')
    .setLetterColor(randomColors.getLetterColor())
    .setBackgroundColor(randomColors.getBackgroundColor())
    .build()
```

### Custom Fonts

**Android:**
```kotlin
val image = AvatarCreator(context)
    .setLetter('P')
    .setLetterColor(randomColors.getLetterColor())
    .setFont(resources.getFont(R.font.custom_font))
    .setBackgroundColor(randomColors.getBackgroundColor())
    .build()
```

**JVM/Desktop:**
```kotlin
import java.awt.Font

val customFont = Font("Arial", Font.BOLD, 12)
val image = AvatarCreator()
    .setLetter('P')
    .setFont(customFont)
    .build()
```

**iOS:**
```kotlin
import platform.UIKit.UIFont

val customFont = UIFont.fontWithName("Helvetica-Bold", 17.0)
val image = AvatarCreator()
    .setLetter('P')
    .setFont(customFont)
    .build()
```

## API Reference

### AvatarCreator

| Method | Description |
|--------|-------------|
| `setLetter(letter: Char)` | Sets the letter to display |
| `setTextSize(size: Int)` | Sets the text size in pixels (default: 25) |
| `setAvatarSize(size: Int)` | Sets the output image size in pixels (default: 180) |
| `setLetterColor(color: Int)` | Sets the letter color (ARGB format) |
| `setBackgroundColor(color: Int)` | Sets the background color (ARGB format) |
| `setFont(font: PlatformTypeface)` | Sets a custom font |
| `build()` | Generates and returns the avatar image |

**Platform-specific methods:**
- **Android:** Requires `Context` in constructor
- **JVM/iOS:** `setDensity(density: Float/Double)` for display scaling

### RandomColors

| Method | Description |
|--------|-------------|
| `setColorPairs(colors: List<Pair<Int, Int>>)` | Sets color pairs (letter to background) |
| `setLetterColors(colors: List<Int>)` | Sets available letter colors |
| `setBackgroundColors(colors: List<Int>)` | Sets available background colors |
| `getColorPair()` | Returns a random color pair |
| `getLetterColor()` | Returns a random letter color |
| `getBackgroundColor()` | Returns a random background color |

## Color Format

All colors use **ARGB 32-bit format** (Int):
- Bits 24-31: Alpha (0xFF = fully opaque)
- Bits 16-23: Red
- Bits 8-15: Green
- Bits 0-7: Blue

Example: `0xFFFF5722` = Opaque Orange

## Migration from Android-only version

If you were using the previous Android-only version, update your imports:

```kotlin
// Old
import android.graphics.Color
// New
import com.pranathicodes.letteravatar.Colors
```

The `AvatarCreator` now returns `PlatformBitmap` which is:
- `Bitmap` on Android
- `BufferedImage` on JVM
- `CGImage` on iOS

## Reference

https://github.com/AmosKorir/AvatarImageGenerator
