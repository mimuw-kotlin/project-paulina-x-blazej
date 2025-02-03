package pl.edu.uw.juwenalia.presentation.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.em
import dev.sargunv.maplibrecompose.compose.CameraState
import dev.sargunv.maplibrecompose.compose.ClickResult
import dev.sargunv.maplibrecompose.compose.MaplibreMap
import dev.sargunv.maplibrecompose.compose.StyleState
import dev.sargunv.maplibrecompose.compose.layer.FillLayer
import dev.sargunv.maplibrecompose.compose.layer.SymbolLayer
import dev.sargunv.maplibrecompose.compose.source.rememberGeoJsonSource
import dev.sargunv.maplibrecompose.core.OrnamentSettings
import dev.sargunv.maplibrecompose.expressions.ast.Expression
import dev.sargunv.maplibrecompose.expressions.dsl.Feature.get
import dev.sargunv.maplibrecompose.expressions.dsl.asString
import dev.sargunv.maplibrecompose.expressions.dsl.const
import dev.sargunv.maplibrecompose.expressions.dsl.format
import dev.sargunv.maplibrecompose.expressions.dsl.image
import dev.sargunv.maplibrecompose.expressions.dsl.nil
import dev.sargunv.maplibrecompose.expressions.dsl.offset
import dev.sargunv.maplibrecompose.expressions.dsl.span
import dev.sargunv.maplibrecompose.expressions.value.BooleanValue
import io.github.dellisd.spatialk.geojson.Point
import io.github.dellisd.spatialk.geojson.Position
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.marker
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MapView(
    cameraState: CameraState,
    styleState: StyleState,
    onMapPointClick: (Int) -> Unit,
    mapPointsFilter: Expression<BooleanValue> = nil(),
    gpsPosition: Position? = null
) {
    val variant = if (isSystemInDarkTheme()) "dark" else "light"
    val marker = painterResource(Res.drawable.marker)

    MaplibreMap(
        cameraState = cameraState,
        styleState = styleState,
        ornamentSettings = OrnamentSettings.AllDisabled,
        // TODO: hide API key
        styleUri =
            "https://api.protomaps.com/styles/v4/$variant/pl.json?key=33e26ffe14599bf8"
    ) {
        val festivalAreas =
            rememberGeoJsonSource(
                id = "festival_areas",
                uri = Res.getUri("files/festival_areas.geojson")
            )

        val festivalMapPoints =
            rememberGeoJsonSource(
                id = "festival_map_points",
                uri = Res.getUri("files/festival_map_points.geojson")
            )

        FillLayer(
            id = "festival_areas",
            source = festivalAreas,
            opacity = const(0.4f),
            color = const(MaterialTheme.colorScheme.primaryContainer),
            outlineColor = const(MaterialTheme.colorScheme.primary)
        )

        SymbolLayer(
            id = "festival_map_points",
            source = festivalMapPoints,
            filter = mapPointsFilter,
            onClick = { features ->
                features
                    .firstOrNull()
                    ?.id
                    ?.toIntOrNull()
                    ?.let(onMapPointClick)
                ClickResult.Consume
            },
            iconImage = image(marker),
            iconAllowOverlap = const(true),
            textField = format(span(get("label").asString())),
            textFont = const(listOf("Noto Sans Regular")),
            textColor = const(MaterialTheme.colorScheme.onSurface),
            textSize = const(0.6.em),
            textOffset = offset(0.em, 1.em),
            textOptional = const(true)
        )

        if (gpsPosition != null) {
            LocationPuck(
                locationSource =
                    rememberGeoJsonSource(
                        "target",
                        Point(gpsPosition)
                    )
            )
        }
    }
}