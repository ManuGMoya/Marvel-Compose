package com.manudev.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manudev.domain.model.ComicDomain
import com.manudev.presentation.R
import com.manudev.presentation.screens.Screen
import com.manudev.presentation.theme.DetailImageHeight
import com.manudev.presentation.theme.Padding16
import com.manudev.presentation.theme.Padding32
import com.manudev.presentation.theme.Padding8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(
    viewModel: DetailViewModel = hiltViewModel(),
    id: Int,
    navController: NavHostController
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.Detail)) },
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()

                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.Back)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            DetailContent(
                state = viewModel.state,
                paddingValues = paddingValues,
                onRetry = { viewModel.getCharacterDetail(id) }
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getCharacterDetail(id)
    }
}

@Composable
fun DetailContent(
    state: DetailViewModel.UiState,
    paddingValues: PaddingValues,
    onRetry: () -> Unit
) {
    Screen {
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.error,
                            modifier = Modifier.padding(Padding32)
                        )
                        Button(
                            onClick = { onRetry.invoke() },
                            modifier = Modifier.padding(Padding16)
                        ) {
                            Text(text = stringResource(R.string.Retry))
                        }
                    }
                }
            }

            else -> {
                val character = state.character
                if (character != null) {
                    Column(modifier = Modifier.padding(paddingValues)) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier
                                .height(DetailImageHeight)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(Padding8))
                        Text(text = character.name ?: "")
                        Spacer(modifier = Modifier.height(Padding8))
                        Text(text = character.description ?: "")
                        LazyColumn {
                            items(state.comics) { comic ->
                                ComicItem(comic)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComicItem(comic: ComicDomain) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding8),
        shape = RoundedCornerShape(Padding8),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Padding8
        ),
    ) {
        Column(modifier = Modifier.padding(Padding16)) {
            Text(
                text = comic.title,
                style = typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(Padding8))
            Text(
                text = comic.date,
                style = typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(Padding8))
            AnimatedVisibility(visible = comic.image.isNotEmpty()) {
                AsyncImage(
                    model = comic.image,
                    contentDescription = comic.title,
                    modifier = Modifier
                        .height(DetailImageHeight)
                        .fillMaxWidth()
                )
            }

        }
    }
}
