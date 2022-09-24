package com.android.example.jetpackcompose.birthdayapp

import android.app.Application
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.example.jetpackcompose.birthdayapp.model.Person
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme
import com.android.example.jetpackcompose.birthdayapp.ui.theme.BottomSheetShape
import kotlinx.coroutines.launch

/**
 * Created by Alvina Lushnikova on 06.09.22.
 */
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
)
@Composable
fun MainScreen(
    viewModel: PersonsViewModel,
    onIconInfoClicked: () -> Unit
) {
    val allPersons by viewModel.allPersons.observeAsState(initial = listOf())


    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val coroutineScope = rememberCoroutineScope()

    val state: Person by viewModel.state.collectAsState()
    val stateValidation: Validation by viewModel.stateValidation.collectAsState()

    fun editCard(person: Person) {
        viewModel.editPersonData(person, bottomSheetScaffoldState, coroutineScope)
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                bottomSheetScaffoldState.bottomSheetState.expand()
            } else {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(stringResource(R.string.top_app_bar_title))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onIconInfoClicked) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                FloatingActionButton(
                    onClick = {
                        viewModel.createNewPersonData(bottomSheetScaffoldState, coroutineScope)
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,

                    ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                    )
                    AnimatedVisibility(visible = bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    }
                }

            }


        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {

            BottomSheetScaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                scaffoldState = bottomSheetScaffoldState,
                sheetShape = BottomSheetShape,
                drawerElevation = 5.dp,
                sheetContent = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(420.dp)
                            .background(Color.Transparent)
                    ) {

                        DialogEditPerson(
                            state = state,
                            stateValidation = stateValidation,
                            bottomSheetScaffoldState = bottomSheetScaffoldState,
                            itemChanged = viewModel::notificationChanged,
                            nameChanged = viewModel::nameChanged,
                            dayChanged = viewModel::dayChanged,
                            monthChanged = viewModel::monthChanged,
                            yearChanged = viewModel::yearChanged,
                            savePerson = viewModel::savePerson,
                            deletePerson = viewModel::deletePerson
                        )


                    }
                }, sheetPeekHeight = 0.dp
            ) {
                MainContent(allPersons, editPersonCard = {
                    editCard(it)
                })
            }

        }

    }


}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    AppTheme {
        val owner = LocalViewModelStoreOwner.current
        owner?.let {
            val viewModel: PersonsViewModel = viewModel(
                it,
                "MainViewModel",
                MainViewModelFactory(
                    LocalContext.current.applicationContext
                            as Application
                )
            )

            MainScreen(viewModel,
                onIconInfoClicked = {

                }
            )
        }
    }

}