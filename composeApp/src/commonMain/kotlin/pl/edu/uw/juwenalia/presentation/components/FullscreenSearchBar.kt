package pl.edu.uw.juwenalia.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.clear
import juweappka.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullscreenSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onFocus: () -> Unit,
    onQueryClear: () -> Unit,
    searchContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(stringResource(Res.string.search)) },
                leadingIcon = {
                    IconButton(onClick = onFocus) {
                        Icon(
                            if (expanded) {
                                Icons.AutoMirrored.Filled.ArrowBack
                            } else {
                                Icons.Filled.Search
                            },
                            contentDescription = stringResource(Res.string.search)
                        )
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = onQueryClear) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = stringResource(Res.string.clear)
                            )
                        }
                    }
                }
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        content = searchContent
    )
}