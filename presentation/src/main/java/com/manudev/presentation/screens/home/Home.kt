package com.manudev.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manudev.domain.model.CharacterDomain
import com.manudev.presentation.R
import com.manudev.presentation.screens.Screen
import com.manudev.presentation.theme.CharacterImageSize
import com.manudev.presentation.theme.Padding16
import com.manudev.presentation.theme.Padding32
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SEARCH_DELAY = 400L
private const val INITIAL_PAGE = 0
private const val PAGE_SIZE = 20
private const val ANIMATION_DURATION = 1000

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            HomeTopBar(
                viewModel
            )
        },
        content = { paddingValues ->
            HomeContent(
                listState,
                viewModel.state,
                paddingValues,
                onItemClick = onItemClick,
                onRefreshList = viewModel::getCharacters,
                onRetry = {
                    viewModel.getCharacters(INITIAL_PAGE, PAGE_SIZE, true)
                }
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getCharacters(INITIAL_PAGE, PAGE_SIZE, true)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopBar(
    viewModel: HomeViewModel
) {
    var isSearchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val searchJob = rememberCoroutineScope()


    Crossfade(
        targetState = isSearchMode,
        label = "",
        animationSpec = tween(ANIMATION_DURATION)
    ) { searchMode ->
        TopAppBar(
            title = {
                if (searchMode) {
                    TextField(
                        value = searchText,
                        onValueChange = { newValue ->
                            searchText = newValue
                            searchJob.launch {
                                delay(SEARCH_DELAY)
                                if (newValue == searchText) {
                                    if (newValue.isEmpty()) {
                                        viewModel.getCharacters(
                                            INITIAL_PAGE,
                                            PAGE_SIZE,
                                            true
                                        )
                                    } else {
                                        viewModel.getCharacterByName(
                                            INITIAL_PAGE,
                                            PAGE_SIZE,
                                            newValue
                                        )
                                    }
                                }
                            }
                        },
                        label = { Text(stringResource(R.string.Search)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    Text(text = stringResource(R.string.AppName))
                }
            },
            actions = {
                IconButton(onClick = {
                    isSearchMode = !isSearchMode
                }) {
                    if (searchMode) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.Close)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.Search)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun HomeContent(
    listState: LazyListState,
    state: HomeViewModel.UiState,
    paddingValues: PaddingValues,
    onItemClick: (Int) -> Unit,
    onRefreshList: (Int, Int) -> Unit,
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
                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    itemsIndexed(state.characters) { index, character ->
                        if (index > INITIAL_PAGE) Divider()
                        CharacterItem(
                            character = character,
                            onItemClick = { onItemClick(character.id) }
                        )
                        if (index == state.characters.size - 1) {
                            onRefreshList(index + 1, PAGE_SIZE)
                            LaunchedEffect(Unit) {
                                listState.scrollToItem(index)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: CharacterDomain,
    onItemClick: () -> Unit
) {
    ListItem(
        headlineContent = { character.name?.let { Text(text = it) } },
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = character.name,
                modifier = Modifier
                    .size(CharacterImageSize)
                    .clip(CircleShape),
            )
        },
        supportingContent = {
            Text(
                text = "${stringResource(R.string.NumberOfComics)} ${character.numberOfComics}"
            )
        },
        modifier = Modifier
            .clickable { onItemClick() }
    )
}
