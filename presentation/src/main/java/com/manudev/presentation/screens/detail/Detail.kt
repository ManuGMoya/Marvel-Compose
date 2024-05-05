package com.manudev.presentation.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manudev.domain.model.Comic
import com.manudev.presentation.R
import com.manudev.presentation.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
        content = {
            DetailContent(
                viewModel.state,
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getCharacterDetail(id)
    }
}

@Composable
fun DetailContent(
    state: DetailViewModel.UiState
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
                    Text(text = state.error)
                }
            }

            else -> {
                val character = state.character
                if (character != null) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = character.name ?: "")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = character.description ?: "")
                        LazyColumn {
                            items(character.comics) { comic ->
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
fun ComicItem(comic: Comic) {
    Column(modifier = Modifier.padding(16.dp)) {
        comic.name?.let { Text(text = it) }
        AsyncImage(
            model = "comic.image",
            contentDescription = comic.name,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
    }
}
