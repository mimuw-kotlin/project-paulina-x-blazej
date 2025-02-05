package pl.edu.uw.juwenalia.ui

import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import pl.edu.uw.juwenalia.data.file.downloadFeed
import pl.edu.uw.juwenalia.ui.theme.AppTheme

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    KoinContext {
        LaunchedEffect(Unit) {
            downloadFeed()
        }

        AppTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            NavGraph()
        }
    }
}