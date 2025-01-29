package pl.edu.uw.juwenalia.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalActivity
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.artists_nav
import juweappka.composeapp.generated.resources.home_nav
import juweappka.composeapp.generated.resources.map_nav
import juweappka.composeapp.generated.resources.tickets_nav
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.presentation.artists.ArtistsScreen
import pl.edu.uw.juwenalia.presentation.home.HomeScreen
import pl.edu.uw.juwenalia.presentation.map.MapScreen
import pl.edu.uw.juwenalia.presentation.theme.AppTheme
import pl.edu.uw.juwenalia.presentation.tickets.TicketsScreen

enum class TopLevelDestination(
    val label: StringResource,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {
    HOME(Res.string.home_nav, Icons.Filled.Home, Icons.Outlined.Home),
    ARTISTS(Res.string.artists_nav, Icons.Filled.PeopleAlt, Icons.Outlined.PeopleAlt),
    MAP(Res.string.map_nav, Icons.Filled.Map, Icons.Outlined.Map),
    TICKETS(Res.string.tickets_nav, Icons.Filled.LocalActivity, Icons.Outlined.LocalActivity)
}

@Composable
internal fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                TopLevelDestination.entries.forEach { destination ->
                    item(
                        icon = {
                            Icon(
                                if (currentDestination == destination.name) {
                                    destination.filledIcon
                                } else {
                                    destination.outlinedIcon
                                },
                                contentDescription = stringResource(destination.label)
                            )
                        },
                        label = { Text(stringResource(destination.label)) },
                        selected = currentDestination == destination.name,
                        onClick = {
                            navController.navigate(destination.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = TopLevelDestination.HOME.name,
                enterTransition = { fadeIn(tween(200, easing = FastOutSlowInEasing)) },
                exitTransition = { fadeOut(tween(200, easing = FastOutSlowInEasing)) }
            ) {
                composable(route = TopLevelDestination.HOME.name) {
                    HomeScreen()
                }

                composable(route = TopLevelDestination.ARTISTS.name) {
                    ArtistsScreen()
                }

                composable(route = TopLevelDestination.MAP.name) {
                    MapScreen()
                }

                composable(route = TopLevelDestination.TICKETS.name) {
                    TicketsScreen()
                }
            }
        }
    }
}