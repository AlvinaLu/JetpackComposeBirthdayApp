package com.android.example.jetpackcompose.birthdayapp.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.neverEqualPolicy

/**
 * Created by Alvina Lushnikova on 13.09.22.
 */

val LocalGridConfiguration = compositionLocalOf<GridConfiguration>(
    neverEqualPolicy()
) { error("Local Grid Configuration not present") }