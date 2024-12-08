package pl.edu.uw.juwenalia

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import pl.edu.uw.juwenalia.presentation.App
import pl.edu.uw.juwenalia.presentation.artists.ArtistsScreen
import pl.edu.uw.juwenalia.presentation.home.HomeScreen
import pl.edu.uw.juwenalia.presentation.map.MapScreen
import pl.edu.uw.juwenalia.presentation.tickets.TicketsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun AppAndroidPreview() {
    App()
}