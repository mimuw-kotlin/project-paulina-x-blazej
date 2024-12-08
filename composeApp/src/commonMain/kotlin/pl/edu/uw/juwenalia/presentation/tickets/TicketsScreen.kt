package pl.edu.uw.juwenalia.presentation.tickets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.compose_multiplatform
import pl.edu.uw.juwenalia.Greeting

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import pl.edu.uw.juwenalia.presentation.theme.AppTheme

@Composable
@Preview
fun TicketsScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Tickets Screen!")
    }
}