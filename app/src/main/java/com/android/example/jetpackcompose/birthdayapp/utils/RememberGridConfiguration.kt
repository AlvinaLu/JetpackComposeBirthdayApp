package com.android.example.jetpackcompose.birthdayapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp

/**
 * Created by Alvina Lushnikova on 13.09.22.
 */

@Composable
fun rememberGridConfiguration(
    layoutWidth: Dp,
    horizontalMargin: Dp,
    gutterWidth: Dp,
    totalColumns: Int,
) = remember {
    gridConfiguration(
        layoutWidth,
        horizontalMargin,
        gutterWidth,
        totalColumns
    )
}