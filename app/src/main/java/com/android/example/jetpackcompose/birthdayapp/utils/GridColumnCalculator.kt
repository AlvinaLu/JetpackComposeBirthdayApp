package com.android.example.jetpackcompose.birthdayapp.utils

import androidx.compose.ui.unit.Dp

/**
 * Created by Alvina Lushnikova on 13.09.22.
 */
fun gridConfiguration(
    windowWidth: Dp,
    horizontalMargin: Dp,
    gutterWidth: Dp,
    totalColumns: Int,
): GridConfiguration {

    val columnLength = (windowWidth - horizontalMargin * 2 - gutterWidth * (totalColumns - 1)) / totalColumns

    return GridConfiguration(
        layoutWidth = windowWidth,
        columnWidth = columnLength,
        horizontalMargin = horizontalMargin,
        gutterWidth = gutterWidth,
        totalColumns = totalColumns
    )
}