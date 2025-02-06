package pl.edu.uw.juwenalia.ui.map.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.filter
import juweappka.composeapp.generated.resources.selected
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.data.model.MapPointCategory

@Composable
fun FilterChipRow(
    selectedFilters: Set<MapPointCategory>,
    onFilterClick: (MapPointCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        for (category in MapPointCategory.entries) {
            val isSelected = selectedFilters.contains(category)
            ElevatedFilterChip(
                selected = isSelected,
                onClick = { onFilterClick(category) },
                label = { Text(stringResource(category.label)) },
                leadingIcon = {
                    if (isSelected) {
                        Icon(Icons.Outlined.Check, stringResource(Res.string.selected))
                    } else {
                        Icon(category.icon, stringResource(Res.string.filter))
                    }
                },
                modifier = Modifier.padding(end = 8.dp).height(36.dp),
                colors =
                    FilterChipDefaults.elevatedFilterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceBright,
                        iconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                shape = CircleShape
            )
        }
    }
}