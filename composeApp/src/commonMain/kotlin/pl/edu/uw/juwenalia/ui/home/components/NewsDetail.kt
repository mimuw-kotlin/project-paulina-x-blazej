package pl.edu.uw.juwenalia.ui.home.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import juweappka.composeapp.generated.resources.Res
import juweappka.composeapp.generated.resources.app_name
import juweappka.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource
import pl.edu.uw.juwenalia.data.model.News

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun NewsDetail(
    news: News,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.app_name)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding)
        ) {
            with(sharedTransitionScope) {
                AsyncImage(
                    news.imageByteArray,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .sharedBounds(
                                sharedTransitionScope.rememberSharedContentState(
                                    key = "news-image-${news.id}"
                                ),
                                animatedVisibilityScope = animatedContentScope
                            ).fillMaxWidth()
                            .aspectRatio(1.77f)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = news.title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier =
                            Modifier
                                .sharedBounds(
                                    sharedTransitionScope.rememberSharedContentState(
                                        key = "news-title-${news.id}"
                                    ),
                                    animatedVisibilityScope = animatedContentScope
                                ).padding(bottom = 8.dp)
                    )
                    Text(
                        text = news.content,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}