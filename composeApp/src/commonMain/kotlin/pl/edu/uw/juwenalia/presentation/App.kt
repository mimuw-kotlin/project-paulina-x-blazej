package pl.edu.uw.juwenalia.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
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

enum class AppDestinations(
    val label: StringResource,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {
    HOME(Res.string.home_nav, Icons.Filled.Home, Icons.Outlined.Home),
    ARTISTS(Res.string.artists_nav, Icons.Filled.PeopleAlt, Icons.Outlined.PeopleAlt),
    MAP(Res.string.map_nav, Icons.Filled.Map, Icons.Outlined.Map),
    TICKETS(Res.string.tickets_nav, Icons.Filled.LocalActivity, Icons.Outlined.LocalActivity),
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
        var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                if (currentDestination == it) it.filledIcon else it.outlinedIcon,
                                contentDescription = stringResource(it.label)
                            )
                        },
                        label = { Text(stringResource(it.label)) },
                        selected = currentDestination == it,
                        onClick = { currentDestination = it }
                    )
                }
            }
        ) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = {
                    fadeIn(tween(200, easing = FastOutSlowInEasing)) togetherWith
                        fadeOut(tween(200, easing = FastOutSlowInEasing))
                }
            ) { destination ->
                when (destination) {
                    AppDestinations.HOME -> HomeScreen()
                    AppDestinations.ARTISTS -> ArtistsScreen()
                    AppDestinations.MAP -> MapScreen()
                    AppDestinations.TICKETS -> TicketsScreen()
                }
            }
        }
    }
}