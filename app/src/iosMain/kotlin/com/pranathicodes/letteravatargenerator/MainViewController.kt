package com.pranathicodes.letteravatargenerator

import androidx.compose.ui.window.ComposeWindow
import com.pranathicodes.letteravatar.AvatarCreator
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeWindow {
    App(AvatarCreator())
}
