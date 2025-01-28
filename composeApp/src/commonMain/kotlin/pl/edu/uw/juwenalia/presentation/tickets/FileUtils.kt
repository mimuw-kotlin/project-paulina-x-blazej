package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.runtime.Composable

expect fun openFile(filePath: String, context : Any?)

@Composable
expect fun getContext(): Any?