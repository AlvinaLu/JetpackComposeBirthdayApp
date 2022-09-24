package com.android.example.jetpackcompose.birthdayapp.utils

import androidx.compose.ui.unit.Dp

/**
 * Created by Alvina Lushnikova on 13.09.22.
 */
data class GridConfiguration(
    val layoutWidth: Dp,
    val columnWidth: Dp,
    val horizontalMargin: Dp,
    val gutterWidth: Dp,
    val totalColumns: Int,
)
