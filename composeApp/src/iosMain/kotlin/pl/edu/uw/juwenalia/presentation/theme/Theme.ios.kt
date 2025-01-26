package pl.edu.uw.juwenalia.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content:
        @Composable()
        () -> Unit
) {
    MaterialTheme(
        // TODO: maybe handle the medium and high contrast levels?
        colorScheme = if (darkTheme) darkScheme else lightScheme,
        typography = AppTypography,
        content = content
    )
}