package pl.edu.uw.juwenalia.ui.map.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import pl.edu.uw.juwenalia.data.model.MapPoint
import pl.edu.uw.juwenalia.data.model.MapPointCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopBar(
    searchExpanded: Boolean,
    onSearchExpandedChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchQueryClear: () -> Unit,
    onSearchLeadingButtonClick: () -> Unit,
    searchResults: List<MapPoint>,
    onSearchResultClick: (MapPoint) -> Unit,
    selectedFilters: Set<MapPointCategory>,
    onFilterClick: (MapPointCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        val animatedPadding by animateDpAsState(
            if (searchExpanded) 0.dp else 16.dp
        )

        FullscreenSearchBar(
            query = searchQuery,
            expanded = searchExpanded,
            onQueryChange = onSearchQueryChange,
            onSearch = { /* do nothing */ },
            onExpandedChange = onSearchExpandedChange,
            onLeadingButtonClick = onSearchLeadingButtonClick,
            onQueryClear = onSearchQueryClear,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = animatedPadding)
                    .testTag("map_search_bar"),
            colors =
                SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright
                )
        ) {
            LazyColumn(
                Modifier.fillMaxSize()
            ) {
                searchResults.forEach { mapPoint ->
                    item {
                        ListItem(
                            modifier =
                                Modifier
                                    .clickable { onSearchResultClick(mapPoint) }
                                    .testTag("map_search_result"),
                            colors =
                                ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceBright
                                ),
                            headlineContent = { Text(mapPoint.name) },
                            supportingContent =
                                mapPoint.additionalInfo?.let {
                                    {
                                        Text(it)
                                    }
                                },
                            leadingContent = {
                                Icon(
                                    mapPoint.category.icon,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }

        FilterChipRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            selectedFilters = selectedFilters,
            onFilterClick = onFilterClick
        )
    }
}