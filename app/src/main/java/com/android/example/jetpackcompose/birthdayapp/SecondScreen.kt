package com.android.example.jetpackcompose.birthdayapp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme
import com.android.example.jetpackcompose.birthdayapp.ui.theme.BottomSheetShape

/**
 * Created by Alvina Lushnikova on 06.09.22.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    onBackPressed: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var color by remember { mutableStateOf(Black) }
    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(stringResource(R.string.top_app_bar_title_setting))
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        navigationIcon = {
                            IconButton(onClick = onBackPressed) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                    )
                }
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = BottomSheetShape,
                        color = MaterialTheme.colorScheme.background,
                        shadowElevation = 3.dp,
                        tonalElevation = 3.dp
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.outline)) {

                            Box(
                                Modifier
                                    .clickable { focusRequester.requestFocus() }
                                    .border(2.dp, color)
                                    // The focusRequester should be added BEFORE the focusable.
                                    .focusRequester(focusRequester)
                                    // The onFocusChanged should be added BEFORE the focusable that is being observed.
                                    .onFocusChanged { color = if (it.isFocused) Green else Black }
                                    .focusable()
                            ){
                                Text(text = "dddd")
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun  SecondScreenPreview() {
    AppTheme {
       SecondScreen(onBackPressed = {})
    }
}