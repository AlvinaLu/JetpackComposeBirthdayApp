package com.android.example.jetpackcompose.birthdayapp.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/**
 * Created by Alvina Lushnikova on 13.09.22.
 */
const val MatchParent = -1

@Composable
fun widthForColumns(columnSpan: Int): Dp {
    val gridConfiguration = LocalGridConfiguration.current

    val numColumns = if (columnSpan == MatchParent) gridConfiguration.totalColumns else columnSpan

    if (numColumns > gridConfiguration.totalColumns)
        Log.w("LocalGridConfiguration", "Column count($numColumns) exceeds Total Columns(${gridConfiguration.totalColumns})")

    return gridConfiguration.columnWidth.times(numColumns) + gridConfiguration.gutterWidth.times(numColumns - 1)
}