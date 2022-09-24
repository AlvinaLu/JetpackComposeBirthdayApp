package com.android.example.jetpackcompose.birthdayapp

import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.chargemap.compose.numberpicker.NumberPicker

/**
 * Created by Alvina Lushnikova on 15.09.22.
 */

@Composable
fun DatePicker(
    value: Int,
    range: Iterable<Int>,
    dividersColor: Color,
    label: (Int) -> String = {
        it.toString()
    },
    textStyle: TextStyle = LocalTextStyle.current,
    valueChange: (Int) -> Unit
) {

    NumberPicker(
        value = value,
        range = range,
        label = label,
        onValueChange = valueChange,
        dividersColor = dividersColor,
        textStyle = textStyle
    )
}