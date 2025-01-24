package pl.edu.uw.juwenalia.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.edu.uw.juwenalia.presentation.theme.AppTheme

import pl.edu.uw.juwenalia.presentation.home.HomeScreen
import pl.edu.uw.juwenalia.presentation.map.MapScreen
import pl.edu.uw.juwenalia.presentation.artists.ArtistsScreen
import pl.edu.uw.juwenalia.presentation.tickets.TicketsScreen

sealed class NavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
) {
    object Home : NavItem("home", Icons.Default.Home, "Główna")
    object Artists : NavItem("artists", Icons.Default.Person, "Artyści")
    object Map : NavItem("map", Icons.Default.Place, "Mapa")
    object Tickets : NavItem("tickets", Icons.Default.ShoppingCart, "Bilety")
}

@Composable
@Preview
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val navItems = listOf(NavItem.Home, NavItem.Artists, NavItem.Map, NavItem.Tickets)
        var selectedRoute by remember { mutableStateOf(NavItem.Home.route) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    navItems.forEach { item ->
                        NavigationBarItem(
                            selected = selectedRoute == item.route,
                            onClick = { selectedRoute = item.route },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) {
            when (selectedRoute) {
                NavItem.Home.route -> HomeScreen()
                NavItem.Map.route -> MapScreen()
                NavItem.Artists.route -> ArtistsScreen()
                NavItem.Tickets.route -> TicketsScreen()
            }
        }
    }

}