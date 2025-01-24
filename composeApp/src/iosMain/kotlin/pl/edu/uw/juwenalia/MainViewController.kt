package pl.edu.uw.juwenalia

import androidx.compose.ui.window.ComposeUIViewController
import pl.edu.uw.juwenalia.presentation.App

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme = isDarkTheme,
        dynamicColor = false,
    )
}