package pl.edu.uw.juwenalia

import androidx.compose.ui.window.ComposeUIViewController
import pl.edu.uw.juwenalia.di.initKoin
import pl.edu.uw.juwenalia.ui.App
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        val isDarkTheme =
            UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
        App(
            darkTheme = isDarkTheme,
            dynamicColor = false
        )
    }