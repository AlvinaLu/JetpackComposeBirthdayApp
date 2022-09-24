package com.android.example.jetpackcompose.birthdayapp

import android.app.Application
import android.app.Person
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme

/**
 * Created by Alvina Lushnikova on 13.09.22.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Reminder(
    item: String,
    index: Int,
    state: Boolean,
    itemChanged: (Int) -> Unit
) {


    Chip(
        colors =
        if (state) {
            ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            )
        } else {
            ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colorScheme.background
            )

        },
        onClick = {itemChanged(index)},
        border = BorderStroke(
            1.dp, if (state) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.75f)
            }
        ),
        leadingIcon = {
            if (state) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }

    ) {
        Text(
            text = item,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            ),
            color = if (state) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.75f)
            },
        )
    }


}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReminderPreview() {
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
//
//            Reminder(
//                item = "one week",
//                index = 0,
//                state = true,
//                viewModel
//            )
        }
    }
}

