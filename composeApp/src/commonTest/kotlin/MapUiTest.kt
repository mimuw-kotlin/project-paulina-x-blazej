
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import dev.jordond.compass.geolocation.Geolocator
import io.github.dellisd.spatialk.geojson.Position
import pl.edu.uw.juwenalia.data.model.MapPoint
import pl.edu.uw.juwenalia.data.model.MapPointCategory
import pl.edu.uw.juwenalia.data.repository.MapPointRepository
import pl.edu.uw.juwenalia.ui.map.MapScreen
import pl.edu.uw.juwenalia.ui.map.MapViewModel
import kotlin.test.Test

class TestMapPointRepository : MapPointRepository {
    private val mapPoints =
        listOf(
            MapPoint(
                id = 0,
                name = "wc",
                position = Position(21.041949808468672, 52.22067484841307),
                category = MapPointCategory.WC,
                additionalInfo = "Toilets"
            )
        )

    override fun getMapPointById(id: Int): MapPoint? = mapPoints.getOrNull(id)

    override fun getMapPointsSearchResults(
        query: String,
        limit: Int
    ): List<MapPoint> = mapPoints.filter { it.name.contains(query) }
}

class MapUiTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun mapSearchTest() {
        runComposeUiTest {
            val testMapPointRepository = TestMapPointRepository()
            val viewModel = MapViewModel(testMapPointRepository, Geolocator())

            setContent {
                MapScreen(viewModel)
            }

            // One-letter search inputs shouldn't bring any results
            onNodeWithTag("map_search_bar").performClick()
            onNodeWithTag("map_search_input").performTextInput("w")
            onNodeWithTag("map_search_result").assertDoesNotExist()

            // Toilets should be found on "wc" input
            onNodeWithTag("map_search_input").performTextInput("c")
            onNodeWithTag("map_search_result").performClick()
            onNodeWithTag("map_point_details").assertExists()
        }
    }
}