package pl.edu.uw.juwenalia.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.SportsBar
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.ui.graphics.vector.ImageVector
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.beer
import juweappka.composeapp.generated.resources.exhibitors
import juweappka.composeapp.generated.resources.food
import juweappka.composeapp.generated.resources.water
import juweappka.composeapp.generated.resources.wc
import org.jetbrains.compose.resources.StringResource

enum class MapPointCategory(
    val label: StringResource,
    val icon: ImageVector
) {
    FOOD(Res.string.food, Icons.Outlined.Fastfood),
    WC(Res.string.wc, Icons.Outlined.Wc),
    BEER(Res.string.beer, Icons.Outlined.SportsBar),
    WATER(Res.string.water, Icons.Outlined.WaterDrop),
    EXHIBITORS(Res.string.exhibitors, Icons.Outlined.Storefront)
}